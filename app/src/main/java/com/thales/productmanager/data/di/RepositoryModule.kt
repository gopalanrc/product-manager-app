package com.thales.productmanager.data.di

import com.thales.productmanager.data.ProductRepository
import com.thales.productmanager.data.local.dao.ProductDao
import com.thales.productmanager.data.local.ProductLocalRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductLocalRepository(productDao)
    }
}