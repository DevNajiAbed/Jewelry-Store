package com.naji.jewelrystore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naji.jewelrystore.core.presenetation.ui.theme.JewelryStoreTheme
import com.naji.jewelrystore.jewelry.presentation.home_screen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint
import com.naji.jewelrystore.core.presenetation.navigation.Route
import com.naji.jewelrystore.authentication.presentation.sign_in_screen.SignInScreen
import com.naji.jewelrystore.authentication.presentation.signup_screen.SignUpScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JewelryStoreTheme {
                val navigationItems = getNavigationItems()
                var selectedIndex by remember { mutableIntStateOf(0) }
                var showNavigationBar by remember { mutableStateOf(true) }

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            if(showNavigationBar) {
                                navigationItems.forEachIndexed { index, item ->
                                    NavigationBarItem(
                                        selected = selectedIndex == index,
                                        label = {
                                            Text(text = item.label)
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = item.icon,
                                                contentDescription = item.label
                                            )
                                        },
                                        onClick = {
                                            selectedIndex = index
                                        },
                                        alwaysShowLabel = item.alwaysShowLabel
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Route.SignInScreen) {
                        composable<Route.SignInScreen> {
                            SignInScreen(
                                modifier = Modifier.padding(innerPadding),
                                navigateToHomeScreen = {
                                    navController.navigate(Route.HomeScreen)
                                },
                                navigateToSignUpScreen = {
                                    navController.navigate(Route.SignUpScreen)
                                },
                                onNavigationBarVisibilityChange = {
                                    showNavigationBar = it
                                }
                            )
                        }
                        composable<Route.SignUpScreen> {
                            SignUpScreen(
                                modifier = Modifier.padding(innerPadding),
                                navigateToHomeScreen = {
                                    navController.navigate(Route.HomeScreen)
                                },
                                navigateToSignInScreen = {
                                    navController.navigateUp()
                                },
                                onNavigationBarVisibilityChange = {
                                    showNavigationBar = it
                                }
                            )
                            composable<Route.HomeScreen> {
                                HomeScreen(
                                    modifier = Modifier.padding(innerPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun getNavigationItems(): List<NavigationItem> {
        return listOf(
            NavigationItem(
                label = "Home",
                icon = Icons.Rounded.Home,
                alwaysShowLabel = false
            ),
            NavigationItem(
                label = "",
                icon = Icons.Rounded.Home,
                alwaysShowLabel = false
            ),
            NavigationItem(
                label = "Home",
                icon = Icons.Rounded.Home,
                alwaysShowLabel = false
            )
        )
    }

    data class NavigationItem(
        val label: String,
        val icon: ImageVector,
        val alwaysShowLabel: Boolean
    )
}