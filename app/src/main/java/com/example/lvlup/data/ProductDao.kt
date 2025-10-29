package com.example.lvlup.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<ProductEntity>>

    @Insert
    suspend fun insert(product: ProductEntity): Long // ← Para productos nuevos

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg products: ProductEntity) // ← Solo para demo/datos iniciales

    @Update
    suspend fun updateProduct(product: ProductEntity) // ← Update dedicado para editar

    @Delete
    suspend fun delete(product: ProductEntity)
}
