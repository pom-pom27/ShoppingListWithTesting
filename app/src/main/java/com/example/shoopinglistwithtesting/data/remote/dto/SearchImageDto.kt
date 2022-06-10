package com.example.shoopinglistwithtesting.data.remote.dto


import com.google.gson.annotations.SerializedName

data class SearchImageDto(
    @SerializedName("hits")
    val hits: List<ImageDto?>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("totalHits")
    val totalHits: Int?
)
