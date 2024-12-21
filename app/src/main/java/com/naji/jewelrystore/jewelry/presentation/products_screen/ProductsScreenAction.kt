package com.naji.jewelrystore.jewelry.presentation.products_screen

sealed interface ProductsScreenAction {
    data class AddCategoryId(val categoryId: String) : ProductsScreenAction
    data class AddProductToShoppingCart(val productId: String) : ProductsScreenAction
}