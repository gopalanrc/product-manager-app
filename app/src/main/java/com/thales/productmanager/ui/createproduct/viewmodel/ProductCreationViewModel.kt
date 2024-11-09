package com.thales.productmanager.ui.createproduct.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thales.productmanager.data.ProductRepository
import com.thales.productmanager.data.local.model.Product
import com.thales.productmanager.ui.createproduct.state.ProductCreationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductCreationViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _productCreationUiState = MutableLiveData<ProductCreationUiState>()
    val productCreationUiState: LiveData<ProductCreationUiState> = _productCreationUiState
    fun createProduct(product: Product) {
        viewModelScope.launch {
            productRepository.addProduct(product)
            _productCreationUiState.value = ProductCreationUiState()
        }
    }
}