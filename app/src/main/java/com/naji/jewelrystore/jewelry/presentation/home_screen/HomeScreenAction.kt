package com.naji.jewelrystore.jewelry.presentation.home_screen

import com.naji.jewelrystore.jewelry.domain.model.Category

sealed interface HomeScreenAction {
    data class OnCategorySelected(val category: Category) : HomeScreenAction
    data object SignOut : HomeScreenAction
}