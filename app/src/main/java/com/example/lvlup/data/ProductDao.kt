package com.example.lvlup.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getProducts(): Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg products: ProductEntity)

    @Delete
    suspend fun delete(product: ProductEntity)

}
