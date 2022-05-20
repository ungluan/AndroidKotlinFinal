package com.example.androidkotlinfinal.repositories

import android.util.Log
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.network.ApiNetwork
import com.example.androidkotlinfinal.network.asDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class UserRepository(private val database: AppDatabase) {
    suspend fun refreshUsers(){
        withContext(Dispatchers.IO){
            Timber.d("Calling API getUsers")
            val data = ApiNetwork.retrofitService.getListUser(0,25)
            Timber.d("ListUser: $data")
            database.userDao.insertListUser(data.map { it.asDomain() })
            Timber.d("Wrote ListUser to Database")
        }
    }
}