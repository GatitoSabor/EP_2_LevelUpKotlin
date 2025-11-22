package com.example.lvlup.repository

import com.example.lvlup.data.ProductEntity
class ProductRepository(private val api: ProductApiService) {
    suspend fun getAllProductsFromBackend() = api.getProducts()
    suspend fun createProductOnBackend(product: ProductEntity) = api.createProduct(product)
    suspend fun updateProductOnBackend(product: ProductEntity) = api.updateProduct(product.id, product)
    suspend fun deleteProductOnBackend(id: Int) = api.deleteProduct(id)
}
