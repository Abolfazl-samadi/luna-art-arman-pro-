package com.example.ui.screens

import kotlinx.serialization.Serializable

@Serializable object HomeRoute
@Serializable object ProductsRoute
@Serializable data class ProductDetailRoute(val productId: String)
@Serializable object CartRoute
@Serializable object CheckoutRoute
@Serializable object AboutRoute
@Serializable object ChatRoute

@Serializable object AdminLoginRoute
@Serializable object AdminDashboardRoute
@Serializable data class AdminEditProductRoute(val productId: String?)
