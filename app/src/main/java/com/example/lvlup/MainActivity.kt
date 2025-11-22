package com.example.lvlup

import ProfileViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lvlup.repository.CouponRepository
import com.example.lvlup.repository.ProductApiService
import com.example.lvlup.repository.ProductRepository
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

        // Instancia Retrofit para productos vía AWS/backend REST
        val retrofit = Retrofit.Builder()
            .baseUrl("https://TUBASEURL.com/") // Reemplaza por tu URL de backend real
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val productApi = retrofit.create(ProductApiService::class.java)
        val productRepo = ProductRepository(productApi)

        // Usuarios y cupones siguen usando Room/DAO local
        val db = com.example.lvlup.data.DatabaseProvider.getInstance(applicationContext)
        val userRepo = UserRepository(db.userDao())
        val couponRepo = CouponRepository(db.couponDao())

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
