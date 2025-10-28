package com.example.lvlup.ui.adminproductos

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lvlup.data.ProductEntity

@Composable
fun AdminProductosScreen(viewModel: AdminProductosViewModel) {
    val productos by viewModel.productos.collectAsState()
    var productoEdit by remember { mutableStateOf<ProductEntity?>(null) }
    var mostrarForm by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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
                        mostrarForm = false
                        productoEdit = null
                    },
                    onCancel = {
                        mostrarForm = false
                        productoEdit = null
                    }
                )
            }
        }
        items(productos) { prod ->
            Card(modifier = Modifier
                .padding(vertical = 4.dp)
                .fillMaxWidth()
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    Column(Modifier.weight(1f)) {
                        Text(prod.name, style = MaterialTheme.typography.titleLarge)
                        Text("${prod.category} - ${prod.brand} - \$${prod.price}")
                    }
                    Column {
                        IconButton(onClick = { productoEdit = prod; mostrarForm = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Editar")
                        }
                        IconButton(onClick = { viewModel.eliminarProducto(prod) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
