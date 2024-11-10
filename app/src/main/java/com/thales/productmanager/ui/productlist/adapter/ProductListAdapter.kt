package com.thales.productmanager.ui.productlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.thales.productmanager.data.local.model.Product
import com.thales.productmanager.databinding.ItemProductBinding
import com.thales.productmanager.ui.core.BaseRecyclerViewAdapter
import com.thales.productmanager.util.DIR_PRODUCT_IMAGE
import java.io.File

class ProductListAdapter(
    products: List<Product>
) : BaseRecyclerViewAdapter<Product, ItemProductBinding>(products) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val product = getItem(position)
        val context = holder.itemView.context
        val imageFileName = product.imageFileNames?.split(", ")?.firstOrNull()
        imageFileName?.let {
            Glide.with(holder.itemView).load(File(context.filesDir, DIR_PRODUCT_IMAGE + "/${product.id}/${imageFileName}")).into(binding.productImage)
        }
        binding.nameText.text = product.name
        binding.priceText.text = "$${String.format("%.2f", product.price)}"
    }

}