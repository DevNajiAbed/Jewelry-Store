package com.naji.jewelrystore.shopping_cart.domain.use_cases

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.Product
import com.naji.jewelrystore.core.domain.repository.JewelryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetShoppingCartProductListUseCase @Inject constructor(
    private val jewelryRepository: JewelryRepository
) {
    operator fun invoke(userId: String): Flow<Result<List<Product>>> {
        return flow {
            emit(Result.Loading())
            val result = jewelryRepository.getShoppingCartProductList(userId)
            emit(result)
        }
    }
}