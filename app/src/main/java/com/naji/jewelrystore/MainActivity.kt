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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.naji.jewelrystore.authentication.presentation.sign_in_screen.SignInScreen
import com.naji.jewelrystore.authentication.presentation.signup_screen.SignUpScreen
import com.naji.jewelrystore.core.presenetation.navigation.Route
import com.naji.jewelrystore.core.presenetation.ui.theme.JewelryStoreTheme
import com.naji.jewelrystore.jewelry.presentation.home_screen.HomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JewelryStoreTheme {
                val viewModel: MainViewModel = viewModel()

                val navigationItems = getNavigationItems()
                val selectedIndex by viewModel.selectedIndex.collectAsState()
                val showNavigationBar by viewModel.showNavigationBar.collectAsState()

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
                                            viewModel.onAction(MainActivityAction.ChangeSelectedIndex(index))
                                        },
                                        alwaysShowLabel = item.alwaysShowLabel
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Route.HomeScreen) {
                        composable<Route.SignInScreen> {
                            SignInScreen(
                                modifier = Modifier.padding(innerPadding),
                                navigateToHomeScreen = {
                                    navController.navigate(Route.HomeScreen)
                                },
                                navigateToSignUpScreen = {
                                    navController.navigate(Route.SignUpScreen)
                                },
                                changeNavigationBarVisibility = {
                                    viewModel.onAction(MainActivityAction.ChangeNavigationBarVisibility(it))
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
                                changeNavigationBarVisibility = {
                                    viewModel.onAction(MainActivityAction.ChangeNavigationBarVisibility(it))
                                }
                            )
                        }
                        composable<Route.HomeScreen> {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                navigateToProductsScreen = { categoryName, categoryId ->

                                }
                            )
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
                label = "Shopping Cart",
                icon = ImageVector.vectorResource(R.drawable.ic_shopping_cart),
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