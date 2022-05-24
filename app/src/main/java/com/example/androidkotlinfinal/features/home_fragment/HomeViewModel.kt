package com.example.androidkotlinfinal.features.home_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
const val ITEMS_PER_PAGE = 25

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

    val items: Flow<PagingData<User>> = Pager(
        config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
        pagingSourceFactory = { repository.userPagingSource() }
    ).flow.cachedIn(viewModelScope)
}