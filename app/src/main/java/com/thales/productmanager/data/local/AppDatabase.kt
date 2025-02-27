package com.thales.productmanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thales.productmanager.data.local.dao.ProductDao
import com.thales.productmanager.data.local.model.Product

@Database(entities = [Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}