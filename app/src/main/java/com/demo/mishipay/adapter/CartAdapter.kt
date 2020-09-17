package com.demo.mishipay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.mishipay.R
import com.demo.mishipay.databinding.CartItemBinding
import com.demo.mishipay.model.Cart
import com.demo.mishipay.model.Product
import com.demo.mishipay.model.Store

class CartAdapter(private val cart: Cart): RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = CartItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(
            itemBinding
        )
    }

    override fun getItemCount(): Int {
        return cart.getProducts().size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(product = cart.getProducts()[position])
    }

    class ViewHolder(private var cartItemBinding: CartItemBinding) :
        RecyclerView.ViewHolder(cartItemBinding.root) {

        fun bind(product: Product) {
            cartItemBinding.product = product
            cartItemBinding.executePendingBindings()
            cartItemBinding.image.setImageResource(product.imageURl)
        }
    }

}