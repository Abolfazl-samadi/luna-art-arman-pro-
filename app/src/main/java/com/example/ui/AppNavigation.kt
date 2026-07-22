package com.example.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.data.DatabaseProvider
import com.example.data.CartRepository
import com.example.ui.screens.*

@Composable
fun LunaArtApp() {
    val context = LocalContext.current
    val database = DatabaseProvider.getDatabase(context)
    val repository = CartRepository(database.cartDao())
    val shopViewModel: ShopViewModel = viewModel(factory = ShopViewModelFactory(repository))
    val chatViewModel: ChatViewModel = viewModel()
    
    val navController = rememberNavController()
    
    Scaffold(
        bottomBar = {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute?.contains("ProductDetailRoute") != true && currentRoute != "com.example.ui.screens.CheckoutRoute") {
                NavigationBar {
                    NavigationBarItem(
                        selected = currentRoute == "com.example.ui.screens.HomeRoute",
                        onClick = { navController.navigate(HomeRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("خانه") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "com.example.ui.screens.ProductsRoute",
                        onClick = { navController.navigate(ProductsRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Products") },
                        label = { Text("محصولات") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "com.example.ui.screens.CartRoute",
                        onClick = { navController.navigate(CartRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                        label = { Text("سبد خرید") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "com.example.ui.screens.ChatRoute",
                        onClick = { navController.navigate(ChatRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = "Chat") },
                        label = { Text("پشتیبانی") }
                    )
                    NavigationBarItem(
                        selected = currentRoute == "com.example.ui.screens.AboutRoute",
                        onClick = { navController.navigate(AboutRoute) { launchSingleTop = true } },
                        icon = { Icon(Icons.Default.Info, contentDescription = "About") },
                        label = { Text("درباره ما") }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<HomeRoute> {
                HomeScreen(
                    viewModel = shopViewModel,
                    onNavigateToProduct = { productId -> navController.navigate(ProductDetailRoute(productId)) },
                    onNavigateToCategory = { categoryId -> 
                        shopViewModel.selectCategory(categoryId)
                        navController.navigate(ProductsRoute)
                    },
                    onNavigateToAdmin = { navController.navigate(AdminLoginRoute) }
                )
            }
            composable<ProductsRoute> {
                ProductsScreen(
                    viewModel = shopViewModel,
                    onNavigateToProduct = { productId -> navController.navigate(ProductDetailRoute(productId)) }
                )
            }
            composable<ProductDetailRoute> { backStackEntry ->
                val detailRoute = backStackEntry.toRoute<ProductDetailRoute>()
                ProductDetailScreen(
                    productId = detailRoute.productId,
                    viewModel = shopViewModel,
                    onBack = { navController.navigateUp() }
                )
            }
            composable<CartRoute> {
                CartScreen(
                    viewModel = shopViewModel,
                    onNavigateToCheckout = { navController.navigate(CheckoutRoute) }
                )
            }
            composable<CheckoutRoute> {
                CheckoutScreen(
                    viewModel = shopViewModel,
                    onOrderComplete = {
                        navController.navigate(HomeRoute) {
                            popUpTo(HomeRoute) { inclusive = false }
                        }
                    }
                )
            }
            composable<ChatRoute> {
                ChatScreen(viewModel = chatViewModel)
            }
            composable<AboutRoute> {
                AboutScreen()
            }
            composable<AdminLoginRoute> {
                AdminLoginScreen(
                    onLoginSuccess = {
                        navController.navigate(AdminDashboardRoute) {
                            popUpTo(AdminLoginRoute) { inclusive = true }
                        }
                    },
                    onBack = { navController.navigateUp() }
                )
            }
            composable<AdminDashboardRoute> {
                AdminDashboardScreen(
                    onNavigateToEdit = { productId ->
                        navController.navigate(AdminEditProductRoute(productId))
                    },
                    onBack = { navController.navigateUp() }
                )
            }
            composable<AdminEditProductRoute> { backStackEntry ->
                val route = backStackEntry.toRoute<AdminEditProductRoute>()
                AdminEditProductScreen(
                    productId = route.productId,
                    onBack = { navController.navigateUp() }
                )
            }
        }
    }
}
