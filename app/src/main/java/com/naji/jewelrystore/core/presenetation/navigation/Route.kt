package com.naji.jewelrystore.core.presenetation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object SignInScreen : Route

    @Serializable
    data object SignUpScreen : Route

    @Serializable
    data object HomeScreen : Route
}