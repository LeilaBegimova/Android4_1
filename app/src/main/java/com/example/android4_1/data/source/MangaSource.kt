package com.example.android4_1.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android4_1.data.remote.apiservises.MangaApi
import com.example.android4_1.data.remote.apiservises.models.Data
import kotlinx.coroutines.delay
import java.io.IOException


private const val START_KEY = 1
private const val LOAD_DELAY_MILLS = 3_000L

class MangaSource(
    private val mangaApiService: MangaApi
) :
    PagingSource<Int, Data>() {

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val offset = params.key ?: START_KEY
            val response = mangaApiService.getManga(offset = offset, limit = params.loadSize)
            val nextKey = offset + params.loadSize
            if (offset != START_KEY) delay(LOAD_DELAY_MILLS)
            LoadResult.Page(
                data = response.data,
                nextKey = nextKey,
                prevKey = null,
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: retrofit2.HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}