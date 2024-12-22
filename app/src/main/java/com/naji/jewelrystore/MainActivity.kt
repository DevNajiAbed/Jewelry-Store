package com.naji.jewelrystore

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.naji.jewelrystore.authentication.presentation.sign_in_screen.SignInScreen
import com.naji.jewelrystore.authentication.presentation.signup_screen.SignUpScreen
import com.naji.jewelrystore.core.data.repository.local.LocalUserDataRepositoryImpl
import com.naji.jewelrystore.core.domain.repository.LocalUserDataRepository
import com.naji.jewelrystore.core.presenetation.navigation.Route
import com.naji.jewelrystore.core.presenetation.ui.theme.JewelryStoreTheme
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary
import com.naji.jewelrystore.jewelry.presentation.home_screen.HomeScreen
import com.naji.jewelrystore.jewelry.presentation.products_screen.ProductsScreen
import com.naji.jewelrystore.shopping_cart.presentation.shopping_cart_screen.ShoppingCartScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = Secondary.copy(0.75f).toArgb(),
                darkScrim = Secondary.copy(0.75f).toArgb()
            )
        )
        setContent {
            JewelryStoreTheme {
                val viewModel: MainViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()
                val uiAction = viewModel.uiAction

                val startDestination = if (state.isSignedIn) {
                    Route.HomeScreen
                } else {
                    Route.SignInScreen
                }

                val navigationItems = getNavigationItems()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Secondary.copy(0.75f)),
                    bottomBar = {
                        if (state.isNavigationBarVisible) {
                            JewelryNavigationBar(
                                navigationItems,
                                state.index,
                                viewModel
                            )
                        }
                    }
                ) { innerPadding ->
                    val navController = rememberNavController()

//                    LaunchedEffect(state.index) {
//                        val route = navigationItems[state.index].route
//                        navController.also {
//                            it.popBackStack()
//                            it.navigate(route)
//                        }
//                    }

                    LaunchedEffect(true) {
                        uiAction.collectLatest { action ->
                            when (action) {
                                MainViewModel.UiAction.NavigateToSignInScreen -> {
                                    navController.also {
                                        it.popBackStack()
                                        it.navigate(Route.SignInScreen)
                                    }
                                }

                                is MainViewModel.UiAction.NavigateToNavigationRoute -> {
                                    navController.navigate(action.route)
                                }
                            }
                        }
                    }

                    NavHost(navController = navController, startDestination = startDestination) {
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
                                    viewModel.onAction(
                                        MainActivityAction.ChangeNavigationBarVisibility(
                                            it
                                        )
                                    )
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
                                    viewModel.onAction(
                                        MainActivityAction.ChangeNavigationBarVisibility(
                                            it
                                        )
                                    )
                                }
                            )
                        }
                        composable<Route.HomeScreen> {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                navigateToProductsScreen = { categoryName, categoryId ->
                                    navController.navigate(
                                        Route.ProductsScreen(
                                            categoryName,
                                            categoryId
                                        )
                                    )
                                },
                                changeNavigationBarVisibility = {
                                    viewModel.onAction(
                                        MainActivityAction.ChangeNavigationBarVisibility(
                                            it
                                        )
                                    )
                                },
                                popBackStack = {
                                    navController.popBackStack()
                                },
                                navigateToSignInScreen = {
                                    navController.navigate(Route.SignInScreen)
                                }
                            )
                        }
                        composable<Route.ProductsScreen> {
                            val route = it.toRoute<Route.ProductsScreen>()
                            ProductsScreen(
                                modifier = Modifier.padding(innerPadding),
                                categoryName = route.categoryName
                            )
                        }
                        composable<Route.ShoppingCartScreen> {
                            ShoppingCartScreen(
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun JewelryNavigationBar(
        navigationItems: List<NavigationItem>,
        selectedIndex: Int,
        viewModel: MainViewModel
    ) {
        NavigationBar(
            containerColor = Secondary.copy(0.75f)
        ) {
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
                        viewModel.also {
                            it.onAction(MainActivityAction.ChangeSelectedIndex(index))
                            it.onAction(MainActivityAction.NavigateToNavigationRoute(item.route))
                        }
                    },
                    alwaysShowLabel = item.alwaysShowLabel,
                    colors = NavigationBarItemColors(
                        selectedIconColor = Color.Black,
                        unselectedIconColor = Color.Black,
                        selectedTextColor = Color.Black,
                        unselectedTextColor = Color.Black,
                        selectedIndicatorColor = Color(0xFFCC6363).copy(0.3f),
                        disabledIconColor = Color.Gray,
                        disabledTextColor = Color.Gray
                    )
                )
            }
        }
    }

    @Composable
    private fun getNavigationItems(): List<NavigationItem> {
        return listOf(
            NavigationItem(
                label = "Home",
                icon = Icons.Rounded.Home,
                alwaysShowLabel = false,
                route = Route.HomeScreen
            ),
            NavigationItem(
                label = "Shopping Cart",
                icon = ImageVector.vectorResource(R.drawable.ic_shopping_cart),
                alwaysShowLabel = false,
                route = Route.ShoppingCartScreen
            )
        )
    }

    private data class NavigationItem(
        val label: String,
        val icon: ImageVector,
        val alwaysShowLabel: Boolean,
        val route: Route
    )
}