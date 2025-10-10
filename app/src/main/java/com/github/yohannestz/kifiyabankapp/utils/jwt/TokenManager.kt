package com.github.yohannestz.kifiyabankapp.utils.jwt

import com.github.yohannestz.kifiyabankapp.data.dto.refreshtoken.RefreshTokenRequest
import com.github.yohannestz.kifiyabankapp.data.repository.auth.AuthRepository
import com.github.yohannestz.kifiyabankapp.data.repository.preferences.PreferenceRepository
import com.github.yohannestz.kifiyabankapp.ui.main.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import timber.log.Timber
import kotlin.coroutines.cancellation.CancellationException

@Serializable
data class TokenPair(
    val token: String,
    val refresh: String
)

class TokenManager(
    private val preferences: PreferenceRepository,
    private val authRepository: AuthRepository,
    private val viewModel: MainViewModel,
    private val refreshThresholdPercent: Int = 80
) : KoinComponent {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

    private var job: Job? = null

    fun startMonitoring() {
        Timber.e("TokenManager started monitoring")
        restartObservation()
    }

    fun stopMonitoring() {
        Timber.e("TokenManager stopped monitoring")
        job?.cancel()
        job = null
    }

    private fun restartObservation() {
        Timber.e("TokenManager restarting observation")
        job?.cancel()
        job = scope.launch {
            monitorLoop()
        }
    }

    private suspend fun monitorLoop() {
        preferences.accessToken.combine(preferences.refreshToken) { access, refresh ->
            TokenPair(access, refresh)
        }.collectLatest { tokens ->
            while (isActive) {
                val now = System.currentTimeMillis() / 1000

                if (tokens.token.isBlank() || tokens.refresh.isBlank()) {
                    delay(30_000)
                    continue
                }

                val accessClaims = JwtUtil.getTokenClaims(tokens.token)
                val refreshClaims = JwtUtil.getRefreshTokenClaims(tokens.refresh)

                val accessIat = accessClaims?.iat ?: now
                val accessExp = accessClaims?.exp ?: (now + 20 * 60)
                val refreshIat = refreshClaims?.iat ?: now
                val refreshExp = refreshClaims?.exp ?: (now + 8 * 60 * 60)

                val accessThreshold = computeThreshold(accessIat, accessExp)
                val refreshThreshold = computeThreshold(refreshIat, refreshExp)

                val shouldRefresh = now >= accessThreshold || now >= refreshThreshold

                if (shouldRefresh) {
                    try {

                        val result = authRepository.refreshToken(
                            RefreshTokenRequest(tokens.refresh)
                        )

                        result.onSuccess { response ->
                            preferences.setAccessToken(response.accessToken)
                            preferences.setRefreshToken(response.refreshToken)
                        }.onFailure { e ->
                            viewModel.handleSessionEviction()
                            return@collectLatest
                        }

                        delay(10_000)
                    } catch (e: CancellationException) {
                        return@collectLatest
                    } catch (e: Exception) {
                        viewModel.handleSessionEviction()
                        return@collectLatest
                    }
                } else {
                    val nextCheck = minOf(accessThreshold, refreshThreshold)
                    val delaySecs = (nextCheck - now).coerceAtLeast(10)
                    delay(delaySecs * 1000)
                }
            }
        }
    }

    private fun computeThreshold(iat: Long, exp: Long): Long {
        val lifetime = exp - iat
        val percent = refreshThresholdPercent.coerceIn(0, 100)
        return iat + (lifetime * percent / 100)
    }
}
