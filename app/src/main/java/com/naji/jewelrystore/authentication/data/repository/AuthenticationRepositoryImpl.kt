package com.naji.jewelrystore.authentication.data.repository

import androidx.compose.ui.util.trace
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
        var result: Result<Boolean>? = null
        firestore.collection(Constants.COLLECTION_USERS)
            .where(
                Filter.and(
                    Filter.equalTo(Constants.FIELD_USERNAME, username),
                    Filter.equalTo(Constants.FIELD_USERNAME, password)
                )
            ).get()
            .addOnSuccessListener {
                if (it.size() > 0)
                    result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Failure(
                    it.localizedMessage ?: "Something went wrong. Try again later."
                )
            }.await()
        return result!!
    }

    override suspend fun signUp(user: User): Result<Boolean> {
        var isAlreadyExisted = false
        var error = ""
        firestore.collection(Constants.COLLECTION_USERS)
            .whereEqualTo(Constants.FIELD_EMAIL, user.email)
            .get()
            .addOnSuccessListener {
                if(it.size() > 0)
                    isAlreadyExisted = true
            }.addOnFailureListener {
                error = it.localizedMessage ?: "Something went wrong. Try again later."
            }.await()

        if(error.isNotBlank())
            return Result.Failure(error)
        if(isAlreadyExisted)
            return Result.Failure("This email is already existed.")

        var result: Result<Boolean>? = null
        firestore.collection(Constants.COLLECTION_USERS)
            .add(user)
            .addOnSuccessListener {
                result = Result.Success(true)
            }.addOnFailureListener {
                result = Result.Failure(it.localizedMessage ?: "Something went wrong. Try again later.")
            }.await()

        return result!!
    }
}