package com.example.androidkotlinfinal.features.user_detail_fragment

import android.app.Application
import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.repositories.UserRepository
import kotlinx.coroutines.*
import timber.log.Timber
import java.time.DateTimeException
import java.util.*

// ViewModelFactory
class UserDetailViewModel(val data: User, application: Application) :
    AndroidViewModel(application) {
    private val repository = UserRepository(AppDatabase.getDatabase(application))


    private var _user = MutableLiveData<User>()
    val user: LiveData<User>
        get() = _user

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isSuccess = MutableLiveData<Boolean>()
    val isSuccess: LiveData<Boolean>
        get() = _isSuccess

    init {
        setup()
    }

    private fun setup(){
        Timber.d("Current Thread At: ${Calendar.getInstance().time} ,${Thread.currentThread().name}")
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                if( data.name == null ) {
                    repository.getUserNetwork(data.login)
                }
                val userDatabase = repository.getUserDatabase(data.login)
                withContext(Dispatchers.Main){
                    _user.value = userDatabase
                    _isSuccess.value = true
                }
            }
        }
    }
//    private suspend fun getUserDatabase(){
//        Timber.d("Start GetUser At: ${Calendar.getInstance().time} ,${Thread.currentThread().name}")
//        withContext(Dispatchers.IO){
//            val userDatabase = repository.getUserDatabase(data.login)
//
//        }
//        withContext(Dispatchers.Main){
//            _user.postValue()
//            Timber.d("GetUser: ${data.login} User: ${_user.value}")
//            Timber.d("Finish GetUser At: ${Calendar.getInstance().time} ,${Thread.currentThread().name}")
//        }
//    }
}

/***
 * Khi call api , and write down database Strategy Replace sẽ xóa record cũ và thêm mới
 * Lý do tại sao call được API và write down database mà nó không cập nhật lên UI
 * 
 * ***/