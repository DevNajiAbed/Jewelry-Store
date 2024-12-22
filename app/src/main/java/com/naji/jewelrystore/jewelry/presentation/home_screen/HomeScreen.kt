package com.naji.jewelrystore.jewelry.presentation.home_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.naji.jewelrystore.core.presenetation.components.DefaultButton
import com.naji.jewelrystore.core.presenetation.ui.theme.Primary
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary
import com.naji.jewelrystore.jewelry.presentation.home_screen.components.CategoryItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToProductsScreen: (categoryName: String, categoryId: String) -> Unit,
    changeNavigationBarVisibility: (Boolean) -> Unit,
    popBackStack: () -> Unit,
    navigateToSignInScreen: () -> Unit
) {
    changeNavigationBarVisibility(true)

    val state by viewModel.state.collectAsState()
    val uiAction = viewModel.uiAction

    HomeScreen(
        modifier = modifier,
        state = state,
        uiAction = uiAction,
        onAction = viewModel::onAction,
        navigateToProductsScreen = navigateToProductsScreen,
        popBackStack = popBackStack,
        navigateToSignInScreen = navigateToSignInScreen
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    uiAction: SharedFlow<HomeScreenViewModel.UiAction>,
    onAction: (HomeScreenAction) -> Unit,
    navigateToProductsScreen: (categoryName: String, categoryId: String) -> Unit,
    popBackStack: () -> Unit,
    navigateToSignInScreen: () -> Unit
) {
    val _32sdp = dimensionResource(com.intuit.sdp.R.dimen._32sdp)
    val _24sdp = dimensionResource(com.intuit.sdp.R.dimen._24sdp)
    val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _32ssp = dimensionResource(com.intuit.ssp.R.dimen._32ssp).value.sp
    val _12ssp = dimensionResource(com.intuit.ssp.R.dimen._12ssp).value.sp
    val _10ssp = dimensionResource(com.intuit.ssp.R.dimen._10ssp).value.sp

    val context = LocalContext.current

    LaunchedEffect(true) {
        uiAction.collectLatest { action ->
            when (action) {
                is HomeScreenViewModel.UiAction.NavigateToCategoryProducts -> {
                    navigateToProductsScreen(action.categoryName, action.categoryId)
                }

                is HomeScreenViewModel.UiAction.ShowToast -> {
                    Toast.makeText(context, action.msg, Toast.LENGTH_SHORT).show()
                }

                HomeScreenViewModel.UiAction.NavigateToSignInScreen -> {
                    popBackStack()
                    navigateToSignInScreen()
                }
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Secondary.copy(alpha = 0.75f))
    ) {
        val (
            loadingIndicator,
            signOutBtn,
            notificationBtn,
            topSection,
            categoryList
        ) = createRefs()

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(loadingIndicator) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                color = Primary
            )
        } else {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .constrainAs(notificationBtn) {
                        end.linkTo(parent.end, margin = _16sdp)
                        top.linkTo(parent.top, margin = _16sdp)
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .size(_24sdp),
                    imageVector = Icons.Rounded.Notifications,
                    contentDescription = "Notifications",
                    tint = Color(0xFFCC6363)
                )
            }

            IconButton(
                onClick = {
                    onAction(HomeScreenAction.SignOut)
                },
                modifier = Modifier
                    .constrainAs(signOutBtn) {
                        start.linkTo(parent.start, margin = _16sdp)
                        top.linkTo(parent.top, margin = _16sdp)
                    }
            ) {
                Icon(
                    modifier = Modifier
                        .size(_24sdp),
                    imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                    contentDescription = "Notifications",
                    tint = Color(0xFFCC6363)
                )
            }

            Row(
                modifier = Modifier
                    .constrainAs(topSection) {
                        top.linkTo(notificationBtn.bottom, margin = _8sdp)
                        start.linkTo(parent.start, margin = _16sdp)
                        end.linkTo(parent.end, margin = _16sdp)
                        width = Dimension.fillToConstraints
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Explore",
                        fontSize = _32ssp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Best categories for you",
                        fontSize = _12ssp
                    )
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFCC6363)
                    ),
                    shape = RoundedCornerShape(_8sdp)
                ) {
                    Text(
                        text = "All Products",
                        color = Color.White,
                        modifier = Modifier
                            .padding(horizontal = _8sdp),
                        fontSize = _10ssp
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(vertical = _8sdp)
                    .constrainAs(categoryList) {
                        top.linkTo(topSection.bottom, margin = _8sdp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.spacedBy(_8sdp)
            ) {
                items(state.categories) { category ->
                    CategoryItem(
                        category = category,
                        modifier = Modifier
                            .padding(horizontal = _16sdp),
                        onItemClick = {
                            onAction(HomeScreenAction.OnCategorySelected(it))
                        }
                    )
                }
            }
        }
    }
}