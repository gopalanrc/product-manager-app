package com.thales.productmanager.ui.createproduct.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.thales.productmanager.databinding.ItemProductImageBinding
import com.thales.productmanager.ui.createproduct.adapter.ProductImageListAdapter.ProductImageViewHolder

class ProductImageListAdapter(imageUris: List<Uri>, private val callback: Callback) : RecyclerView.Adapter<ProductImageViewHolder>() {

    interface Callback {
        fun onRemoveImageBtnClick(position: Int)
    }

    private var imageUris: List<Uri>

    init {
        // Copy to a new collection to avoid outside manipulation
        this.imageUris = imageUris.toList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductImageViewHolder {
        return ProductImageViewHolder(ItemProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return imageUris.size
    }

    fun removeImage(position: Int) {
        val updatedImageUris = imageUris.toMutableList()
        updatedImageUris.removeAt(position)
        imageUris = updatedImageUris.toList()
        notifyDataSetChanged()
    }

    fun updateImageUris(imageUris: List<Uri>) {
        this.imageUris = imageUris
        notifyDataSetChanged()
    }

    fun getImageUris(): List<Uri> {
        return imageUris.toList()
    }

    override fun onBindViewHolder(holder: ProductImageViewHolder, position: Int) {
        Glide.with(holder.itemView).load(imageUris[position]).into(holder.binding.productImage)
    }

    inner class ProductImageViewHolder(val binding: ItemProductImageBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.removeImageBtn.setOnClickListener {
                callback.onRemoveImageBtnClick(bindingAdapterPosition)
            }
        }
    }
}