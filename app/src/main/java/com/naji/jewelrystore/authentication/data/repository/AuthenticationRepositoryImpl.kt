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

    override suspend fun signIn(username: String, password: String): Result<String> {
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

            val userId = querySnapshot.documents[0].id

            Result.Success(userId)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }

    override suspend fun signUp(user: User): Result<String> {
        return try {
            val querySnapshot = firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .whereEqualTo(Constants.Collection.Users.FIELD_EMAIL, user.email)
                .get()
                .await()

            if (querySnapshot.size() > 0) {
                return Result.Failure("This email already exists")
            }

            val documentRef = firestore.collection(Constants.Collection.Users.COLLECTION_NAME)
                .add(user)
                .await()

            val userId = documentRef.id

            Result.Success(userId)
        } catch (e: Exception) {
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }
}