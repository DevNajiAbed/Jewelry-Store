package com.naji.jewelrystore.jewelry.domain.use_cases

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.repository.JewelryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddProductToShoppingCartUseCase @Inject constructor(
    private val jewelryRepository: JewelryRepository
) {
    operator fun invoke(
        userId: String,
        productId: String,
        categoryId: String
    ): Flow<Result<Boolean>> {
        return flow {
            emit(Result.Loading())
            val result = jewelryRepository.addProductToShoppingCart(userId, productId, categoryId)
            emit(result)
        }
    }
}