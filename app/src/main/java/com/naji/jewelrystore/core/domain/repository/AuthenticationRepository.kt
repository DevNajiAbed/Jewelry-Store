package com.naji.jewelrystore.core.domain.repository

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.User

interface AuthenticationRepository {
    suspend fun signIn(username: String, password: String): Result<String>
    suspend fun signUp(user: User): Result<String>
}