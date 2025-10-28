package com.example.lvlup.ui.adminproductos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvlup.data.ProductDao
import com.example.lvlup.data.ProductEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminProductosViewModel(private val productDao: ProductDao) : ViewModel() {
    private val _productos = MutableStateFlow<List<ProductEntity>>(emptyList())
    val productos: StateFlow<List<ProductEntity>> get() = _productos

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            productDao.getProducts().collect { prodList ->
                _productos.value = prodList
            }
        }
    }

    fun agregarProducto(producto: ProductEntity) {
        viewModelScope.launch {
            productDao.insertAll(producto)
            cargarProductos()
        }
    }

    fun actualizarProducto(producto: ProductEntity) {
        viewModelScope.launch {
            productDao.insertAll(producto)
            cargarProductos()
        }
    }

    fun eliminarProducto(producto: ProductEntity) {
        viewModelScope.launch {
            // Usa tu método de delete si lo agregaste, si no, crea uno en ProductDao
            // Por ejemplo: productDao.delete(producto)
        }
    }
}
