package com.example.serenity.di.repository

import com.example.serenity.data.repository.FavoriteProductRepositoryImpl
import com.example.serenity.data.repository.SerenityRepositoryImpl
import com.example.serenity.domain.repository.FavoriteProductRepository
import com.example.serenity.domain.repository.SerenityRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSerenityRepository(
        serenityRepositoryImpl: SerenityRepositoryImpl
    ): SerenityRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteProductRepository(
        favoriteProductRepositoryImpl: FavoriteProductRepositoryImpl
    ): FavoriteProductRepository
}