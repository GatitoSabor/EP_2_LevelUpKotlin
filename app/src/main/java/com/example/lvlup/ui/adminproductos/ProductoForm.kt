package com.example.lvlup.ui.adminproductos

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.lvlup.data.ProductEntity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.Image
import coil.compose.rememberAsyncImagePainter
import androidx.core.net.toUri

@Composable
fun ProductoForm(
    producto: ProductEntity? = null,
    onSubmit: (ProductEntity) -> Unit,
    onCancel: () -> Unit
) {
    var nombre by remember { mutableStateOf(producto?.name ?: "") }
    var categoria by remember { mutableStateOf(producto?.category ?: "") }
    var marca by remember { mutableStateOf(producto?.brand ?: "") }
    var precio by remember { mutableStateOf(producto?.price?.toString() ?: "") }
    var descripcion by remember { mutableStateOf(producto?.description ?: "") }
    var descuento by remember { mutableStateOf(producto?.discountPercent?.toString() ?: "") }
    var error by remember { mutableStateOf("") }

    // Imagen
    var imageUri by remember { mutableStateOf<Uri?>(producto?.imageUrl?.toUri()) }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) imageUri = uri
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        OutlinedTextField(value = categoria, onValueChange = { categoria = it }, label = { Text("Categoría") })
        OutlinedTextField(value = marca, onValueChange = { marca = it }, label = { Text("Marca") })
        OutlinedTextField(value = precio, onValueChange = { precio = it }, label = { Text("Precio") })
        OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
        OutlinedTextField(value = descuento, onValueChange = { descuento = it }, label = { Text("Descuento (%)") })

        // Selector de imagen
        Button(
            onClick = { pickImageLauncher.launch("image/*") },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(if (imageUri != null) "Cambiar imagen" else "Seleccionar imagen")
        }

        // Mostrar preview de la imagen seleccionada
        imageUri?.let {
            Image(
                painter = rememberAsyncImagePainter(model = it),
                contentDescription = "Imagen del producto",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .padding(vertical = 8.dp)
            )
        }

        if (error.isNotEmpty())
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(4.dp))

        Row(Modifier.padding(top = 8.dp)) {
            Button(onClick = {
                val precioDouble = precio.toDoubleOrNull()
                val descuentoDouble = descuento.toDoubleOrNull()
                if (nombre.isBlank() || categoria.isBlank() || marca.isBlank() || precioDouble == null || precioDouble <= 0) {
                    error = "Completa los campos obligatorios y asegúrate de que el precio sea positivo."
                } else {
                    onSubmit(
                        ProductEntity(
                            id = producto?.id ?: 0,
                            name = nombre,
                            category = categoria,
                            brand = marca,
                            price = precioDouble,
                            description = descripcion,
                            imageUrl = imageUri?.toString(),
                            discountPercent = descuentoDouble
                        )
                    )
                }
            }) { Text(if (producto == null) "Agregar" else "Actualizar") }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = onCancel) { Text("Cancelar") }
        }
    }
}
