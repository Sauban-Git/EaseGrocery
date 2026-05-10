package com.ease.grocery.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ease.grocery.data.model.CartItem
import com.ease.grocery.data.model.Product

object CartRepository {

    private val cartItems = mutableListOf<CartItem>()

    private val _cartLiveData = MutableLiveData<List<CartItem>>()
    val cartLiveData: LiveData<List<CartItem>> = _cartLiveData

    init {
        _cartLiveData.value = cartItems
    }

    fun addToCart(product: Product) {

        val existingItem = cartItems.find {
            it.product.name == product.name
        }

        if (existingItem != null) {
            existingItem.quantity++
        } else {
            cartItems.add(CartItem(product))
        }

        _cartLiveData.value = cartItems.toList()
    }

    fun increaseQty(cartItem: CartItem) {
        cartItem.quantity++
        _cartLiveData.value = cartItems.toList()
    }

    fun decreaseQty(cartItem: CartItem) {

        if (cartItem.quantity > 1) {
            cartItem.quantity--
        } else {
            cartItems.remove(cartItem)
        }

        _cartLiveData.value = cartItems.toList()
    }

    fun removeItem(cartItem: CartItem) {
        cartItems.remove(cartItem)
        _cartLiveData.value = cartItems.toList()
    }

    fun getTotalPrice(): Int {
        return cartItems.sumOf {
            it.product.price * it.quantity
        }
    }
    fun clearCart() {
        cartItems.clear()
        _cartLiveData.value = cartItems.toList()
    }
}