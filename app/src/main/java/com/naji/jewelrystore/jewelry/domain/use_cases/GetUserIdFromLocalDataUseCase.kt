package com.naji.jewelrystore.jewelry.domain.use_cases

import com.naji.jewelrystore.core.domain.repository.LocalUserDataRepository
import javax.inject.Inject

class GetUserIdFromLocalDataUseCase @Inject constructor(
    private val localDataRepository: LocalUserDataRepository
) {
    operator fun invoke(): String? {
        return localDataRepository.getUserId()
    }
}