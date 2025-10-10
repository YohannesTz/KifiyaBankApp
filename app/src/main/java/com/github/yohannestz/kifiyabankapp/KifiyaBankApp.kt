package com.github.yohannestz.kifiyabankapp

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.request.crossfade
import com.github.yohannestz.kifiyabankapp.di.dataStoreModule
import com.github.yohannestz.kifiyabankapp.di.databaseModule
import com.github.yohannestz.kifiyabankapp.di.networkModule
import com.github.yohannestz.kifiyabankapp.di.repositoryModule
import com.github.yohannestz.kifiyabankapp.di.serviceModule
import com.github.yohannestz.kifiyabankapp.di.viewModelModule
import com.github.yohannestz.kifiyabankapp.utils.jwt.TokenManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import timber.log.Timber

class KifiyaBankApp : Application(), KoinComponent, SingletonImageLoader.Factory {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@KifiyaBankApp)
            modules(
                dataStoreModule,
                networkModule,
                databaseModule,
                serviceModule,
                repositoryModule,
                viewModelModule,
            )
        }

        val tokenManager: TokenManager = getKoin().get()
        tokenManager.startMonitoring()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(this)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, percent = 0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .crossfade(300)
            .build()
    }

}