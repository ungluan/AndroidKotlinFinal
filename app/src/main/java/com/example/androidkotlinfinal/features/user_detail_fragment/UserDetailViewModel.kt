package com.example.androidkotlinfinal.features.user_detail_fragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidkotlinfinal.domain.User

// ViewModelFactory
class UserDetailViewModel(data: User, application: Application) :
    AndroidViewModel(application) {

    private val _user = MutableLiveData<User>(data)
    val user: LiveData<User>
        get() = _user

}

