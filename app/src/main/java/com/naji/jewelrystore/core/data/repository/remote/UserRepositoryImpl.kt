package com.naji.jewelrystore.core.data.repository.remote

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.naji.jewelrystore.core.data.Constants
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.User
import com.naji.jewelrystore.core.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await

class UserRepositoryImpl : UserRepository {
    private val firestore = Firebase.firestore

    override suspend fun getUser(userId: String): Result<User> {
        return try {
            var error = ""
            val documentSnapshot = firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .document(userId)
                .get()
                .addOnFailureListener {
                    it.printStackTrace()
                    error = it.localizedMessage ?: "Something went wrong. Try again later"
                }.await()

            if(error.isNotBlank())
                return Result.Failure(error)

            val user = documentSnapshot.toObject<User>()?.also {
                it.id = documentSnapshot.id
            } ?: return Result.Failure("Something went wrong. Try again later")

            return Result.Success(user)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }
}