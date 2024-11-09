package com.thales.productmanager.data.local

import com.thales.productmanager.data.ProductRepository
import com.thales.productmanager.data.local.dao.ProductDao
import com.thales.productmanager.data.local.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductLocalRepository(private val productDao: ProductDao) : ProductRepository {
    override suspend fun getAllProducts(): List<Product> = withContext(Dispatchers.IO) {
        productDao.getAll()
    }

    override suspend fun findProductsByName(name: String): List<Product> = withContext(Dispatchers.IO) {
        productDao.findByName(name)
    }

    override suspend fun addProduct(product: Product) = withContext(Dispatchers.IO) {
        productDao.addAll(product)
    }

    override suspend fun updateProduct(product: Product) = withContext(Dispatchers.IO) {
        productDao.update(product)
    }

    override suspend fun deleteProduct(product: Product) = withContext(Dispatchers.IO) {
        productDao.delete(product)
    }

}