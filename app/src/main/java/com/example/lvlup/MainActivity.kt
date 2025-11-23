package com.example.lvlup

import ProfileViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lvlup.repository.CouponRepository
import com.example.lvlup.repository.CouponApiService
import com.example.lvlup.repository.ProductApiService
import com.example.lvlup.repository.ProductRepository
import com.example.lvlup.repository.UserApiService
import com.example.lvlup.repository.UserRepository
import com.example.lvlup.ui.cart.CartViewModel
import com.example.lvlup.ui.cart.CartViewModelFactory
import com.example.lvlup.ui.home.ProductListViewModel
import com.example.lvlup.ui.home.ProductListViewModelFactory
import com.example.lvlup.ui.login.LoginViewModel
import com.example.lvlup.ui.login.LoginViewModelFactory
import com.example.lvlup.ui.micuenta.ProfileViewModelFactory
import com.example.lvlup.ui.puntos.PuntosViewModelFactory
import com.example.lvlup.ui.theme.LvlUpTheme
import com.example.lvlup.util.MainAppWithDrawer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrofit construido con tu URL para AWS/Spring Boot backend
        val retrofit = Retrofit.Builder()
            .baseUrl("http://localhost:8080") // Reemplaza por la URL real de tu backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Instancias de los servicios de API
        val productApi = retrofit.create(ProductApiService::class.java)
        val userApi = retrofit.create(UserApiService::class.java)
        val couponApi = retrofit.create(CouponApiService::class.java)

        // Repositorios conectados vía Retrofit/API REST
        val productRepo = ProductRepository(productApi)
        val userRepo = UserRepository(userApi)
        val couponRepo = CouponRepository(couponApi)

        setContent {
            LvlUpTheme {
                val navController = androidx.navigation.compose.rememberNavController()
                val loginVM: LoginViewModel = viewModel(factory = LoginViewModelFactory(userRepo, applicationContext))
                val productListVM: ProductListViewModel = viewModel(factory = ProductListViewModelFactory(applicationContext))
                val cartVM: CartViewModel = viewModel(factory = CartViewModelFactory())
                val puntosVM: com.example.lvlup.ui.puntos.PuntosViewModel = viewModel(factory = PuntosViewModelFactory(couponRepo))

                val prefs = getSharedPreferences("lvlup_prefs", MODE_PRIVATE)
                val usuarioIdGuardado = prefs.getInt("usuario_id", -1)

                val miCuentaVM: ProfileViewModel = viewModel(
                    factory = ProfileViewModelFactory(userRepo, couponRepo, usuarioIdGuardado)
                )

                MainAppWithDrawer(
                    navController = navController,
                    loginVM = loginVM,
                    productListVM = productListVM,
                    cartVM = cartVM,
                    puntosVM = puntosVM,
                    miCuentaVM = miCuentaVM,
                    usuarioIdGuardado = usuarioIdGuardado
                )
            }
        }
    }
}
