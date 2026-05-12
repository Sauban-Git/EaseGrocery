package com.ease.grocery.ui.cart

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ease.grocery.R
import com.ease.grocery.ui.adapters.CartAdapter
import com.ease.grocery.ui.checkout.CheckoutActivity

class CartActivity : AppCompatActivity() {

    private val viewModel: CartViewModel by viewModels()

    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_cart)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerCart)

        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        btnCheckout.setOnClickListener {
            if (viewModel.getTotalPrice() <= 0) {
                Toast.makeText(this, "No item is in cart to checkout", Toast.LENGTH_SHORT).show()
            } else {
                startActivity(
                    Intent(this, CheckoutActivity::class.java)
                )
            }
        }

        adapter = CartAdapter(
            emptyList(),
            onIncrease = {
                viewModel.increaseQty(it)
            },
            onDecrease = {
                viewModel.decreaseQty(it)
            },
            onRemove = {
                viewModel.removeItem(it)
            }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter

        viewModel.cartItems.observe(this) {

            adapter.updateData(it)

            tvTotal.text =
                "Total: ₹${viewModel.getTotalPrice()}"
        }
    }
}