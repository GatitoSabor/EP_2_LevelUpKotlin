package com.example.lvlup

import ProductListViewModel
import ProfileViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.lvlup.repository.UserRepository
import com.example.lvlup.data.AppDatabase
import com.example.lvlup.data.productosDemo
import com.example.lvlup.repository.ProductRepository
import com.example.lvlup.repository.CouponRepository        // <-- IMPORTANTE!
import kotlinx.coroutines.flow.first
import com.example.lvlup.ui.cart.CartScreen
import com.example.lvlup.ui.cart.CartViewModel
import com.example.lvlup.ui.cart.CartViewModelFactory
import com.example.lvlup.ui.home.ProductListViewModelFactory
import com.example.lvlup.ui.login.LoginScreen
import com.example.lvlup.ui.login.LoginViewModel
import com.example.lvlup.ui.login.LoginViewModelFactory
import com.example.lvlup.ui.login.RegisterScreen
import com.example.lvlup.ui.theme.LvlUpTheme
import com.example.lvlup.screens.ComunidadScreen
import com.example.lvlup.ui.micuenta.ProfileViewModelFactory
import com.example.lvlup.util.MainAppWithDrawer
import com.example.lvlup.ui.puntos.PuntosViewModel
import com.example.lvlup.ui.puntos.PuntosViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "appdb")
            .fallbackToDestructiveMigration()
            .build()
        val userRepo = UserRepository(db.userDao())
        val productRepo = ProductRepository(db.productDao())
        val couponRepo = CouponRepository(db.couponDao())           // <-- CREA AQUÍ EL REPO

        setContent {
            LvlUpTheme {
                // SOLO AGREGA productos demo si la base está vacía
                androidx.compose.runtime.LaunchedEffect(Unit) {
                    val productosExistentes = productRepo.getProducts().first()
                    if (productosExistentes.isEmpty()) {
                        productRepo.insertAllDemo(productosDemo)
                    }
                }

                val navController = androidx.navigation.compose.rememberNavController()
                val loginVM: LoginViewModel = viewModel(factory = LoginViewModelFactory(userRepo))
                val productListVM: ProductListViewModel = viewModel(factory = ProductListViewModelFactory(productRepo))
                val cartVM: CartViewModel = viewModel(factory = CartViewModelFactory())
                val puntosVM: PuntosViewModel = viewModel(factory = PuntosViewModelFactory(couponRepo))

                // SUPONIENDO QUE TIENES EL ID DEL USUARIO LOGUEADO (por ejemplo: 1)
                val userIdLogueado = 1 // <-- Debe venir de sesión real
                val miCuentaVM: ProfileViewModel = viewModel(
                    factory = ProfileViewModelFactory(userRepo, couponRepo, userIdLogueado) // <-- AHORA SÍ, tres parámetros
                )

                MainAppWithDrawer(
                    navController = navController,
                    loginVM = loginVM,
                    productListVM = productListVM,
                    cartVM = cartVM,
                    puntosVM = puntosVM,
                    miCuentaVM = miCuentaVM
                )
            }
        }
    }
}
