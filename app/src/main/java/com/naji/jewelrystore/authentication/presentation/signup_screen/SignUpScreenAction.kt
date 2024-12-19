package com.naji.jewelrystore.authentication.presentation.signup_screen

sealed class SignUpScreenAction {
    data class OnEmailChange(val email: String) : SignUpScreenAction()
    data class OnUsernameChange(val username: String) : SignUpScreenAction()
    data class OnPasswordChange(val password: String) : SignUpScreenAction()
    data object PerformSignUp : SignUpScreenAction()
}