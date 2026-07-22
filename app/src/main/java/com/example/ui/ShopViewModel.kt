package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.CartItem
import com.example.data.CartRepository
import com.example.data.Category
import com.example.data.Product
import com.example.data.SampleData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.compose.runtime.snapshotFlow

class ShopViewModel(private val repository: CartRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory = _selectedCategory.asStateFlow()

    val categories: List<Category> = SampleData.categories
    
    // popularProducts can remain a property that reads the current state
    val popularProducts: List<Product>
        get() = SampleData.products.take(3)

    val products: StateFlow<List<Product>> = combine(
        _searchQuery, 
        _selectedCategory,
        snapshotFlow { SampleData.products.toList() }
    ) { query, category, productList ->
        var list: List<Product> = productList
        if (category != null) {
            list = list.filter { it.categoryId == category }
        }
        if (query.isNotBlank()) {
            list = list.filter { it.name.contains(query, ignoreCase = true) }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SampleData.products.toList())

    val cartItems: StateFlow<List<CartItem>> = repository.allCartItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(categoryId: String?) {
        _selectedCategory.value = categoryId
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            val currentItem = cartItems.value.find { it.productId == productId }
            val currentQty = currentItem?.quantity ?: 0
            repository.updateQuantity(productId, currentQty + 1)
        }
    }

    fun updateCartQuantity(productId: String, quantity: Int) {
        viewModelScope.launch {
            repository.updateQuantity(productId, quantity)
        }
    }

    fun removeFromCart(productId: String) {
        viewModelScope.launch {
            repository.deleteItem(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
