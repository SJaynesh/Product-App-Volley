package com.rnwjs.product_app.adpater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.rnwjs.product_app.R
import com.rnwjs.product_app.databinding.ProductListBinding
import com.rnwjs.product_app.models.ProductModel

class ProductAdapter(private val productList: ArrayList<ProductModel>) :
    RecyclerView.Adapter<ProductAdapter.DataViewHolder>() {
    class DataViewHolder(itemView: View) : ViewHolder(itemView) {
        val binding: ProductListBinding = ProductListBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_list, parent, false)

        return DataViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.binding.titleId.setText(productList[position].title)
        holder.binding.descId.setText(productList[position].desc)

        Glide.with(holder.itemView.context).load(productList[position].image)
            .into(holder.binding.imageId)
    }
}