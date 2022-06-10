package com.example.shoopinglistwithtesting.repository

import androidx.lifecycle.LiveData
import com.example.shoopinglistwithtesting.data.local.entities.ShoppingItem
import com.example.shoopinglistwithtesting.data.remote.dto.SearchImageDto
import com.example.shoopinglistwithtesting.util.Resource

interface ShoppingRepository {

    //dao

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeShoppingAllItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Double>

    //api

    suspend fun searchImage(imageQuery: String): Resource<SearchImageDto>
}
