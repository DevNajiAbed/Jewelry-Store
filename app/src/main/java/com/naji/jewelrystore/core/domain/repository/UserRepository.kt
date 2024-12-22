package com.naji.jewelrystore.core.domain.repository

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.User

interface UserRepository {
    suspend fun getUser(userId: String): Result<User>
}