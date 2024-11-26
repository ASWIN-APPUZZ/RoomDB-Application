package com.android.roomdb.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.android.roomdb.model.UserModel

class UserRepository(private val userDao: UserDao) {
    val getAllUsers: LiveData<List<UserModel>> = userDao.getAllUsers().map { list->
        list.map { it.toUserModel()  }
    }

    suspend fun addUser(user: UserModel) {
        userDao.addUser(
            UserEntity(
                userId = user.userId,
                userName = user.userName,
                userEmail = user.userEmail,
                userAge = user.userAge
            )
        )
    }

    suspend fun updateUser(user: UserModel) {
        userDao.updateUser(
            UserEntity(
                userId = user.userId,
                userName = user.userName,
                userEmail = user.userEmail,
                userAge = user.userAge
            )
        )
    }

    suspend fun deleteUser(user: UserModel) {
        userDao.deleteUser(
            UserEntity(
                userId = user.userId,
                userName = user.userName,
                userEmail = user.userEmail,
                userAge = user.userAge
            )
        )
    }
}