package com.naji.jewelrystore.jewelry.presentation.products_screen

import androidx.lifecycle.ViewModel
import com.naji.jewelrystore.jewelry.domain.use_cases.AddProductToShoppingCartUseCase
import com.naji.jewelrystore.jewelry.domain.use_cases.GetAllProductsOfCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val getAllProductsOfCategoryUseCase: GetAllProductsOfCategoryUseCase,
    private val addProductToShoppingCartUseCase: AddProductToShoppingCartUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsScreenState())
    val state = _state.asStateFlow()

    private var categoryId: String? = null

    init {
        getAllProducts()
    }

    fun onAction(action: ProductsScreenAction) {
        when(action) {
            is ProductsScreenAction.AddCategoryId -> {
                categoryId = action.categoryId
                getAllProducts()
            }

            is ProductsScreenAction.AddProductToShoppingCart -> {
                addProductToShoppingCartUseCase(
                    userId = "",
                    productId = action.productId
                )
            }
        }
    }

    private fun getAllProducts() {

    }
}