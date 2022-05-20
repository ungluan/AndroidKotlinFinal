package com.example.androidkotlinfinal.network.dto

import com.example.androidkotlinfinal.database.entities.DatabaseUser
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.network.models.NetworkUser
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkUserContainer(val users: List<NetworkUser>)

fun NetworkUser.asDatabaseModel(): DatabaseUser = DatabaseUser(id, login, html_url, avatar_url)

fun NetworkUser.asDomainModel(): User = User(id, login, avatar_url, html_url)

fun NetworkUserContainer.asDomainModel(): List<User> = users.map { it.asDomainModel() }

fun NetworkUserContainer.asDatabaseModel(): List<DatabaseUser> = users.map { it.asDatabaseModel() }