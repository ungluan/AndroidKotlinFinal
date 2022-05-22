package com.example.androidkotlinfinal.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.database.entities.asDomainModel
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.network.ApiNetwork
import com.example.androidkotlinfinal.network.dto.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepository(private val database: AppDatabase) {

    val users: LiveData<List<User>> = Transformations.map(database.userDao.getUsers()){
        users -> users.asDomainModel()
    }
    fun getUserDatabase(login: String): LiveData<User> =
        Transformations.map(database.userDao.getUserByLogin(login)){ user -> user.asDomainModel() }

    suspend fun refreshUsers(){
        try{
            withContext(Dispatchers.IO){
                Timber.d("Calling API getUsers")
                val data = ApiNetwork.retrofitService.getListUser(0,25)
                Timber.d("ListUser: $data")
                database.userDao.insertListUser(data.asDatabaseModel())
                Timber.d("Wrote ListUser to Database")
            }
        }catch (e: Exception){
            Log.e("HomeFragment","Refresher User failed: ${e.message}")
        }
    }

    suspend fun getUserNetwork(login: String){
        try{
            withContext(Dispatchers.IO){
                Timber.d("Calling API getUser")
                val user = ApiNetwork.retrofitService.getUser(login)
                Timber.d("User: $user")
                database.userDao.insertUser(user.asDatabaseModel())
                Timber.d("Wrote User to Database")
            }
        }catch (e: Exception){
            Log.e("DetailFragment","GetUserNetwork failed: ${e.message}")
        }
    }
}