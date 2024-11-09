package com.thales.productmanager.ui.productlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.thales.productmanager.R
import com.thales.productmanager.databinding.FragmentProductListBinding
import com.thales.productmanager.ui.productlist.adapter.ProductRecyclerViewAdapter
import com.thales.productmanager.ui.productlist.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView(): Unit = with(binding) {
//        with(productList) {
//            layoutManager = GridLayoutManager(context, 3)
//            adapter = ProductRecyclerViewAdapter(PlaceholderContent.ITEMS)
//        }
    }
}