package com.naji.jewelrystore.authentication.data.repository

import android.util.Log
import androidx.compose.ui.util.trace
import com.google.firebase.Firebase
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.firestore
import com.naji.jewelrystore.core.domain.repository.AuthenticationRepository
import com.naji.jewelrystore.core.domain.model.User
import com.naji.jewelrystore.core.data.Constants
import com.naji.jewelrystore.core.data.Result
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume

class AuthenticationRepositoryImpl : AuthenticationRepository {
    private val firestore = Firebase.firestore

    override suspend fun signIn(username: String, password: String): Result<Boolean> {
        return suspendCancellableCoroutine { continuation ->
            firestore.collection(Constants.COLLECTION_USERS)
                .where(
                    Filter.and(
                        Filter.equalTo(Constants.FIELD_USERNAME, username),
                        Filter.equalTo(Constants.FIELD_USERNAME, password)
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
        }
    }

    override suspend fun signUp(user: User): Result<Boolean> {
//        return suspendCancellableCoroutine { continuation ->
//            var isAlreadyExisted = false
//            var error = ""
//            firestore.collection(Constants.COLLECTION_USERS)
//                .whereEqualTo(Constants.FIELD_EMAIL, user.email)
//                .get()
//                .addOnSuccessListener {
//                    if (it.size() > 0)
//                        isAlreadyExisted = true
//                }.addOnFailureListener {
//                    error = it.localizedMessage ?: "Something went wrong. Try again later"
//                }
//
//            if (error.isNotBlank())
//                continuation.resume(Result.Failure(error))
//            if (isAlreadyExisted)
//                continuation.resume(Result.Failure("This email is already existed"))
//
//            firestore.collection(Constants.COLLECTION_USERS)
//                .add(user)
//                .addOnSuccessListener {
//                    continuation.resume(Result.Success(true))
//                }.addOnFailureListener {
//                    continuation.resume(
//                        Result.Failure(it.localizedMessage ?: "Something went wrong. Try again later")
//                    )
//                }
//        }
        Log.i("nji", "here0")
        return try {
            Log.i("nji", "here1")
            // Step 1: Check if the user already exists
            val querySnapshot = firestore.collection(Constants.COLLECTION_USERS)
                .whereEqualTo(Constants.FIELD_EMAIL, user.email)
                .get()
                .await() // Use await to suspend until the query completes
            Log.i("nji", "here2")

            if (querySnapshot.size() > 0) {
                return Result.Failure("This email already exists")
            }
            Log.i("nji", "here3")

            // Step 2: Add the new user
            firestore.collection(Constants.COLLECTION_USERS)
                .add(user)
                .await() // Use await to suspend until the user is added
            Log.i("nji", "here4")
            Result.Success(true)
        } catch (e: Exception) {
            Log.i("nji", "here5")
            Result.Failure(e.localizedMessage ?: "Something went wrong. Try again later")
        }
    }
}