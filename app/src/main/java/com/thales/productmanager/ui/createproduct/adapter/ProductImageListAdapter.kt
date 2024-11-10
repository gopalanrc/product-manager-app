package com.thales.productmanager.ui.createproduct.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.thales.productmanager.databinding.ItemProductImageBinding
import com.thales.productmanager.ui.core.BaseRecyclerViewAdapter

class ProductImageListAdapter(imageUris: List<Uri>, private val callback: Callback) :
    BaseRecyclerViewAdapter<Uri, ItemProductImageBinding>(imageUris) {

    interface Callback {
        fun onRemoveImageBtnClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder = ViewHolder(ItemProductImageBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        setViewListener(viewHolder)
        return viewHolder
    }

    private fun setViewListener(viewHolder: ViewHolder) {
        viewHolder.binding.removeImageBtn.setOnClickListener {
            callback.onRemoveImageBtnClick(viewHolder.bindingAdapterPosition)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(getItem(position)).into(holder.binding.productImage)
    }
}