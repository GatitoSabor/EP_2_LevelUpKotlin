package com.example.lvlup.ui.home

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.*
import androidx.lifecycle.viewModelScope
import com.example.lvlup.data.ProductEntity
import com.example.lvlup.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(private val repo: ProductRepository) : ViewModel() {
    var category by mutableStateOf("")

    public val _products = MutableStateFlow<List<ProductEntity>>(emptyList())
    val products: StateFlow<List<ProductEntity>> get() = _products

    init {
        cargarProductos()
    }

    fun cargarProductos() {
        viewModelScope.launch {
            val productosRemotos = repo.getAllProductsFromBackend()
            _products.value = if (category.isEmpty()) {
                productosRemotos
            } else {
                productosRemotos.filter { it.category == category }
            }
        }
    }

    fun actualizarCategoria(nuevaCategoria: String) {
        category = nuevaCategoria
        cargarProductos()
    }
}