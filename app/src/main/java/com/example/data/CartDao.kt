package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: CartItem)

    @Query("DELETE FROM cart_items WHERE productId = :productId")
    suspend fun deleteById(productId: String)

    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
