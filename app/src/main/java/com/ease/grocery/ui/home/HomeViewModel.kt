package com.ease.grocery.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ease.grocery.R
import com.ease.grocery.data.model.Product

class HomeViewModel : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products
    private val list = listOf(
        Product("Apple", 120, "1 Kg", "Fruits", R.drawable.ic_launcher_foreground),
        Product("Banana", 60, "1 Dozen", "Fruits", R.drawable.ic_launcher_foreground),
        Product("Milk", 50, "1 Litre", "Dairy", R.drawable.ic_launcher_foreground),
        Product("Bread", 40, "1 Pack", "Bakery", R.drawable.ic_launcher_foreground),
        Product("Tomato", 30, "1 Kg", "Vegetables", R.drawable.ic_launcher_foreground),

        Product("Rice", 80, "1 Kg", "Grocery", R.drawable.ic_launcher_foreground),
        Product("Wheat Flour", 70, "1 Kg", "Grocery", R.drawable.ic_launcher_foreground),
        Product("Sugar", 45, "1 Kg", "Grocery", R.drawable.ic_launcher_foreground),
        Product("Salt", 20, "1 Kg", "Grocery", R.drawable.ic_launcher_foreground),
        Product("Onion", 35, "1 Kg", "Vegetables", R.drawable.ic_launcher_foreground),

        Product("Potato", 25, "1 Kg", "Vegetables", R.drawable.ic_launcher_foreground),
        Product("Apple Juice", 90, "1 Litre", "Beverages", R.drawable.ic_launcher_foreground),
        Product("Orange", 70, "1 Kg", "Fruits", R.drawable.ic_launcher_foreground),
        Product("Grapes", 110, "1 Kg", "Fruits", R.drawable.ic_launcher_foreground),
        Product("Mango", 150, "1 Kg", "Fruits", R.drawable.ic_launcher_foreground),

        Product("Eggs", 65, "12 Pcs", "Dairy", R.drawable.ic_launcher_foreground),
        Product("Butter", 120, "100 G", "Dairy", R.drawable.ic_launcher_foreground),
        Product("Cheese", 140, "200 G", "Dairy", R.drawable.ic_launcher_foreground),
        Product("Yogurt", 55, "500 G", "Dairy", R.drawable.ic_launcher_foreground),
        Product("Tea Powder", 100, "250 G", "Beverages", R.drawable.ic_launcher_foreground),

        Product("Coffee", 160, "200 G", "Beverages", R.drawable.ic_launcher_foreground),
        Product("Biscuits", 35, "1 Pack", "Bakery", R.drawable.ic_launcher_foreground),
        Product("Soap", 25, "1 Piece", "Household", R.drawable.ic_launcher_foreground),
        Product("Shampoo", 180, "200 Ml", "Household", R.drawable.ic_launcher_foreground),
        Product("Cooking Oil", 130, "1 Litre", "Grocery", R.drawable.ic_launcher_foreground),
    )

    init {
        loadProducts()
    }

    private var selectedCategory: String = "All"
    private var searchQuery: String = ""


    private fun loadProducts() {
        _products.value = list
    }

    fun filterByCategory(category: String?) {
        _products.value =
            if (category == null || category == "All") list
            else list.filter { it.category == category }
    }

    fun searchProducts(query: String) {
        searchQuery = query
        applyFilters()
    }

    private fun applyFilters() {

        val filtered = list.filter { product ->

            val matchesCategory =
                selectedCategory == "All" ||
                        product.category.equals(selectedCategory, ignoreCase = true)

            val matchesSearch =
                product.name.contains(searchQuery, ignoreCase = true)

            matchesCategory && matchesSearch
        }

        _products.value = filtered
    }
}