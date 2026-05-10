package com.ease.grocery.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ease.grocery.data.model.CartItem
import com.ease.grocery.data.repository.CartRepository

class CartViewModel : ViewModel() {

    val cartItems: LiveData<List<CartItem>> =
        CartRepository.cartLiveData

    fun increaseQty(item: CartItem) {
        CartRepository.increaseQty(item)
    }

    fun decreaseQty(item: CartItem) {
        CartRepository.decreaseQty(item)
    }

    fun removeItem(item: CartItem) {
        CartRepository.removeItem(item)
    }

    fun getTotalPrice(): Int {
        return CartRepository.getTotalPrice()
    }
}