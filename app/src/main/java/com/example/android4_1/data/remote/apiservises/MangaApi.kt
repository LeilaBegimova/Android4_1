package com.example.android4_1.data.remote.apiservises

import com.example.android4_1.data.remote.apiservises.models.MangaResponse
import retrofit2.http.GET
import retrofit2.http.Query

private const val POST_END_POINT = "manga"

interface MangaApi {

    @GET(POST_END_POINT)
    suspend fun getManga(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int,
    ): MangaResponse
}
