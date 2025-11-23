package com.example.lvlup.ui.adminproductos

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvlup.repository.ProductRepository
import com.example.lvlup.repository.ProductApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminProductosViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val productApi = retrofit.create(ProductApiService::class.java)
        val productRepo = ProductRepository(productApi)
        return AdminProductosViewModel(productRepo) as T
    }
}

