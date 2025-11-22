package com.example.lvlup.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvlup.repository.ProductApiService
import com.example.lvlup.repository.ProductRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Inicializa Retrofit y el API remota
        val retrofit = Retrofit.Builder()
            .baseUrl("https://TUBASEURL.com/") // Pon aquí la URL base correcta de tu backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val productApi = retrofit.create(ProductApiService::class.java)
        val productRepo = ProductRepository(productApi)

        return ProductListViewModel(productRepo) as T
    }
}
