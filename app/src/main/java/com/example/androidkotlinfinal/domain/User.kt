package com.example.androidkotlinfinal.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int,
    val login: String,
    val avatarUrl: String,
    val htmlUrl: String,
    val name: String?,
    val blog: String?,
    val company: String?,
    val createdAt: String?,
    val email: String?,
    val followers: Int?,
    val bio: String?
) : Parcelable