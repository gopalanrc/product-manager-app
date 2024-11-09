package com.thales.productmanager.data

import com.thales.productmanager.data.local.model.Product

interface ProductRepository {
    suspend fun getAllProducts(): List<Product>

    suspend fun findProductsByName(name: String): List<Product>

    suspend fun addProduct(product: Product)

    suspend fun updateProduct(product: Product)

    suspend fun deleteProduct(product: Product)
}