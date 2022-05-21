package com.example.androidkotlinfinal.features.home_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(
        AppDatabase.getDatabase(application)
    )

    init {
        refresherUser()
    }

    private val _listUser = repository.users
    val listUser : LiveData<List<User>>
        get() = _listUser

    private val _navigationToDetailUser = MutableLiveData<Boolean>()
    val navigationToDetailUser : LiveData<Boolean>
        get() = _navigationToDetailUser

    fun completedNavigationToDetailUser(){
        _navigationToDetailUser.value = false
    }

    private fun refresherUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.refreshUsers()
            }
        }
    }


}