package com.example.serenity.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToFavorite(product: FavoriteProductEntity)

    @Query("SELECT * FROM favorite_product")
    fun getFavoriteProducts(): List<FavoriteProductEntity>

    @Query("SELECT count(*) FROM favorite_product WHERE product_id = :id")
    suspend fun checkFavoriteProduct(id: Int): Int

    @Query("DELETE FROM favorite_product WHERE product_id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}