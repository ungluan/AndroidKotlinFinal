package com.example.androidkotlinfinal.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.androidkotlinfinal.database.AppDatabase
import com.example.androidkotlinfinal.domain.User
import com.example.androidkotlinfinal.network.ApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val userRepository: UserRepository
) : RemoteMediator<Int, User>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        return try{
            userRepository.refreshUsersPaging(0, state.config.pageSize)



            MediatorResult.Success(endOfPaginationReached = false)
        } catch (exception: IOException) {
            MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            MediatorResult.Error(exception)
        }
    }

}