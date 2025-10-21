package com.example.lvlup.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun ContactoScreen(
    onBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showError by remember { mutableStateOf(false) }

    val camposValidos = nombre.isNotBlank() && email.isNotBlank() && mensaje.isNotBlank()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 8.dp,
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Contáctanos",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.height(16.dp))
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it; showError = false },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it; showError = false },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = mensaje,
                        onValueChange = { mensaje = it; showError = false },
                        label = { Text("¿En qué te podemos ayudar?") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        maxLines = 6
                    )

                    // Mensaje de error si intentan enviar con campos vacíos
                    if (showError && !camposValidos) {
                        Text(
                            "Debes completar todos los campos.",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (camposValidos) {
                                scope.launch {
                                    snackbarHostState.showSnackbar("¡Gracias por contactarnos! Te responderemos pronto.")
                                    nombre = ""
                                    email = ""
                                    mensaje = ""
                                    showError = false
                                }
                            } else {
                                showError = true
                            }
                        },
                        enabled = camposValidos,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Enviar")
                    }
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = onBack,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}
