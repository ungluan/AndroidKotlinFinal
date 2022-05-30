package com.example.androidkotlinfinal.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.database.entities.DatabaseUser
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.network.ApiService
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userRepository: UserRepository
) : RemoteMediator<Int, DatabaseUser>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DatabaseUser>
    ): MediatorResult {
        return try {
            val lastId = userRepository.getLastId()
            Timber.d("Last id: $lastId")
            userRepository.refreshUsersPaging(lastId, state.config.pageSize)
            val isEnd = userRepository.getAfterUser(lastId) == null
            Timber.d("isEnd: $isEnd")
            MediatorResult.Success(endOfPaginationReached = isEnd)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

}