package com.thales.productmanager.ui.productlist

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thales.productmanager.R
import com.thales.productmanager.data.local.model.Product
import com.thales.productmanager.databinding.FragmentProductListBinding
import com.thales.productmanager.ui.core.UiState
import com.thales.productmanager.ui.productlist.adapter.ProductListAdapter
import com.thales.productmanager.ui.productlist.viewmodel.ProductListViewModel
import com.thales.productmanager.util.showLongToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : Fragment() {

    companion object {
        private const val GRID_COLUMN_COUNT = 3
    }

    private lateinit var binding: FragmentProductListBinding
    private val productListAdapter = ProductListAdapter(listOf())
    private val viewModel: ProductListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        setViewModelObservers()
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadProducts()
    }

    private fun setViewModelObservers() {
        viewModel.productListLiveData.observe(viewLifecycleOwner) {
            handleProductUiState(it)
        }
    }

    private fun handleProductUiState(productUiState: UiState<List<Product>>) {
        productUiState.errorDetail?.let {
            requireContext().showLongToast(getString(R.string.msg_general_failure))
        } ?: run {
            productUiState.data?.let {
                productListAdapter.updateItems(it)
            }
        }
    }

    private fun initView(): Unit = with(binding) {
        with(productList) {
            layoutManager = GridLayoutManager(context, GRID_COLUMN_COUNT)
            adapter = productListAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    val spaceSize = resources.getDimensionPixelSize(R.dimen.product_image_spacing)
                    with(outRect) {
                        if (parent.getChildAdapterPosition(view) == 0) {
                            top = spaceSize
                        }
                        left = spaceSize
                        right = spaceSize
                        bottom = spaceSize
                    }
                }
            })
        }
    }
}