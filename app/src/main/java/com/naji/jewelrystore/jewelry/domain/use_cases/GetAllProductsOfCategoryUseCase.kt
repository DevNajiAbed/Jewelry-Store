package com.naji.jewelrystore.jewelry.domain.use_cases

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.Product
import com.naji.jewelrystore.core.domain.repository.JewelryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllProductsOfCategoryUseCase @Inject constructor(
    private val repository: JewelryRepository
) {
    operator fun invoke(categoryId: String): Flow<Result<List<Product>>> {
        return flow {
            emit(Result.Loading())
            val products = repository.getAllProductsOfCategory(categoryId)
            emit(products)
        }
    }
}