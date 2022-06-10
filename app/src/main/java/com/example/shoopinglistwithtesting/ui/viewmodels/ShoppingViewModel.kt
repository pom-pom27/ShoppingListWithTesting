package com.example.shoopinglistwithtesting.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoopinglistwithtesting.data.local.entities.ShoppingItem
import com.example.shoopinglistwithtesting.data.remote.dto.SearchImageDto
import com.example.shoopinglistwithtesting.repository.ShoppingRepository
import com.example.shoopinglistwithtesting.util.Constant
import com.example.shoopinglistwithtesting.util.Event
import com.example.shoopinglistwithtesting.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    val shoppingList = shoppingRepository.observeShoppingAllItems()
    val totalPrice = shoppingRepository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<SearchImageDto>>>()
    val images: LiveData<Event<Resource<SearchImageDto>>> = _images

    private val _currentImageUrl = MutableLiveData<String>()
    val currentImageUrl: LiveData<String> = _currentImageUrl

    private val _inputFormStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val inputFormStatus: LiveData<Event<Resource<ShoppingItem>>> = _inputFormStatus

    fun setCurImage(url: String) {
        _currentImageUrl.postValue(url)
    }

    fun validateInputForm(name: String, amountString: String, priceString: String) {
        val isInputBlank = name.isBlank() || amountString.isBlank() || priceString.isBlank()

        if (isInputBlank) {
            _inputFormStatus.postValue(Event(Resource.Error("All input cant be empty")))
            return
        }

        if (name.length > Constant.MAX_NAME_LENGTH) {
            _inputFormStatus.postValue(Event(Resource.Error("The name length must not exceed ${Constant.MAX_NAME_LENGTH} characters")))
            return
        }

        val isValidNumber =
            amountString.toIntOrNull() == null || priceString.toDoubleOrNull() == null
        if (isValidNumber) {
            _inputFormStatus.postValue(Event(Resource.Error("Please input a valid number")))
            return
        }
        if (priceString.length > Constant.MAX_PRICE_LENGTH) {
            _inputFormStatus.postValue(Event(Resource.Error("The price must not exceed ${Constant.MAX_NAME_LENGTH} characters")))
            return
        }

        val shoppingItem = ShoppingItem(
            name,
            amountString.toInt(),
            priceString.toDouble(),
            currentImageUrl.value ?: ""
        )
        viewModelScope.launch {

            insertShoppingItemIntoDb(shoppingItem)
        }
        _currentImageUrl.postValue("")

        _inputFormStatus.postValue(
            Event(
                Resource.Success(
                    shoppingItem
                )
            )
        )
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.insertShoppingItem(shoppingItem)
    }

    fun deleteShoppingList(item: ShoppingItem) = viewModelScope.launch {
        shoppingRepository.deleteShoppingItem(item)
    }

    fun searchImage(query: String) {
        if (query.isBlank()) {
            return
        }
        _images.value = Event(Resource.Loading())

        viewModelScope.launch {
            val response = shoppingRepository.searchImage(query)
            _images.value = Event(response)
        }
    }

}
