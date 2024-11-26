package com.android.roomdb.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.android.roomdb.model.UserModel

@Entity(tableName = "user_table")
data class UserEntity (
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val userName: String,
    val userEmail: String,
    val userAge: Int
){
    fun toUserModel(): UserModel {
        return UserModel(
            userId = userId,
            userName = userName,
            userEmail = userEmail,
            userAge = userAge
        )
    }
}