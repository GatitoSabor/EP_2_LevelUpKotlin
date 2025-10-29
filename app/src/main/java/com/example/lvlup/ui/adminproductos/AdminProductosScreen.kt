package com.example.lvlup.ui.adminproductos

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import com.example.lvlup.data.ProductEntity
import coil.compose.rememberAsyncImagePainter

@Composable
fun AdminProductosScreenWrapper() {
    val context = LocalContext.current.applicationContext
    val adminVM: AdminProductosViewModel = viewModel(factory = AdminProductosViewModelFactory(context))
    AdminProductosScreen(adminVM)
}

@Composable
fun AdminProductosScreen(viewModel: AdminProductosViewModel) {
    val productos by viewModel.productos.collectAsState()
    var productoEdit by remember { mutableStateOf<ProductEntity?>(null) }
    var mostrarForm by remember { mutableStateOf(false) }

    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 12.dp,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(18.dp)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(bottom = 60.dp)
                ) {
                    item {
                        Button(
                            onClick = { mostrarForm = true; productoEdit = null },
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                                .fillMaxWidth()
                        ) {
                            Text("Agregar producto")
                        }
                    }
                    item {
                        if (mostrarForm) {
                            ProductoForm(
                                producto = productoEdit,
                                onSubmit = { prod ->
                                    if (productoEdit == null)
                                        viewModel.agregarProducto(prod)
                                    else
                                        viewModel.actualizarProducto(prod)
                                    mostrarForm = false; productoEdit = null
                                },
                                onCancel = {
                                    mostrarForm = false; productoEdit = null
                                }
                            )
                        }
                    }
                    items(productos) { prod ->
                        Card(
                            modifier = Modifier
                                .padding(vertical = 6.dp)
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(6.dp),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // ——— IMAGEN DEL PRODUCTO (si existe) ———
                                if (!prod.imageUrl.isNullOrBlank()) {
                                    Image(
                                        painter = rememberAsyncImagePainter(prod.imageUrl),
                                        contentDescription = prod.name,
                                        modifier = Modifier
                                            .size(64.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .padding(end = 12.dp)
                                    )
                                }
                                Column(Modifier.weight(1f)) {
                                    Text(prod.name, style = MaterialTheme.typography.titleLarge)
                                    Text("${prod.category} - ${prod.brand} - \$${prod.price}")
                                }
                                Column {
                                    IconButton(
                                        onClick = { productoEdit = prod; mostrarForm = true }
                                    ) {
                                        Icon(Icons.Default.Edit, contentDescription = "Editar")
                                    }
                                    IconButton(
                                        onClick = { viewModel.eliminarProducto(prod) }
                                    ) {
                                        Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
