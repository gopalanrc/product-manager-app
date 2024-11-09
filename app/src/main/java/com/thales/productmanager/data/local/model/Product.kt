package com.thales.productmanager.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "product")
data class Product(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val name: String?,
    val type: String?,
    val description: String?,
    @ColumnInfo(name = "image_id") val imageId: String?,
    val price: Double?
)
