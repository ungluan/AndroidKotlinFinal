package com.example.androidkotlinfinal.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.androidkotlinfinal.features.home_fragment.ITEMS_PER_PAGE
import com.example.androidkotlinfinal.network.ApiService
import com.example.androidkotlinfinal.repositories.UserRepository
import java.lang.Exception
import kotlin.math.max

private const val STARTING_KEY = 0


class UserPagingSource(
    private val repository: UserRepository
) : PagingSource<Int, User>() {
    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(article.id - ITEMS_PER_PAGE)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val start = params.key ?: STARTING_KEY
        val range = start.until(start + params.loadSize)

        return try {
            repository.refreshUsers(curId = start)
            val data = repository.getUserPaging(start, range.last)
            LoadResult.Page(
                data = data,
                prevKey = when (start) {
                    STARTING_KEY -> null
                    else -> ensureValidKey(start)
                },
                nextKey = range.last + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}