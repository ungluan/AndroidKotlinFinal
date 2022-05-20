package com.example.androidkotlinfinal.utils

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.features.home_fragment.UserListAdapter

@BindingAdapter("listDataUser")
fun RecyclerView.listDataUser(users: List<User>?){
    users?.let {
        (adapter as UserListAdapter).submitList(users)
    }
}