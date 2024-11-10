package com.thales.productmanager.ui.createproduct.viewmodel

import android.content.ContentResolver
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thales.productmanager.data.ProductRepository
import com.thales.productmanager.data.local.model.Product
import com.thales.productmanager.ui.core.UiState
import com.thales.productmanager.util.copyFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ProductCreationViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {
    private val _productCreationUiState = MutableLiveData<UiState<Boolean>>()
    val productCreationUiState: LiveData<UiState<Boolean>> = _productCreationUiState
    fun createProduct(product: Product, imageUris: List<Uri>, contentResolver: ContentResolver, imageDir: File) {
        viewModelScope.launch {
            // Save images to local storage
            val productImageDir = File(imageDir, product.id)
            productImageDir.mkdirs()
            val imageFileNames = saveProductImages(imageUris, contentResolver, productImageDir)
            productRepository.addProduct(product.copy(imageFileNames = imageFileNames.joinToString { it }))
            _productCreationUiState.value = UiState(true)
        }
    }

    private fun saveProductImages(imageUris: List<Uri>, contentResolver: ContentResolver, imageDir: File): List<String> {
        val productImageFileNames = mutableListOf<String>()
        imageUris.forEach { uri ->
            val imageFileName = UUID.randomUUID().toString() + ".jpg"
            copyFile(uri, contentResolver, File(imageDir, imageFileName))
            productImageFileNames.add(imageFileName)
        }

        return productImageFileNames
    }
}