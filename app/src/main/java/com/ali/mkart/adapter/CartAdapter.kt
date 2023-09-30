package com.ali.mkart.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.ali.mkart.activity.ProductDetailsActivity
import com.ali.mkart.databinding.LayoutCartItemBinding
import com.ali.mkart.roomdb.AppDatabase
import com.ali.mkart.roomdb.ProductModel
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(val context : Context, val list : List<ProductModel>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    inner class CartViewHolder(val binding : LayoutCartItemBinding) : RecyclerView.ViewHolder(binding.root)

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
     val binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context),parent,false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView4)
        holder.binding.textView6.text = list[position].productName
        holder.binding.textView9.text = list[position].productSp
holder.itemView.setOnClickListener {
    val intent = Intent(context, ProductDetailsActivity::class.java)
    intent.putExtra("id",list[position].productId)
    context.startActivity(intent)
}

        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.imageView5.setOnClickListener {
            GlobalScope.launch(Dispatchers.IO) {
           dao.deleteProduct(ProductModel(list[position].productId,list[position].productName,list[position].productImage,list[position].productSp))
            }
        }
    }
}