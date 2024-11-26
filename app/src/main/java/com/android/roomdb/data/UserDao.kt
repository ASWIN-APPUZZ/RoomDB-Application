package com.android.roomdb.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)
 
    @Query("SELECT * FROM user_table ORDER BY userId ASC")
    fun getAllUsers(): LiveData<List<UserEntity>>

    @Delete
    suspend fun deleteUser(user: UserEntity)

}