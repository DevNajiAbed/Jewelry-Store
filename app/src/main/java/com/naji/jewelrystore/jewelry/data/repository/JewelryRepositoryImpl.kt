package com.naji.jewelrystore.jewelry.data.repository

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.naji.jewelrystore.core.data.Constants
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.jewelry.domain.model.Category
import com.naji.jewelrystore.jewelry.domain.model.Product
import com.naji.jewelrystore.jewelry.domain.repository.JewelryRepository
import kotlinx.coroutines.tasks.await

class JewelryRepositoryImpl : JewelryRepository {
    private val firestore = Firebase.firestore

    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val querySnapshot = firestore.collection(Constants.Collection.Categories.COLLECTION_NAME)
                .get()
                .await()
            val categories = querySnapshot.toObjects(Category::class.java)
            Result.Success(categories)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }

    override suspend fun getAllProductsOfCategory(categoryId: String): Result<List<Product>> {
        return try {
            val querySnapshot = firestore.collection(Constants.Collection.Categories.COLLECTION_NAME)
                .document(categoryId)
                .collection(Constants.Collection.Products.COLLECTION_NAME)
                .get()
                .await()
            val products = querySnapshot.toObjects(Product::class.java)
            Result.Success(products)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }
}