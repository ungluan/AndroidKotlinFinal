package com.example.androidkotlinfinal.database.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidkotlinfinal.database.entities.User

@Dao
interface UserDao {
    @Query("Select * from user")
    fun getUsers() : LiveData<List<User>>

    @Query("Select * from user where login = :loginValue")
    suspend fun getUserByLogin(loginValue : String) : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListUser(users: List<User>)
}