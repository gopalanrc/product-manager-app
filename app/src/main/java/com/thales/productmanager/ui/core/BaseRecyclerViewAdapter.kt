package com.thales.productmanager.ui.core

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<Item, VB : ViewBinding>(private var items: List<Item>) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter<Item, VB>.ViewHolder>() {

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(position: Int): Item {
        return items[position]
    }

    fun removeItem(position: Int) {
        val updatedImageUris = items.toMutableList()
        updatedImageUris.removeAt(position)
        items = updatedImageUris.toList()
        notifyDataSetChanged()
    }

    fun updateItems(items: List<Item>) {
        this.items = items
        notifyDataSetChanged()
    }

    fun getItems(): List<Item> {
        return items.toList()
    }
}