package com.naji.jewelrystore.shopping_cart.domain.use_cases

import android.util.Log
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.Product
import com.naji.jewelrystore.core.domain.repository.JewelryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderProductsUseCase @Inject constructor(
    private val jewelryRepository: JewelryRepository
) {
    operator fun invoke(
        userId: String,
        products: List<Product>
    ): Flow<Result<Boolean>> {
        Log.i("nji", "use case 1")
        return flow {
            Log.i("nji", "use case 2")
            emit(Result.Loading())
            Log.i("nji", "use case 3")
            val result = jewelryRepository.orderProducts(userId, products)
            Log.i("nji", "use case 4")
            emit(result)
            Log.i("nji", "use case 5")
        }
    }
}