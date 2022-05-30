package com.example.androidkotlinfinal.features.home_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.database.entities.DatabaseUser
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository = UserRepository(
        AppDatabase.getDatabase(application)
    )

    init {
//        refresherUser()
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

    private val _isCompletedRefresh = MutableLiveData<Boolean>()
    val isCompletedRefresh: LiveData<Boolean>
        get() = _isCompletedRefresh

    fun refresherUser(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.refreshUsers()
                withContext(Dispatchers.Main){
                    _isCompletedRefresh.value = true
                }
            }
        }
    }

    fun  fetchUsers(): Flow<PagingData<DatabaseUser>> {
        return repository.fetchUsers().map { it }.cachedIn(viewModelScope)
    }
}