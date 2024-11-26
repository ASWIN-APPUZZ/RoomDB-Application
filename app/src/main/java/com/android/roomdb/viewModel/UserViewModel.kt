package com.android.roomdb.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.android.roomdb.data.UserDatabase
import com.android.roomdb.data.UserRepository
import com.android.roomdb.model.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val repository: UserRepository
    val getAllUsers: LiveData<List<UserModel>>

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
        getAllUsers = repository.getAllUsers
    }

    fun addUser(user: UserModel) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.addUser(user)
        }
    }

    fun updateUser(user: UserModel) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.updateUser(user)
        }
    }

    fun deleteUser(user: UserModel) {
        viewModelScope.launch (Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }
}