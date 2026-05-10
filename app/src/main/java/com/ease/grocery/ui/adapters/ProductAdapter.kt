package com.ease.grocery.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ease.grocery.R
import com.ease.grocery.data.model.Product

class ProductAdapter(
    private var list: List<Product>
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imgProduct)
        val name: TextView = view.findViewById(R.id.tvProductName)
        val unit: TextView = view.findViewById(R.id.tvUnit)
        val price: TextView = view.findViewById(R.id.tvPrice)
        val btn: Button = view.findViewById(R.id.btnAdd)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.product_item, parent, false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val item = list[position]

        holder.name.text = item.name
        holder.unit.text = item.unit
        holder.price.text = "₹${item.price}"
        holder.img.setImageResource(item.image)

        holder.btn.setOnClickListener {
            holder.btn.text = "ADDED"
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<Product>) {
        list = newList
        notifyDataSetChanged()
    }
}