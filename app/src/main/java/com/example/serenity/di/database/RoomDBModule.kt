package com.example.serenity.di.database

import android.content.Context
import androidx.room.Room
import com.example.serenity.data.database.ProductRoomDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDBModule {

    @Singleton
    @Provides
    fun provideProductRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ProductRoomDB::class.java, "products_room_database").build()

    @Singleton
    @Provides
    fun provideFavoriteProductDao(roomDB: ProductRoomDB) = roomDB.getFavoriteProductDao()
}