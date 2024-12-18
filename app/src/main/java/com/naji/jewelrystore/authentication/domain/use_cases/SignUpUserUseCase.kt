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
    operator fun invoke(user: User): Flow<Result<Boolean>> = flow {
        emit(Result.Loading())
        when(val result = authenticationRepository.signUp(user)) {
            is Result.Success -> emit(result)
            is Result.Failure -> emit(result)
            is Result.Loading -> Unit
        }
    }
}