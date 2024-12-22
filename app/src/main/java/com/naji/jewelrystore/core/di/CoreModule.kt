package com.naji.jewelrystore.core.di

import android.app.Application
import com.naji.jewelrystore.core.data.repository.remote.JewelryRepositoryImpl
import com.naji.jewelrystore.core.data.repository.local.LocalUserDataRepositoryImpl
import com.naji.jewelrystore.core.data.repository.remote.UserRepositoryImpl
import com.naji.jewelrystore.core.domain.repository.JewelryRepository
import com.naji.jewelrystore.core.domain.repository.LocalUserDataRepository
import com.naji.jewelrystore.core.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Singleton
    @Provides
    fun provideUserLocalDataRepository(application: Application): LocalUserDataRepository {
        return LocalUserDataRepositoryImpl(application)
    }

    @Singleton
    @Provides
    fun provideJewelryRepository(): JewelryRepository {
        return JewelryRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideUserRepository(): UserRepository {
        return UserRepositoryImpl()
    }
}