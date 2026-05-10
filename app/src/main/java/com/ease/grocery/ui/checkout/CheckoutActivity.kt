package com.ease.grocery.ui.checkout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ease.grocery.R
import com.ease.grocery.ui.order.OrderSuccessActivity

class CheckoutActivity : AppCompatActivity() {

    private val viewModel: CheckoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_checkout)

        val tvItemsTotal = findViewById<TextView>(R.id.tvItemsTotal)
        val tvDelivery = findViewById<TextView>(R.id.tvDelivery)
        val tvGrandTotal = findViewById<TextView>(R.id.tvGrandTotal)

        val etName = findViewById<EditText>(R.id.etName)
        val etAddress = findViewById<EditText>(R.id.etAddress)
        val etPhone = findViewById<EditText>(R.id.etPhone)

        val btnPlaceOrder = findViewById<Button>(R.id.btnPlaceOrder)

        viewModel.totalPrice.observe(this) { total ->

            val delivery = 40
            val grandTotal = total + delivery

            tvItemsTotal.text = "₹$total"
            tvDelivery.text = "₹$delivery"
            tvGrandTotal.text = "₹$grandTotal"
        }

        btnPlaceOrder.setOnClickListener {

            val name = etName.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val phone = etPhone.text.toString().trim()

            if (name.isEmpty() || address.isEmpty() || phone.isEmpty()) {

                Toast.makeText(
                    this,
                    "Please fill all details",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            viewModel.placeOrder()

            startActivity(
                Intent(this, OrderSuccessActivity::class.java)
            )

            finish()
        }
    }
}