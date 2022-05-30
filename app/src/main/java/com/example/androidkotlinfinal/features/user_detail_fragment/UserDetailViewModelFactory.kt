package com.example.androidkotlinfinal.features.user_detail_fragment

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.androidkotlinfinal.domain.User

/**
 * Simple ViewModel factory that provides the User and context to the ViewModel.
 * ViewModelProvider.Factory is implementations of Factory interface are response for instantiate ViewModel.
 */
class UserDetailViewModelFactory(
    private val user: User,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserDetailViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return UserDetailViewModel(user, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}