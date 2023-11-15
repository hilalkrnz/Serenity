package com.example.serenity.di.source

import com.example.serenity.data.source.local.SerenityLocalDataSource
import com.example.serenity.data.source.local.SerenityLocalDataSourceImpl
import com.example.serenity.data.source.remote.SerenityRemoteDataSource
import com.example.serenity.data.source.remote.SerenityRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSerenityRemoteDataSource(
        remoteDataSourceImpl: SerenityRemoteDataSourceImpl
    ): SerenityRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSerenityLocalDataSource(
        localDataSourceImpl: SerenityLocalDataSourceImpl
    ): SerenityLocalDataSource
}