package com.example.shoopinglistwithtesting.ui.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoopinglistwithtesting.MainDispatcherRule
import com.example.shoopinglistwithtesting.getOrAwaitValue
import com.example.shoopinglistwithtesting.repository.FakeShoppingRepository
import com.example.shoopinglistwithtesting.util.Constant
import com.example.shoopinglistwithtesting.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingViewModelTest {

    private lateinit var viewModel: ShoppingViewModel

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `When name input is empty, return Error`() {
        viewModel.validateInputForm("", "10", "2000")

        val value = viewModel.inputFormStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled() is Resource.Error).isTrue()
    }

    @Test
    fun `When amount input is empty, return Error`() {
        viewModel.validateInputForm("asdas", "", "2000")

        val value = viewModel.inputFormStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled() is Resource.Error).isTrue()
    }

    @Test
    fun `When price input is empty, return Error`() {
        viewModel.validateInputForm("asdas", "10", "")

        val value = viewModel.inputFormStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled() is Resource.Error).isTrue()
    }

    @Test
    fun `When amount or price inputs is not a number, return Error`() {
        viewModel.validateInputForm("asdas", "asdas", "asdas")

        val value = viewModel.inputFormStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled() is Resource.Error).isTrue()
    }

    @Test
    fun `When all input is valid, return Success`() =
        runTest {

            viewModel.validateInputForm("adsa", "10", "2000")
            val value = viewModel.inputFormStatus.getOrAwaitValue()

            assertThat(value.getContentIfNotHandled() is Resource.Success).isTrue()
        }

    @Test
    fun `When insert a shoppingItem successfully, currentImage must empty`() = runTest {
        viewModel.validateInputForm("adsa", "10", "2000")

        val currentImage = viewModel.currentImageUrl.getOrAwaitValue()

        assertThat(currentImage).isEqualTo("")

    }

    @Test
    fun `When user input name that exceed max length, return Error`() = runTest {
        val string = buildString {
            for (i in 1..Constant.MAX_NAME_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.validateInputForm(string, "10", "2000")

        val value = viewModel.inputFormStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled() is Resource.Error).isTrue()

    }

    @Test
    fun `When user input price that exceed max length, return Error`() = runTest {
        val string = buildString {
            for (i in 1..Constant.MAX_PRICE_LENGTH + 1) {
                append(1)
            }
        }

        viewModel.validateInputForm("string", "10", string)

        val value = viewModel.inputFormStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled() is Resource.Error).isTrue()

    }

}
