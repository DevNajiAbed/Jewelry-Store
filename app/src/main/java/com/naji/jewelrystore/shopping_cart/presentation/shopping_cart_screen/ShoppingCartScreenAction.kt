package com.naji.jewelrystore.shopping_cart.presentation.shopping_cart_screen

import com.naji.jewelrystore.core.domain.model.Product

sealed interface ShoppingCartScreenAction {
    data class OnProductSelectChange(
        val product: Product,
        val checked: Boolean
    ) : ShoppingCartScreenAction

    data object OrderViaWhatsApp : ShoppingCartScreenAction
}