package com.naji.jewelrystore.core.domain.repository

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.jewelry.domain.model.Category
import com.naji.jewelrystore.core.domain.model.Product

interface JewelryRepository {
    suspend fun getAllCategories(): Result<List<Category>>

    suspend fun getAllProductsOfCategory(categoryId: String): Result<List<Product>>

    suspend fun addProductToShoppingCart(
        userId: String,
        productId: String,
        categoryId: String
    ) : Result<Boolean>

    suspend fun getShoppingCartProductList(userId: String) : Result<List<Product>>

    suspend fun orderProducts(
        userId: String,
        products: List<Product>
    ) : Result<Boolean>
}