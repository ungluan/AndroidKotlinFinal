package com.example.androidkotlinfinal.network


import com.example.androidkotlinfinal.BuildConfig
import com.example.androidkotlinfinal.network.models.NetworkUser
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = " https://api.github.com"


interface ApiService {
    //https://api.github.com/users?since=2&per_page=10
    @GET("/users")
    suspend fun getListUser(
        @Query("since") since: Int,
        @Query("per_page") perPage: Int
    ): List<NetworkUser>

    @GET("users/{login}")
    suspend fun getUser(
        @Path("login") login: String
    ): NetworkUser
}

object ApiNetwork {
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val client = OkHttpClient.Builder().addInterceptor {
        HeaderInterceptor().intercept(it)
    }.build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .client(client)
        .build()

    val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        proceed(
            request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.ACCESS_TOKEN}")
                .build()
        )
    }
}
