package com.naji.jewelrystore.shopping_cart.presentation.shopping_cart_screen

import com.naji.jewelrystore.core.domain.model.Product

data class ShoppingCartScreenState(
    val products: List<Product> = emptyList(),
    val selectedProducts: MutableList<Product> = mutableListOf(),
    val isLoading: Boolean = false,
    val error: String = ""
)
