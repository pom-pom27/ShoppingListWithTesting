package com.example.shoopinglistwithtesting.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shopping_items")
data class ShoppingItem(
    val name: String,
    val amount: Int,
    val price: Double,
    val imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
)
