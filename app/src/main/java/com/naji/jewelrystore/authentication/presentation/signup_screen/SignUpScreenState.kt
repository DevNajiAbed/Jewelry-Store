package com.naji.jewelrystore.authentication.presentation.signup_screen

data class SignUpScreenState(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)
