package com.naji.jewelrystore.jewelry.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.jewelry.domain.use_cases.GetAllCategoriesUseCase
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
class HomeScreenViewModel @Inject constructor(
    private val getAllCategoriesUseCase: GetAllCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        viewModelScope.launch {
            getALlCategories()
        }
    }

    fun onAction(action: HomeScreenAction) {
        when (action) {
            is HomeScreenAction.OnCategorySelected -> {
                viewModelScope.launch {
                    _uiAction.emit(
                        UiAction.NavigateToCategoryProducts(
                            categoryName = action.category.name,
                            categoryId = action.category.id!!
                        )
                    )
                }
            }
        }
    }

    private suspend fun getALlCategories() {
        getAllCategoriesUseCase().collectLatest { result ->
            when (result) {
                is Result.Success -> {
                    _state.update { state ->
                        result.data?.let {
                            state.copy(
                                categories = result.data,
                                isLoading = false,
                                error = ""
                            )
                        } ?: state.copy(
                            isLoading = false,
                            error = "Something went wrong. Try again later"
                        )
                    }
                    _uiAction.emit(UiAction.ShowToast("Something went wrong. Try again later"))
                }

                is Result.Failure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = result.msg ?: "Something went wrong. Try again later"
                        )
                    }
                    _uiAction.emit(UiAction.ShowToast("Something went wrong. Try again later"))
                }

                is Result.Loading -> {
                    _state.update {
                        it.copy(isLoading = true)
                    }
                }
            }
        }
    }

    sealed interface UiAction {
        data class NavigateToCategoryProducts(val categoryName: String, val categoryId: String) :
            UiAction

        data class ShowToast(val msg: String) : UiAction
    }
}
