package com.naji.jewelrystore.core.domain.use_cases

import com.naji.jewelrystore.core.domain.repository.LocalUserDataRepository
import javax.inject.Inject

class SaveLocalUserDataUseCase @Inject constructor(
    private val localUserDataRepository: LocalUserDataRepository
) {
    operator fun invoke(userId: String) {
        localUserDataRepository.saveUserId(userId)
    }
}