package com.naji.jewelrystore.jewelry.presentation.home_screen

import com.naji.jewelrystore.jewelry.domain.model.Category

data class HomeScreenState(
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
