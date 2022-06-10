package com.example.shoopinglistwithtesting.data.remote

import com.example.shoopinglistwithtesting.BuildConfig
import com.example.shoopinglistwithtesting.data.remote.dto.SearchImageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayApi {

    @GET("api/")
    suspend fun searchImages(
        @Query("q") searchQuery: String,
        @Query("key") apiKey: String = BuildConfig.API_KEY
    ): Response<SearchImageDto>
}
