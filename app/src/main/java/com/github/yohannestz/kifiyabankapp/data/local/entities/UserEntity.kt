package com.github.yohannestz.kifiyabankapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String?,
    val phoneNumber: String?,
    val lastUpdated: Long = System.currentTimeMillis()
)