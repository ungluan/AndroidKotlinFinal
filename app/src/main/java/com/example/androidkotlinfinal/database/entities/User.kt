package com.example.androidkotlinfinal.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "user")
data class User (
    val id: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "html_url")
    val htmlUrl : String,
    val email: String
)