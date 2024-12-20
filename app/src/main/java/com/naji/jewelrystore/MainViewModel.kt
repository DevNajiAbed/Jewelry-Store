package com.naji.jewelrystore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _selectedIndex = MutableStateFlow(0)
    val selectedIndex = _selectedIndex.asStateFlow()

    private val _showNavigationBar = MutableStateFlow(true)
    val showNavigationBar = _showNavigationBar.asStateFlow()

    fun onAction(action: MainActivityAction) {
        when(action) {
            is MainActivityAction.ChangeSelectedIndex -> {
                _selectedIndex.update { action.index }
            }
            is MainActivityAction.ChangeNavigationBarVisibility -> {
                _showNavigationBar.update { action.isVisible }
            }
        }
    }
}