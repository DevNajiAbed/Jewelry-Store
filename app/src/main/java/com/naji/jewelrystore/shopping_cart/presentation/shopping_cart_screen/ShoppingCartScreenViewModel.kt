package com.naji.jewelrystore.shopping_cart.presentation.shopping_cart_screen

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.model.User
import com.naji.jewelrystore.core.domain.use_cases.GetLocalUserDataUseCase
import com.naji.jewelrystore.core.domain.use_cases.GetRemoteUserUseCase
import com.naji.jewelrystore.shopping_cart.domain.use_cases.GetShoppingCartProductListUseCase
import com.naji.jewelrystore.shopping_cart.domain.use_cases.OrderProductsUseCase
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
class ShoppingCartScreenViewModel @Inject constructor(
    private val orderProductsUseCase: OrderProductsUseCase,
    private val getLocalUserDataUseCase: GetLocalUserDataUseCase,
    private val getShoppingCartProductListUseCase: GetShoppingCartProductListUseCase,
    private val getRemoteUserUseCase: GetRemoteUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(ShoppingCartScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        getShoppingCartProductsList()
    }

    fun onAction(action: ShoppingCartScreenAction) {
        when (action) {
            is ShoppingCartScreenAction.OnProductSelectChange -> {
                _state.update { state ->
                    state.copy(
                        selectedProducts = state.selectedProducts.also {
                            if (action.checked)
                                it.add(action.product)
                            else
                                it.remove(action.product)
                        }
                    )
                }
            }

            ShoppingCartScreenAction.OrderViaWhatsApp -> {
                viewModelScope.launch {
                    if (_state.value.selectedProducts.isEmpty())
                        _uiAction.emit(UiAction.ShowToast("Select products to order"))
                    else {
                        viewModelScope.launch {
                            getRemoteUserUseCase(getLocalUserDataUseCase()!!)
                                .collectLatest { result ->
                                    when (result) {
                                        is Result.Success -> {
                                            Log.i("nji", "here1")
                                            _state.update {
                                                it.copy(
                                                    isLoading = false,
                                                    error = ""
                                                )
                                            }
                                            Log.i("nji", "here2")
                                            orderProductsViaWhatsApp(result.data!!)
                                            Log.i("nji", "here6")
                                        }

                                        is Result.Failure -> {
                                            _state.update {
                                                it.copy(
                                                    isLoading = false,
                                                    error = ""
                                                )
                                            }
                                            _uiAction.emit(
                                                UiAction.ShowToast(
                                                    result.msg
                                                        ?: "Something went wrong. Try again later"
                                                )
                                            )
                                        }

                                        is Result.Loading -> Unit

                                        else -> Unit
                                    }
                                }
                        }
                    }
                }
            }
        }
    }

    private fun orderProductsViaWhatsApp(user: User) {
//        Log.i("nji", "here3")
        viewModelScope.launch {
//            orderProductsUseCase(
//                getLocalUserDataUseCase(),
//                _state.value.selectedProducts
//            ).collectLatest { result ->
//                Log.i("nji", "collect ${result::class.java.name}")
//                when (result) {
//                    is Result.Success -> {
//                        Log.i("nji", "here4")
//                        _state.update {
//                            it.copy(
//                                isLoading = false,
//                                error = ""
//                            )
//                        }
                        val phoneNumber = "+970592265988"
                        val message = prepareWhatsAppOrderMessage(user)
                        val url = "https://wa.me/$phoneNumber?text=${Uri.encode(message)}"
                        _uiAction.emit(UiAction.RedirectToWhatsApp(url))
//                        Log.i("nji", "here5")
//                    }

//                    is Result.Failure -> {
//                        _state.update {
//                            it.copy(
//                                isLoading = false,
//                                error = ""
//                            )
//                        }
//                        _uiAction.emit(
//                            UiAction.ShowToast(
//                                result.msg ?: "Something went wrong. Try again later"
//                            )
//                        )
//                    }

//                    is Result.Loading -> {
//                        _uiAction.emit(UiAction.ShowToast("Processing..."))
//                    }
//                }
//            }
        }
    }

    private fun prepareWhatsAppOrderMessage(user: User): String {
        var message = "Hi, I'm ${user.username}." +
                "\nMy email address is: ${user.email}" +
                "\nI'm ordering the following products:"
        _state.value.selectedProducts.forEach { product ->
            message += "\n- ${product.name} ($${product.price})"
        }
        message += "\n\nI hope to receive them as soon as possible!"

        return message
    }

    private fun getShoppingCartProductsList() {
        viewModelScope.launch {
            getShoppingCartProductListUseCase(
                userId = getLocalUserDataUseCase()!!
            ).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { state ->
                            result.data?.let { data ->
                                state.copy(
                                    products = data,
                                    isLoading = false,
                                    error = ""
                                )
                            } ?: state.copy(
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
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                error = result.msg ?: "Something went wrong. Try again later"
                            )
                        }
                        _uiAction.emit(
                            UiAction.ShowToast(
                                result.msg ?: "Something went wrong. Try again later"
                            )
                        )
                    }

                    is Result.Loading -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    sealed interface UiAction {
        data class ShowToast(val message: String) : UiAction
        data class RedirectToWhatsApp(val url: String) : UiAction
    }
}