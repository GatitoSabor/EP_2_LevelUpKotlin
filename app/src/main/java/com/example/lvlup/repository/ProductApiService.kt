package com.example.lvlup.repository

import com.example.lvlup.data.ProductEntity
import retrofit2.http.*

interface ProductApiService {
    @GET("/products")
    suspend fun getProducts(): List<ProductEntity>
    @POST("/products")
    suspend fun createProduct(@Body product: ProductEntity): ProductEntity
    @PUT("/products/{id}")
    suspend fun updateProduct(@Path("id") id: Int, @Body product: ProductEntity)
    @DELETE("/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)
}


