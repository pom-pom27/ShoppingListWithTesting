package com.example.shoopinglistwithtesting.repository

import androidx.lifecycle.LiveData
import com.example.shoopinglistwithtesting.data.local.ShoppingDao
import com.example.shoopinglistwithtesting.data.local.entities.ShoppingItem
import com.example.shoopinglistwithtesting.data.remote.PixabayApi
import com.example.shoopinglistwithtesting.data.remote.dto.SearchImageDto
import com.example.shoopinglistwithtesting.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    val pixabayApi: PixabayApi, val shoppingDao: ShoppingDao
) : ShoppingRepository {
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeShoppingAllItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Double> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchImage(imageQuery: String): Resource<SearchImageDto> {
        return try {
            val response = pixabayApi.searchImages(imageQuery)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("Unknown Error")

            } else {
                Resource.Error("Unknown Error")
            }

        } catch (e: Exception) {

            when (e) {
                is IOException -> Resource.Error("No Internet connection found. Check your internet connection")
                is HttpException -> Resource.Error("Unreachable server")

                else -> {
                    Resource.Error("Unknown Error")
                }
            }
        }
    }
}
