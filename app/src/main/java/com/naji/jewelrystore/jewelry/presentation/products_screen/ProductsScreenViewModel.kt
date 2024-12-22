package com.naji.jewelrystore.jewelry.presentation.products_screen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.jewelry.domain.use_cases.AddProductToShoppingCartUseCase
import com.naji.jewelrystore.jewelry.domain.use_cases.GetAllProductsOfCategoryUseCase
import com.naji.jewelrystore.jewelry.domain.use_cases.GetUserIdFromLocalDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsScreenViewModel @Inject constructor(
    private val getAllProductsOfCategoryUseCase: GetAllProductsOfCategoryUseCase,
    private val addProductToShoppingCartUseCase: AddProductToShoppingCartUseCase,
    private val getUserIdFromLocalDataUseCase: GetUserIdFromLocalDataUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = MutableStateFlow(ProductsScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        savedStateHandle.get<String>("categoryId")?.let { categoryId ->
            getAllProducts(categoryId)
        } ?: also {
            viewModelScope.launch {
                _uiAction.emit(UiAction.ShowToast("Something went wrong. Try again later"))
            }
        }
    }

    fun onAction(action: ProductsScreenAction) {
        when (action) {
            is ProductsScreenAction.AddProductToShoppingCart -> {
                addProductToShoppingCart(action)
            }
        }
    }

    private fun addProductToShoppingCart(action: ProductsScreenAction.AddProductToShoppingCart) {
        viewModelScope.launch {
            addProductToShoppingCartUseCase(
                userId = getUserIdFromLocalDataUseCase()!!,
                productId = action.productId,
                categoryId = action.categoryId
            ).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _uiAction.emit(UiAction.ShowToast("Go check your cart!"))
                    }

                    is Result.Failure -> {
                        _uiAction.emit(
                            UiAction.ShowToast(
                                result.msg ?: "Something went wrong. Try again later"
                            )
                        )
                    }

                    is Result.Loading -> Unit
                }
            }
        }
    }

    private fun getAllProducts(categoryId: String) {
        viewModelScope.launch {
            getAllProductsOfCategoryUseCase(categoryId)
                .collectLatest { result ->
                    when (result) {
                        is Result.Success -> {
                            _state.update {
                                result.data?.let { data ->
                                    it.copy(
                                        products = data,
                                        isLoading = false,
                                        error = ""
                                    )
                                } ?: it.copy(
                                    isLoading = false,
                                    error = result.msg ?: "Something went wrong. Try again later"
                                ).also {
                                    _uiAction.emit(
                                        UiAction.ShowToast(
                                            result.msg ?: "Something went wrong. Try again later"
                                        )
                                    )
                                }
                            }
                        }

                        is Result.Failure -> {
                            result.msg?.let { msg ->
                                _state.update {
                                    it.copy(
                                        isLoading = false,
                                        error = msg
                                    )
                                }
                                _uiAction.emit(UiAction.ShowToast(msg))
                            }
                        }

                        is Result.Loading -> {
                            _state.update {
                                it.copy(
                                    isLoading = true
                                )
                            }
                        }
                    }
                }
        }
    }

    sealed interface UiAction {
        data class ShowToast(val message: String) : UiAction
    }
}