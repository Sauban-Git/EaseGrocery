package com.ease.grocery.ui.order

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ease.grocery.R
import com.ease.grocery.ui.home.HomeActivity

class OrderSuccessActivity : AppCompatActivity() {

    private val viewModel: OrderSuccessViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_order_success)

        val tvOrderId = findViewById<TextView>(R.id.tvOrderId)
        val tvDeliveryTime = findViewById<TextView>(R.id.tvDeliveryTime)

        viewModel.orderId.observe(this) {
            tvOrderId.text = "Order ID: $it"
        }

        viewModel.deliveryTime.observe(this) {
            tvDeliveryTime.text = "Estimated Delivery: $it mins"
        }
        onBackPressedDispatcher.addCallback(this) {

            val intent = Intent(
                this@OrderSuccessActivity,
                HomeActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

            finish()
        }
    }


}