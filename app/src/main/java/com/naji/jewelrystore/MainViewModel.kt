package com.naji.jewelrystore

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.jewelrystore.core.domain.repository.LocalUserDataRepository
import com.naji.jewelrystore.core.domain.use_cases.GetLocalUserDataUseCase
import com.naji.jewelrystore.core.domain.use_cases.RemoveLocalUserDataUseCase
import com.naji.jewelrystore.core.presenetation.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getLocalUserDataUseCase: GetLocalUserDataUseCase,
    private val removeLocalUserDataUseCase: RemoveLocalUserDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityState())
    val state = _state.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    init {
        val userId = getLocalUserDataUseCase()
        _state.update {
            it.copy(
                isSignedIn = userId != null
            )
        }
    }

    fun onAction(action: MainActivityAction) {
        when (action) {
            is MainActivityAction.ChangeSelectedIndex -> {
                _state.update {
                    it.copy(
                        index = action.index
                    )
                }
            }

            is MainActivityAction.ChangeNavigationBarVisibility -> {
                _state.update {
                    it.copy(
                        isNavigationBarVisible = action.isVisible
                    )
                }
            }

            MainActivityAction.SignOut -> {
                removeLocalUserDataUseCase()
                viewModelScope.launch {
                    _uiAction.emit(UiAction.NavigateToSignInScreen)
                }
            }

            is MainActivityAction.NavigateToNavigationRoute -> {
                viewModelScope.launch {
                    _uiAction.emit(UiAction.NavigateToNavigationRoute(action.route))
                }
            }
        }
    }

    sealed interface UiAction {
        data object NavigateToSignInScreen : UiAction
        data class NavigateToNavigationRoute(val route: Route) : UiAction
    }
}