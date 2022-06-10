package com.example.shoopinglistwithtesting.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.shoopinglistwithtesting.R
import com.example.shoopinglistwithtesting.launchFragmentInHiltContainer
import com.example.shoopinglistwithtesting.repository.FakeShoppingRepositoryAndroidTest
import com.example.shoopinglistwithtesting.ui.viewmodels.ShoppingViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@HiltAndroidTest
class AddShoppingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)

        //launch the fragment
        launchFragmentInHiltContainer<AddShoppingFragment> {
            //setting nav controller
            Navigation.setViewNavController(requireView(), navController)
        }
        //do something
        pressBack()

        //expect
        verify(navController).popBackStack()

    }

    @Test
    fun pressShoppingImage_navigateRoPickImageFragment() {

        //mock navcontroller
        val navController = mock(NavController::class.java)

        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())

        //launch the fragment
        launchFragmentInHiltContainer<AddShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)

            //            viewModel = testViewModel

        }

        //do the task
        onView(withId(R.id.ivShoppingImage)).perform(click())

        //vertify/expect
        verify(navController).navigate(R.id.action_addShoppingFragment_to_imagePickFragment)

    }
}
