package com.naji.jewelrystore.jewelry.presentation.products_screen

import com.naji.jewelrystore.core.domain.model.Product

data class ProductsScreenState(
    val products: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
