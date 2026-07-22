package com.example.data

import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {
    val allCartItems: Flow<List<CartItem>> = cartDao.getAllCartItems()

    suspend fun updateQuantity(productId: String, quantity: Int) {
        if (quantity <= 0) {
            cartDao.deleteById(productId)
        } else {
            cartDao.insertOrUpdate(CartItem(productId, quantity))
        }
    }

    suspend fun deleteItem(productId: String) {
        cartDao.deleteById(productId)
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}
