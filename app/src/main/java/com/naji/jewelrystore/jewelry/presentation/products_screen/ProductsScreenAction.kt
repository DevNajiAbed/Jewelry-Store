package com.naji.jewelrystore.jewelry.presentation.products_screen

sealed interface ProductsScreenAction {
    data class AddProductToShoppingCart(val productId: String, val categoryId: String) : ProductsScreenAction
}