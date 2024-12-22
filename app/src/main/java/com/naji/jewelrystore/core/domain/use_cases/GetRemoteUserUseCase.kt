package com.naji.jewelrystore.core.domain.use_cases

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.User
import com.naji.jewelrystore.core.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRemoteUserUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(userId: String): Flow<Result<User>> {
        return flow {
            emit(Result.Loading())
            val result = userRepository.getUser(userId)
            emit(result)
        }
    }
}