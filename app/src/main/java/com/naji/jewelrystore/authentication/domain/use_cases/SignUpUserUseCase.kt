package com.naji.jewelrystore.authentication.domain.use_cases

import com.naji.jewelrystore.core.domain.model.User
import com.naji.jewelrystore.core.domain.repository.AuthenticationRepository
import com.naji.jewelrystore.core.data.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SignUpUserUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) {
    operator fun invoke(user: User): Flow<Result<String>> = flow {
        emit(Result.Loading())
        val result = authenticationRepository.signUp(user)
        emit(result)
    }
}