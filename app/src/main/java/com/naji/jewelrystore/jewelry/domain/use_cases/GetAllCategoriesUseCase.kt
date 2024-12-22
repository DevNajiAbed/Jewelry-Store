package com.naji.jewelrystore.jewelry.domain.use_cases

import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.jewelry.domain.model.Category
import com.naji.jewelrystore.core.domain.repository.JewelryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: JewelryRepository
) {
    operator fun invoke(): Flow<Result<List<Category>>> {
        return flow {
            emit(Result.Loading())
            val categories = repository.getAllCategories()
            emit(categories)
        }
    }
}