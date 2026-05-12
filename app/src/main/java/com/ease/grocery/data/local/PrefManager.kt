package com.ease.grocery.data.local

import android.content.Context
import com.ease.grocery.data.model.CartItem
import com.ease.grocery.data.model.Product
import org.json.JSONArray
import org.json.JSONObject

class PrefManager(context: Context) {

    private val prefs =
        context.getSharedPreferences("grocery_app", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LOGIN = "is_logged_in"
        private const val KEY_CART = "cart_items"
    }

    // ---------------- LOGIN ----------------

    fun saveLogin(isLoggedIn: Boolean) {

        prefs.edit()
            .putBoolean(KEY_LOGIN, isLoggedIn)
            .apply()
    }

    fun isLoggedIn(): Boolean {

        return prefs.getBoolean(KEY_LOGIN, false)
    }

    fun logout() {

        prefs.edit().clear().apply()
    }

    // ---------------- CART ----------------

    fun saveCart(cartList: List<CartItem>) {

        val jsonArray = JSONArray()

        cartList.forEach { item ->

            val json = JSONObject()

            json.put("name", item.product.name)
            json.put("price", item.product.price)
            json.put("unit", item.product.unit)
            json.put("category", item.product.category)
            json.put("image", item.product.image)
            json.put("quantity", item.quantity)

            jsonArray.put(json)
        }

        prefs.edit()
            .putString(KEY_CART, jsonArray.toString())
            .apply()
    }

    fun getCart(): MutableList<CartItem> {

        val cartList = mutableListOf<CartItem>()

        val jsonString = prefs.getString(KEY_CART, null)
            ?: return cartList

        val jsonArray = JSONArray(jsonString)

        for (i in 0 until jsonArray.length()) {

            val json = jsonArray.getJSONObject(i)

            val product = Product(
                name = json.getString("name"),
                price = json.getInt("price"),
                unit = json.getString("unit"),
                category = json.getString("category"),
                image = json.getInt("image")
            )

            val cartItem = CartItem(
                product = product,
                quantity = json.getInt("quantity")
            )

            cartList.add(cartItem)
        }

        return cartList
    }
}