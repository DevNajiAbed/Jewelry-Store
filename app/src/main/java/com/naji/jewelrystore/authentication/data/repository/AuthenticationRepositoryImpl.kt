package com.naji.jewelrystore.authentication.data.repository

import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.naji.jewelrystore.core.domain.repository.AuthenticationRepository
import com.naji.jewelrystore.core.domain.model.User
import com.naji.jewelrystore.core.data.Constants
import com.naji.jewelrystore.core.data.Result
import kotlinx.coroutines.tasks.await

class AuthenticationRepositoryImpl : AuthenticationRepository {
    private val firestore = Firebase.firestore

    override suspend fun signIn(username: String, password: String): Result<Boolean> {
        /*return suspendCancellableCoroutine { continuation ->
            firestore.collection(Constants.COLLECTION_USERS)
                .where(
                    Filter.and(
                        Filter.equalTo(Constants.FIELD_USERNAME, username),
                        Filter.equalTo(Constants.FIELD_PASSWORD, password)
                    )
                ).get()
                .addOnSuccessListener {
                    if (it.size() > 0)
                        continuation.resume(Result.Success(true))
                    else
                        continuation.resume(Result.Failure("User not found"))
                }.addOnFailureListener {
                    continuation.resume(
                        Result.Failure(
                            it.localizedMessage ?: "Something went wrong. Try again later"
                        )
                    )
                }
        }*/
        return try {
            val querySnapshot = firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .where(
                    Filter.and(
                        Filter.equalTo(Constants.Collection.Users.FIELD_USERNAME, username),
                        Filter.equalTo(Constants.Collection.Users.FIELD_PASSWORD, password)
                    )
                ).get()
                .await()

            if(querySnapshot.size() == 0)
                return Result.Failure("Error in username or password")

            Result.Success(true)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }

    override suspend fun signUp(user: User): Result<Boolean> {
        return try {
            val querySnapshot = firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .whereEqualTo(Constants.Collection.Users.FIELD_EMAIL, user.email)
                .get()
                .await()

            if (querySnapshot.size() > 0) {
                return Result.Failure("This email already exists")
            }

            firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .add(user)
                .await()
            Result.Success(true)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }
}