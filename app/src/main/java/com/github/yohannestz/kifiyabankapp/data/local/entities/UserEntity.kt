package com.github.yohannestz.kifiyabankapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.yohannestz.kifiyabankapp.data.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val username: String,
    val firstName: String? = null,
    val lastName: String? = null,
    val phoneNumber: String? = null,
    val lastUpdated: Long = System.currentTimeMillis()
) {
    fun toUser() = User(
        id = id,
        username = username,
        firstName = firstName ?: "No firstname",
        lastName = lastName ?: "No lastname",
        phoneNumber = phoneNumber
    )
}