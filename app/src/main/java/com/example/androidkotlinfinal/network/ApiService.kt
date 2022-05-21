package com.example.androidkotlinfinal.network

import com.example.androidkotlinfinal.network.dto.NetworkUserContainer
import com.example.androidkotlinfinal.network.models.NetworkUser
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = " https://api.github.com"


interface ApiService {
    //https://api.github.com/users?since=2&per_page=10
    @GET("/users")
    suspend fun getListUser(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ) : List<NetworkUser>

//    @GET("users/{login}")
//    suspend fun getUser(
//        @Path("login") login: String
//    ) : User
}

object ApiNetwork {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

