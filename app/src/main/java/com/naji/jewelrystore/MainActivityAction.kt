package com.naji.jewelrystore

sealed interface MainActivityAction {
    data class ChangeSelectedIndex(val index: Int) : MainActivityAction
    data class ChangeNavigationBarVisibility(val isVisible: Boolean) : MainActivityAction
}