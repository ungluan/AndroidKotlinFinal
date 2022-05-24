package com.example.androidkotlinfinal.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.database.entities.asDomainModel
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.network.ApiNetwork
import com.example.androidkotlinfinal.network.dto.asDatabaseModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class UserRepository(private val database: AppDatabase) {

    val users: LiveData<List<User>> = Transformations.map(database.userDao.getUsers()) { users ->
        users.asDomainModel()
    }

    suspend fun getUserDatabase(login: String): User {
        Timber.d("Start getUserNetwork At: ${Calendar.getInstance().time}")
        val userDatabase = database.userDao.getUserByLogin(login)
        Timber.d("Finished getUserDatabase: $userDatabase")
        return userDatabase.asDomainModel()
    }

    suspend fun refreshUsers() {
        try {
            withContext(Dispatchers.IO) {
                Timber.d("Calling API getUsers")
                val data = ApiNetwork.retrofitService.getListUser(0, 25)
                Timber.d("ListUser: $data")
                database.userDao.insertListUser(data.asDatabaseModel())
                Timber.d("Wrote ListUser to Database")
            }
        } catch (e: Exception) {
            Timber.d("Refresher User failed: ${e.message}")
        } finally {
            Timber.d("Finished Refresh User At: ${Calendar.getInstance().time} ,${Thread.currentThread().name}")
        }
    }

    suspend fun getUserNetwork(login: String) {
        try {
                Timber.d("Start getUserNetwork At: ${Calendar.getInstance().time} ,${Thread.currentThread().name}")
                val user = ApiNetwork.retrofitService.getUser(login)
                Timber.d("User: $user")
                database.userDao.insertUser(user.asDatabaseModel())
                Timber.d("Wrote User to Database")

        } catch (e: Exception) {
            Timber.d("GetUserNetwork failed: ${e.message}")
        } finally {
            Timber.d("Finished Refresh User At: ${Calendar.getInstance().time} ,${Thread.currentThread().name}")
        }
    }
}