package com.thales.productmanager.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thales.productmanager.data.local.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    fun getAll(): List<Product>

    @Query("SELECT * FROM product WHERE name LIKE :name")
    fun findByName(name: String): List<Product>

    @Insert
    fun addAll(vararg product: Product)

    @Update
    fun update(product: Product)

    @Delete
    fun delete(product: Product)
}