package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.Product
import com.example.data.SampleData
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminLoginScreen(onLoginSuccess: () -> Unit, onBack: () -> Unit) {
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ورود مدیریت") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "پنل مدیریت لونا آرت",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(32.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    error = false
                },
                label = { Text("رمز عبور") },
                visualTransformation = PasswordVisualTransformation(),
                isError = error,
                modifier = Modifier.fillMaxWidth()
            )
            if (error) {
                Text("رمز عبور اشتباه است", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (password == "1234") {
                        onLoginSuccess()
                    } else {
                        error = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("ورود", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminDashboardScreen(
    onNavigateToEdit: (String?) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("مدیریت محصولات") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "خروج")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToEdit(null) }, containerColor = MaterialTheme.colorScheme.primary) {
                Icon(Icons.Default.Add, contentDescription = "افزودن محصول")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(SampleData.products) { product ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(product.name, fontWeight = FontWeight.Bold)
                            Text(product.priceStr, color = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onNavigateToEdit(product.id) }) {
                            Icon(Icons.Default.Edit, contentDescription = "ویرایش")
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminEditProductScreen(
    productId: String?,
    onBack: () -> Unit
) {
    val isEditing = productId != null
    val existingProduct = if (isEditing) SampleData.products.find { it.id == productId } else null

    var name by remember { mutableStateOf(existingProduct?.name ?: "") }
    var priceStr by remember { mutableStateOf(existingProduct?.priceStr ?: "") }
    var shortDesc by remember { mutableStateOf(existingProduct?.shortDesc ?: "") }
    var fullDesc by remember { mutableStateOf(existingProduct?.fullDesc ?: "") }
    var categoryId by remember { mutableStateOf(existingProduct?.categoryId ?: SampleData.categories.first().id) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditing) "ویرایش محصول" else "افزودن محصول") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("نام محصول") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = priceStr,
                onValueChange = { priceStr = it },
                label = { Text("قیمت (مثلاً ۱۵۰,۰۰۰ تومان)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = shortDesc,
                onValueChange = { shortDesc = it },
                label = { Text("توضیح کوتاه") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = fullDesc,
                onValueChange = { fullDesc = it },
                label = { Text("توضیح کامل") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    if (isEditing && existingProduct != null) {
                        val index = SampleData.products.indexOf(existingProduct)
                        if (index != -1) {
                            SampleData.products[index] = existingProduct.copy(
                                name = name,
                                priceStr = priceStr,
                                shortDesc = shortDesc,
                                fullDesc = fullDesc,
                                categoryId = categoryId
                            )
                        }
                    } else {
                        val newProduct = Product(
                            id = "p_${UUID.randomUUID().toString().take(6)}",
                            name = name,
                            priceStr = priceStr,
                            shortDesc = shortDesc,
                            fullDesc = fullDesc,
                            imageResId = R.drawable.img_placeholder_stone_1784754783098, // Default placeholder
                            categoryId = categoryId,
                            materials = "متریال پیش‌فرض",
                            usage = "استفاده پیش‌فرض"
                        )
                        SampleData.products.add(newProduct)
                    }
                    onBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("ذخیره محصول", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
