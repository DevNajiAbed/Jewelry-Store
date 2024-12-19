package com.naji.jewelrystore.authentication.presentation.sign_in_screen

sealed class SignInScreenAction {
    data class OnUsernameChange(val username: String) : SignInScreenAction()
    data class OnPasswordChange(val password: String) : SignInScreenAction()
    data object PerformSignIn : SignInScreenAction()
}