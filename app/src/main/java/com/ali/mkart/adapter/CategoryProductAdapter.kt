package com.ali.mkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ali.mkart.activity.ProductDetailsActivity
import com.ali.mkart.databinding.ItemCategoryProductLayoutBinding
import com.ali.mkart.model.AddProductModel
import com.bumptech.glide.Glide

class CategoryProductAdapter(val context: Context, val list : ArrayList<AddProductModel>)
    :RecyclerView.Adapter<CategoryProductAdapter.CategoryProductViewHolder>(){
    inner class CategoryProductViewHolder(val binding: ItemCategoryProductLayoutBinding)
        :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryProductViewHolder {
        val binding = ItemCategoryProductLayoutBinding.inflate(LayoutInflater.from(context),parent,false)
        return CategoryProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return  list.size
    }

    override fun onBindViewHolder(holder: CategoryProductViewHolder, position: Int) {
       Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageView3)
        holder.binding.textView.text = list[position].productName
        holder.binding.textView4.text = list[position].productSp

        holder.itemView.setOnClickListener {
            val intent = Intent(context,ProductDetailsActivity::class.java)
            intent.putExtra("id",list[position].productId)
            context.startActivity(intent)
        }
    }
}