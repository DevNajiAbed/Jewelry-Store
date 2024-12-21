package com.naji.jewelrystore.jewelry.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
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
            val categories = mutableListOf<Category>()
            querySnapshot.documents.forEach { document ->
                document.toObject<Category>()?.let { category ->
                    category.id = document.id
                    categories.add(category)
                }
            }
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
            val products = mutableListOf<Product>()
            querySnapshot.documents.forEach { document ->
                document.toObject<Product>()?.let { product ->
                    product.id = document.id
                    products.add(product)
                }
            }
            Result.Success(products)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }

    override suspend fun addProductToShoppingCart(
        userId: String,
        productId: String
    ): Result<Boolean> {
        return try {
            var result: Result<Boolean>? = null
            firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .document(userId)
                .update(
                    Constants.Collection.Users.FIELD_SHOPPING_CART_LIST,
                    FieldValue.arrayUnion(productId)
                )
                .addOnSuccessListener {
                    result = Result.Success(true)
                }.addOnFailureListener {
                    result = Result.Failure(
                        it.localizedMessage ?: "Something went wrong. Try again later"
                    )
                }.await()
            return result!!
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }
}