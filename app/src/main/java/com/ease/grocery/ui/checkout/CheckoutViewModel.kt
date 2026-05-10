package com.ease.grocery.ui.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ease.grocery.data.repository.CartRepository

class CheckoutViewModel : ViewModel() {

    private val _totalPrice = MutableLiveData<Int>()

    val totalPrice: LiveData<Int> = _totalPrice

    init {
        loadTotal()
    }

    private fun loadTotal() {
        _totalPrice.value = CartRepository.getTotalPrice()
    }

    fun placeOrder() {

        CartRepository.clearCart()
    }
}