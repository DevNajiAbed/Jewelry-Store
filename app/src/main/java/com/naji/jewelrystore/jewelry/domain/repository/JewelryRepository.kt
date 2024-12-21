package com.naji.jewelrystore.jewelry.domain.repository

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.jewelry.domain.model.Category
import com.naji.jewelrystore.jewelry.domain.model.Product

interface JewelryRepository {
    suspend fun getAllCategories(): Result<List<Category>>

    suspend fun getAllProductsOfCategory(categoryId: String): Result<List<Product>>

    suspend fun addProductToShoppingCart(
        userId: String,
        productId: String
    ) : Result<Boolean>
}