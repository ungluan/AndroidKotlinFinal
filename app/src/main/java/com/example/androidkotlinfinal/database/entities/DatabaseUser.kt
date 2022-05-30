package com.example.androidkotlinfinal.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidkotlinfinal.domain.User

@Entity(tableName = "user")
data class DatabaseUser(
    @PrimaryKey
    val id: Int,
    val login: String,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    val name: String?,
    val blog: String?,
    val company: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: String?,
    val email: String?,
    val followers: Int?,
    val bio: String?,
    val location: String?
)

fun DatabaseUser.asDomainModel(): User =
    User(
        id,
        login,
        avatarUrl,
        htmlUrl,
        name,
        blog,
        company,
        createdAt,
        email,
        followers,
        bio,
        location
    )

fun List<DatabaseUser>.asDomainModel(): List<User> {
    return map { userDatabase -> userDatabase.asDomainModel() }
}
