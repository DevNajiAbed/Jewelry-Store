package com.naji.jewelrystore.core.data.repository.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.naji.jewelrystore.core.data.Constants
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.jewelry.domain.model.Category
import com.naji.jewelrystore.core.domain.model.Product
import com.naji.jewelrystore.core.domain.repository.JewelryRepository
import kotlinx.coroutines.tasks.await

class JewelryRepositoryImpl : JewelryRepository {
    private val firestore = Firebase.firestore

    override suspend fun getAllCategories(): Result<List<Category>> {
        return try {
            val querySnapshot =
                firestore.collection(Constants.Collection.Categories.COLLECTION_NAME)
                    .get()
                    .await()
            val categories = mutableListOf<Category>()
            querySnapshot.documents.forEach { document ->
                categories.add(
                    Category(
                        id = document.id,
                        name = document.getString(Constants.Collection.Categories.FIELD_NAME) ?: "",
                        imageUrl = document.getString(Constants.Collection.Categories.FIELD_IMAGE_URL)
                            ?: ""
                    )
                )
            }
            Result.Success(categories)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }

    override suspend fun getAllProductsOfCategory(categoryId: String): Result<List<Product>> {
        return try {
            val querySnapshot =
                firestore.collection(Constants.Collection.Categories.COLLECTION_NAME)
                    .document(categoryId)
                    .collection(Constants.Collection.Products.COLLECTION_NAME)
                    .get()
                    .await()
            val products = mutableListOf<Product>()
            querySnapshot.documents.forEach { document ->
                products.add(
                    Product(
                        id = document.id,
                        name = document.getString(Constants.Collection.Products.FIELD_NAME) ?: "",
                        imageUrl = document.getString(Constants.Collection.Products.FIELD_IMAGE_URL)
                            ?: "",
                        price = document.getDouble(Constants.Collection.Products.FIELD_PRICE)
                            ?.toFloat() ?: 0f,
                        categoryId = categoryId
                    )
                )
            }
            Result.Success(products)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }

    override suspend fun addProductToShoppingCart(
        userId: String,
        productId: String,
        categoryId: String
    ): Result<Boolean> {
        return try {
            var result: Result<Boolean>? = null
            firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .document(userId)
                .update(
                    Constants.Collection.Users.FIELD_SHOPPING_CART_LIST,
                    FieldValue.arrayUnion("$productId,$categoryId")
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

    override suspend fun getShoppingCartProductList(userId: String): Result<List<Product>> {
        return try {
            var result: Result<List<Product>>
            var shoppingList: List<String>? = null
            firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .document(userId)
                .get()
                .addOnSuccessListener {
                    shoppingList =
                        (it.get(Constants.Collection.Users.FIELD_SHOPPING_CART_LIST) as List<String>)/*.also { shoppingList ->
                        shoppingList.forEach { item ->
                            val splitItem = item.split(",")
                            val productId = splitItem[0]
                            val categoryId = splitItem[1]
                            firestore.collection(Constants.Collection.Categories.COLLECTION_NAME)
                                .document(categoryId)
                                .collection(Constants.Collection.Products.COLLECTION_NAME)
                                .document(productId)
                                .get()
                                .addOnSuccessListener {

                                }.addOnFailureListener {

                                }.await()
                        }
                    }*/
                }.addOnFailureListener {
                    result = Result.Failure(
                        it.localizedMessage ?: "Something went wrong. Try again later"
                    )
                }.await()

            val productList = mutableListOf<Product>()
            shoppingList?.forEach { item ->
                val splitItem = item.split(",")
                val productId = splitItem[0]
                val categoryId = splitItem[1]
                firestore.collection(Constants.Collection.Categories.COLLECTION_NAME)
                    .document(categoryId)
                    .collection(Constants.Collection.Products.COLLECTION_NAME)
                    .document(productId)
                    .get()
                    .addOnSuccessListener { document ->
                        document.toObject<Product>()?.let {
                            it.id = document.id
                            productList.add(it)
                        }
                    }.addOnFailureListener {
                        it.printStackTrace()
                    }.await()
            }
            shoppingList?.let {
                if (it.isEmpty())
                    return Result.Failure("Something went wrong. Try again later")
            }
            result = Result.Success(productList)

            result
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }

    override suspend fun orderProducts(userId: String, products: List<Product>): Result<Boolean> {
        return try {
            val ids = products.map { "${it.id},${it.categoryId}" }

            var result: Result<Boolean>? = null
            firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .document(userId)
                .update(
                    Constants.Collection.Users.FIELD_SHOPPING_CART_LIST,
                    FieldValue.arrayRemove(ids)
                ).addOnSuccessListener {
                    result = Result.Success(true)
                }.addOnFailureListener {
                    result = Result.Failure(
                        it.localizedMessage ?: "Something went wrong. Try again later"
                    )
                }.await()
            result!!
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }
}