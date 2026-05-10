package com.ease.grocery.ui.home

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ease.grocery.R
import com.ease.grocery.data.repository.CartRepository
import com.ease.grocery.ui.adapters.ProductAdapter
import com.ease.grocery.ui.cart.CartActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val search = findViewById<EditText>(R.id.etSearch)
        val recycler = findViewById<RecyclerView>(R.id.recyclerProducts)
        val chipGroup = findViewById<com.google.android.material.chip.ChipGroup>(R.id.chipGroupCategories)
        val scrollView = findViewById<HorizontalScrollView>(R.id.chipScroll)

        val btnCart = findViewById<Button>(R.id.btnCart)

        btnCart.setOnClickListener {

            startActivity(
                Intent(this, CartActivity::class.java)
            )
        }

        scrollView.post {
            val maxScroll = scrollView.getChildAt(0).width - scrollView.width

            val animator = ValueAnimator.ofInt(0, maxScroll / 4, 0)
            animator.duration = 1200
            animator.startDelay = 300
            animator.start()

            animator.addUpdateListener {
                scrollView.scrollTo(it.animatedValue as Int, 0)
            }
        }

        adapter = ProductAdapter(emptyList()) { product ->
            CartRepository.addToCart(product)
        }

        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = adapter

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        viewModel.products.observe(this) { list ->
            adapter.updateData(list)
        }

        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->

            val chip = checkedIds.firstOrNull()?.let { id ->
                findViewById<com.google.android.material.chip.Chip>(id)
            }

            val category = chip?.text?.toString() ?: "All"

            viewModel.filterByCategory(category)
        }
        search.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                viewModel.searchProducts(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }
}