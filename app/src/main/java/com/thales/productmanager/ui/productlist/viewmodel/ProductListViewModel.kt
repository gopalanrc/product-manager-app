package com.thales.productmanager.ui.productlist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thales.productmanager.data.ProductRepository
import com.thales.productmanager.data.local.model.Product
import com.thales.productmanager.ui.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private val _productListLiveData = MutableLiveData<UiState<List<Product>>>()
    val productListLiveData: LiveData<UiState<List<Product>>> = _productListLiveData

    fun loadProducts() {
        viewModelScope.launch {
            val products = productRepository.getAllProducts()
            _productListLiveData.value = UiState(products)
        }
    }

}