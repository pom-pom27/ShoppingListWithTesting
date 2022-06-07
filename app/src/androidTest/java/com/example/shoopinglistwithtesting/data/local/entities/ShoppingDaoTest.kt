package com.example.shoopinglistwithtesting.data.local.entities

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shoopinglistwithtesting.data.local.ShoppingDatabase
import com.example.shoopinglistwithtesting.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ShoppingDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ShoppingDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.shoppingDao()
    }

    @Test
    fun insertShoppingItem() = runTest(UnconfinedTestDispatcher()) {

        val shoppingItem = ShoppingItem("Ayam", 1, 10.0, "ayam.png", 1)

        dao.insertShoppingItem(shoppingItem)
        val shippingList = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(shippingList).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runTest(UnconfinedTestDispatcher()) {
        val shoppingItem = ShoppingItem("Ayam", 1, 10.0, "ayam.png", 1)

        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val shoppingList = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(shoppingList).doesNotContain(shoppingItem)

    }

    @Test
    fun observeTotalPrice() = runTest(UnconfinedTestDispatcher()) {
        val shoppingItem1 = ShoppingItem("Ayam", 1, 10.0, "ayam.png", 1)
        val shoppingItem2 = ShoppingItem("Ayam", 2, 10.0, "ayam.png", 2)
        val shoppingItem3 = ShoppingItem("Ayam", 3, 10.0, "ayam.png", 3)

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPrice = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPrice).isEqualTo(1 * 10.0 + 2 * 10.0 + 3 * 10.0)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
