package com.example.androidkotlinfinal.network.dto

import com.example.androidkotlinfinal.database.entities.DatabaseUser
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.network.models.NetworkUser
import com.squareup.moshi.JsonClass

fun NetworkUser.asDatabaseModel(): DatabaseUser = DatabaseUser(
    id,
    login,
    avatar_url,
    html_url,
    name,
    blog,
    company,
    created_at,
    email,
    followers,
    bio,
    location
)

fun NetworkUser.asDomainModel(): User = User(
    id,
    login,
    avatar_url,
    html_url,
    name,
    blog,
    company,
    created_at,
    email,
    followers,
    bio,
    location
)

fun List<NetworkUser>.asDomainModel(): List<User> = map { it.asDomainModel() }

fun List<NetworkUser>.asDatabaseModel(): List<DatabaseUser> = map { it.asDatabaseModel() }