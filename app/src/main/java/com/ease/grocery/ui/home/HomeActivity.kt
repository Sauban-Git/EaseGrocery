package com.ease.grocery.ui.home

import android.animation.ValueAnimator
import android.os.Bundle
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ease.grocery.R
import com.ease.grocery.ui.adapters.ProductAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.homescreen)

        val recycler = findViewById<RecyclerView>(R.id.recyclerProducts)
        val chipGroup = findViewById<com.google.android.material.chip.ChipGroup>(R.id.chipGroupCategories)
        val scrollView = findViewById<HorizontalScrollView>(R.id.chipScroll)

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

        adapter = ProductAdapter(emptyList())

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

    }
}