package com.example.androidkotlinfinal.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.androidkotlinfinal.database.entities.DatabaseUser
import com.example.androidkotlinfinal.domain.User
import kotlinx.coroutines.Deferred

@Dao
interface UserDao {
    @Query("Select * from user")
    fun getUsers() : LiveData<List<DatabaseUser>>

    @Query("Select * from user where id > :id LIMIT :limit")
    fun getUserPaging(id: Int, limit: Int) : List<DatabaseUser>

    @Query("Select * from user where login = :loginValue")
    suspend fun getUserByLogin(loginValue : String) : DatabaseUser

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListUser(databaseUsers: List<DatabaseUser>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: DatabaseUser)
}