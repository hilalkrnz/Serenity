package com.example.serenity.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteProductEntity::class], version = 1, exportSchema = false)
abstract class ProductRoomDB : RoomDatabase() {
    abstract fun getFavoriteProductDao(): FavoriteProductDao
}