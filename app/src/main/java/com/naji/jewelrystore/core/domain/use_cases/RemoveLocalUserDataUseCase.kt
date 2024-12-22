package com.naji.jewelrystore.core.domain.use_cases

import com.naji.jewelrystore.core.domain.repository.LocalUserDataRepository
import javax.inject.Inject

class RemoveLocalUserDataUseCase @Inject constructor(
    private val localUserDataRepository: LocalUserDataRepository
) {
    operator fun invoke() {
        localUserDataRepository.removeUserId()
    }
}