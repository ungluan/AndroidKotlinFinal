package com.example.androidkotlinfinal.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.*
import androidx.room.Database
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.database.entities.DatabaseUser
import com.example.androidkotlinfinal.database.entities.asDomainModel
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.network.ApiNetwork
import com.example.androidkotlinfinal.network.dto.asDatabaseModel
import com.example.androidkotlinfinal.network.dto.asDomainModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import kotlin.system.measureTimeMillis

private const val ITEMS_PER_PAGE = 30

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

    suspend fun refreshUsersPaging(after: Int, pageSize: Int) {
        try {
            withContext(Dispatchers.IO) {
                Timber.d("Current Thread: ${Thread.currentThread().name}")
                Timber.d("Calling API getUsers")
                val data = ApiNetwork.retrofitService.getListUser(after, pageSize)
                Timber.d("Current Thread: ${Thread.currentThread().name}")
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

    suspend fun getAfterUser(lastId: Int): User? {
        val data: List<User>
        withContext(Dispatchers.IO) {
            data = ApiNetwork.retrofitService.getListUser(lastId, 1).asDomainModel()
        }
        return data.firstOrNull()
    }

    @OptIn(ExperimentalPagingApi::class)
    fun fetchUsers(): Flow<PagingData<DatabaseUser>> {
        return Pager(
            config = PagingConfig(ITEMS_PER_PAGE, prefetchDistance = 3, enablePlaceholders = false),
            remoteMediator = UserRemoteMediator(this),
            pagingSourceFactory = { database.userDao.getUserForPagingSource() }
        ).flow
    }

    suspend fun getLastId(): Int {
        val id: Int
        withContext(Dispatchers.IO) {
            id = database.userDao.getLastId()
        }
        return id
    }
}