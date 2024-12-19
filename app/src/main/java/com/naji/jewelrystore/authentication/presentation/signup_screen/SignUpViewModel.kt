package com.naji.jewelrystore.authentication.presentation.signup_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.naji.jewelrystore.authentication.domain.use_cases.SignInUseCase
import com.naji.jewelrystore.core.data.Result
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
class SignUpViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpScreenState())
    val state = _state.asStateFlow()

    private val _uiAction = MutableSharedFlow<UiAction>()
    val uiAction = _uiAction.asSharedFlow()

    fun onAction(action: SignUpScreenAction) {
        when(action) {
            is SignUpScreenAction.OnEmailChange -> {
                _state.update {
                    it.copy(email = action.email)
                }
            }
            is SignUpScreenAction.OnUsernameChange -> {
                _state.update {
                    it.copy(username = action.username)
                }
            }
            is SignUpScreenAction.OnPasswordChange -> {
                _state.update {
                    it.copy(password = action.password)
                }
            }
            SignUpScreenAction.PerformSignUp -> {
                viewModelScope.launch {
                    signInUseCase(
                        _state.value.email,
                        _state.value.password
                    ).collectLatest {
                        when(it) {
                            is Result.Success -> {
                                _state.update { value ->
                                    value.copy(isLoading = false)
                                }
                                _uiAction.emit(UiAction.OnSignUpSuccess)
                            }
                            is Result.Failure -> {
                                val msg = it.msg ?: "Something went wrong. Try again later."
                                _state.update { value ->
                                    value.copy(
                                        isLoading = false,
                                        error = msg
                                    )
                                }
                                _uiAction.emit(UiAction.OnSignUpFailure(msg))
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
        }
    }

    sealed class UiAction {
        data object OnSignUpSuccess : UiAction()
        data class OnSignUpFailure(val message: String) : UiAction()
    }
}