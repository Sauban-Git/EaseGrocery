package com.ease.grocery.ui.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class OrderSuccessViewModel : ViewModel() {

    private val _orderId = MutableLiveData<String>()
    val orderId: LiveData<String> = _orderId

    private val _deliveryTime = MutableLiveData<Int>()
    val deliveryTime: LiveData<Int> = _deliveryTime

    init {
        generateOrderDetails()
    }

    private fun generateOrderDetails() {

        val randomId =
            "EZ${System.currentTimeMillis()}${Random.nextInt(100, 999)}"

        _orderId.value = randomId

        _deliveryTime.value = Random.nextInt(8, 11)
    }
}