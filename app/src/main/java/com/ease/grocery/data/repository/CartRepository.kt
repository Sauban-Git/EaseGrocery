package com.ease.grocery.data.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ease.grocery.data.local.PrefManager
import com.ease.grocery.data.model.CartItem
import com.ease.grocery.data.model.Product

object CartRepository {

    private var prefManager: PrefManager? = null

    private val cartItems = mutableListOf<CartItem>()

    private val _cartLiveData = MutableLiveData<List<CartItem>>()

    val cartLiveData: LiveData<List<CartItem>> = _cartLiveData

    fun init(context: Context) {

        if (prefManager == null) {

            prefManager = PrefManager(context)

            cartItems.clear()

            cartItems.addAll(prefManager!!.getCart())

            _cartLiveData.value = cartItems.toList()
        }
    }

    private fun saveCart() {

        prefManager?.saveCart(cartItems)
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

        saveCart()

        _cartLiveData.value = cartItems.toList()
    }

    fun increaseQty(cartItem: CartItem) {

        cartItem.quantity++

        saveCart()

        _cartLiveData.value = cartItems.toList()
    }

    fun decreaseQty(cartItem: CartItem) {

        if (cartItem.quantity > 1) {

            cartItem.quantity--

        } else {

            cartItems.remove(cartItem)
        }

        saveCart()

        _cartLiveData.value = cartItems.toList()
    }

    fun removeItem(cartItem: CartItem) {

        cartItems.remove(cartItem)

        saveCart()

        _cartLiveData.value = cartItems.toList()
    }

    fun getTotalPrice(): Int {

        return cartItems.sumOf {
            it.product.price * it.quantity
        }
    }

    fun getCartCount(): Int {

        return cartItems.sumOf {
            it.quantity
        }
    }

    fun clearCart() {

        cartItems.clear()

        saveCart()

        _cartLiveData.value = cartItems.toList()
    }
}