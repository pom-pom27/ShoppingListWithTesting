package com.example.shoopinglistwithtesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoopinglistwithtesting.data.local.entities.ShoppingItem

@Dao
interface ShoppingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM( price * amount) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Double>
}
