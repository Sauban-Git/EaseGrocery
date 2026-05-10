package com.ease.grocery.data.model

data class Product(
    val name: String,
    val price: Int,
    val unit: String,
    val category: String,
    val image: Int // drawable resource for now
)