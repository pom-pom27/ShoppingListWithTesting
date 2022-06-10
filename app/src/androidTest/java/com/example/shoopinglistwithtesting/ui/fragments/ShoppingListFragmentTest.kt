package com.example.shoopinglistwithtesting.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.shoopinglistwithtesting.R
import com.example.shoopinglistwithtesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingListFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun clickFab_navigateToAddShoppingItemFragment() {
        val navController = mock(NavController::class.java)

        //launch the fragment
        launchFragmentInHiltContainer<ShoppingListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        //click fab
        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(navController).navigate(R.id.action_shoppingListFragment_to_addShoppingFragment)
    }

}
