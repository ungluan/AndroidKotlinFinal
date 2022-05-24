package com.example.androidkotlinfinal.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.database.entities.asDomainModel
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.domain.UserPagingSource
import com.example.androidkotlinfinal.network.ApiNetwork
import com.example.androidkotlinfinal.network.dto.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*

class UserRepository(private val database: AppDatabase) {

    val users: LiveData<List<User>> = Transformations.map(database.userDao.getUsers()) { users ->
        users.asDomainModel()
    }

    fun userPagingSource() = UserPagingSource(this)

    fun getUserPaging(id: Int, limit: Int) : List<User> {
        return database.userDao.getUserPaging(id, limit).asDomainModel()
    }

    suspend fun getUserDatabase(login: String): User {
        Timber.d("Start getUserNetwork At: ${Calendar.getInstance().time}")
        val userDatabase = database.userDao.getUserByLogin(login)
        Timber.d("Finished getUserDatabase: $userDatabase")
        return userDatabase.asDomainModel()
    }

    suspend fun refreshUsers(curId: Int = 0, pageSize: Int = 25) {
        try {
            withContext(Dispatchers.IO) {
                Timber.d("Calling API getUsers")
                val data = ApiNetwork.retrofitService.getListUser(curId,pageSize)
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