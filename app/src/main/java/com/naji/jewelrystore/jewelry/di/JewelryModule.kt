package com.naji.jewelrystore.jewelry.di

import com.naji.jewelrystore.jewelry.data.repository.JewelryRepositoryImpl
import com.naji.jewelrystore.jewelry.domain.repository.JewelryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JewelryModule {

    @Singleton
    @Provides
    fun provideJewelryRepository(): JewelryRepository {
        return JewelryRepositoryImpl()
    }
}