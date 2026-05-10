package com.ease.grocery.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ease.grocery.R
import com.ease.grocery.data.model.CartItem

class CartAdapter(
    private var list: List<CartItem>,
    private val onIncrease: (CartItem) -> Unit,
    private val onDecrease: (CartItem) -> Unit,
    private val onRemove: (CartItem) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val img: ImageView = view.findViewById(R.id.imgCartProduct)

        val name: TextView = view.findViewById(R.id.tvCartName)

        val price: TextView = view.findViewById(R.id.tvCartPrice)

        val qty: TextView = view.findViewById(R.id.tvQty)

        val btnPlus: ImageButton = view.findViewById(R.id.btnPlus)

        val btnMinus: ImageButton = view.findViewById(R.id.btnMinus)

        val btnRemove: TextView = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_item, parent, false)

        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {

        val item = list[position]

        holder.name.text = item.product.name

        holder.price.text =
            "₹${item.product.price * item.quantity}"

        holder.qty.text = item.quantity.toString()

        holder.img.setImageResource(item.product.image)

        holder.btnPlus.setOnClickListener {
            onIncrease(item)
        }

        holder.btnMinus.setOnClickListener {
            onDecrease(item)
        }

        holder.btnRemove.setOnClickListener {
            onRemove(item)
        }
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<CartItem>) {
        list = newList
        notifyDataSetChanged()
    }
}