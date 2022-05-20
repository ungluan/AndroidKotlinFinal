package com.example.androidkotlinfinal.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User  (
    @PrimaryKey
    val id: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "html_url")
    val htmlUrl : String
)