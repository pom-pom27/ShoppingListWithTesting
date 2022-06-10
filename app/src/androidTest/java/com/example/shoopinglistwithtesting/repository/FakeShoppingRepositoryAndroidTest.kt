package com.example.shoopinglistwithtesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoopinglistwithtesting.data.local.entities.ShoppingItem
import com.example.shoopinglistwithtesting.data.remote.dto.SearchImageDto
import com.example.shoopinglistwithtesting.util.Resource

class FakeShoppingRepositoryAndroidTest : ShoppingRepository {

    private val shoppingList = mutableListOf<ShoppingItem>()

    private var observableShoppingList = MutableLiveData<List<ShoppingItem>>(shoppingList)

    private val observableTotalPrice = MutableLiveData<Double>()

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observableShoppingList.postValue(shoppingList)
        observableTotalPrice.postValue(getTotalPrice())
    }

    fun getTotalPrice(): Double {
        return shoppingList.sumOf { it.price }
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingList.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeShoppingAllItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingList
    }

    override fun observeTotalPrice(): LiveData<Double> {
        return observableTotalPrice
    }

    override suspend fun searchImage(imageQuery: String): Resource<SearchImageDto> {
        return if (shouldReturnNetworkError) {
            Resource.Error("Error")
        } else {
            Resource.Success(SearchImageDto(listOf(), 1, 1))
        }
    }

}
