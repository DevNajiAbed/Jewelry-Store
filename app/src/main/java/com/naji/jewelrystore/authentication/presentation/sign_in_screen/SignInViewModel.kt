package com.naji.jewelrystore.authentication.presentation.sign_in_screen

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.jewelrystore.authentication.domain.use_cases.SignInUseCase
import com.naji.jewelrystore.core.data.Result
import com.naji.jewelrystore.core.domain.use_cases.SaveLocalUserDataUseCase
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
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val saveLocalUserDataUseCase: SaveLocalUserDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignInScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    fun onAction(action: SignInScreenAction) {
        when(action) {
            is SignInScreenAction.OnUsernameChange -> {
                _state.update {
                    it.copy(username = action.username)
                }
            }
            is SignInScreenAction.OnPasswordChange -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }
            SignInScreenAction.PerformSignIn -> {
                performSignIn()
            }
        }
    }

    private fun performSignIn() {
        viewModelScope.launch {
            if(_state.value.username.isBlank()) {
                _uiAction.emit(UiAction.ShowToast("Username cannot be empty"))
                return@launch
            }
            if(_state.value.password.isBlank()) {
                _uiAction.emit(UiAction.ShowToast("Password cannot be empty"))
                return@launch
            }
            signInUseCase(
                _state.value.username,
                _state.value.password
            ).collectLatest {
                when(it) {
                    is Result.Success -> {
                        _state.update { value ->
                            value.copy(
                                isLoading = false,
                                error = ""
                            )
                        }
                        saveLocalUserDataUseCase(it.data!!)
                        _uiAction.emit(UiAction.OnSignInSuccess)
                    }
                    is Result.Failure -> {
                        val msg = it.msg ?: "Something went wrong. Try again later."
                        _state.update { value ->
                            value.copy(
                                isLoading = false,
                                error = msg
                            )
                        }
                        _uiAction.emit(UiAction.ShowToast(msg))
                    }
                    is Result.Loading -> {
                        _state.update { value ->
                            value.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    sealed interface UiAction {
        data class ShowToast(val message: String) : UiAction
        data object OnSignInSuccess : UiAction
    }
}