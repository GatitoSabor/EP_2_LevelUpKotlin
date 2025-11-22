package com.example.lvlup.ui.adminproductos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvlup.data.ProductEntity
import com.example.lvlup.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminProductosViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _productos = MutableStateFlow<List<ProductEntity>>(emptyList())
    val productos: StateFlow<List<ProductEntity>> get() = _productos

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            val prodList = repository.getAllProductsFromBackend()
            _productos.value = prodList
        }
    }

    fun agregarProducto(producto: ProductEntity) {
        viewModelScope.launch {
            val nuevoProducto = repository.createProductOnBackend(producto)
            cargarProductos() // recarga desde backend
        }
    }

    fun actualizarProducto(producto: ProductEntity) {
        viewModelScope.launch {
            repository.updateProductOnBackend(producto)
            cargarProductos()
        }
    }

    fun eliminarProducto(producto: ProductEntity) {
        viewModelScope.launch {
            repository.deleteProductOnBackend(producto.id)
            cargarProductos()
        }
    }
}

