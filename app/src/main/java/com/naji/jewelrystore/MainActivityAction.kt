package com.naji.jewelrystore

import com.naji.jewelrystore.core.presenetation.navigation.Route

sealed interface MainActivityAction {
    data class ChangeSelectedIndex(val index: Int) : MainActivityAction
    data class ChangeNavigationBarVisibility(val isVisible: Boolean) : MainActivityAction
    data object SignOut : MainActivityAction
    data class NavigateToNavigationRoute(val route: Route) : MainActivityAction
}