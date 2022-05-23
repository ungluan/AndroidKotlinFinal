package com.example.androidkotlinfinal.features.user_detail_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.repositories.UserRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

// ViewModelFactory
class UserDetailViewModel(data: User, application: Application) :
    AndroidViewModel(application) {
    private val repository = UserRepository(AppDatabase.getDatabase(application))
    private var _user = MutableLiveData(data)
    val user: LiveData<User>
        get() = _user

    init {
        runBlocking {
            getUserNetwork(data.login)
        }
//        _user.value = repository.getUserDatabase(data.login).value
    }



    private fun getUserNetwork(login: String) {
        viewModelScope.launch {
//            if(_user.value?.name.isNullOrEmpty()){
//            viewModelScope.launch {
                repository.getUserNetwork(login)
                _showLocation.value = _user.value?.location?.isNotEmpty() ?: false
//            }
//            }
        }
    }
    // Có data, lúc đầu chưa có -> Cần phải đợi api -> Sau khi có api nó vẫn chưa thay đổi
    private val _showLocation =
        MutableLiveData(_user.value?.location?.isNotEmpty() ?: false)
    val showLocation : LiveData<Boolean>
        get() = _showLocation

    private val _showEmail = MutableLiveData(_user.value?.blog)
}

