package com.example.lvlup.ui.cart

import com.example.lvlup.data.ProductEntity

data class CartItem(
    val producto: ProductEntity,  // producto del carrito
    var cantidad: Int             // cantidad seleccionada
)