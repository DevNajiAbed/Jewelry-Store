package com.naji.jewelrystore.authentication.presentation.sign_in_screen

data class SignInScreenState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
