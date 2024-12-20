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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary
import com.naji.jewelrystore.jewelry.presentation.home_screen.components.CategoryItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateToProductsScreen: (categoryName: String, categoryId: String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val uiAction = viewModel.uiAction

    HomeScreen(
        modifier = modifier,
        state = state,
        uiAction = uiAction,
        onAction = viewModel::onAction,
        navigateToProductsScreen = navigateToProductsScreen
    )
}

@Composable
private fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    uiAction: SharedFlow<HomeScreenViewModel.UiAction>,
    onAction: (HomeScreenAction) -> Unit,
    navigateToProductsScreen: (categoryName: String, categoryId: String) -> Unit
) {
    val _32sdp = dimensionResource(com.intuit.sdp.R.dimen._32sdp)
    val _24sdp = dimensionResource(com.intuit.sdp.R.dimen._24sdp)
    val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _32ssp = dimensionResource(com.intuit.ssp.R.dimen._32ssp).value.sp
    val _12ssp = dimensionResource(com.intuit.ssp.R.dimen._12ssp).value.sp

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
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Secondary.copy(alpha = 0.75f))
    ) {
        val (
            notificationIcon,
            topSection,
            categoryList
        ) = createRefs()

        IconButton(
            onClick = {},
            modifier = Modifier
                .constrainAs(notificationIcon) {
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

        Row(
            modifier = Modifier
                .constrainAs(topSection) {
                    top.linkTo(notificationIcon.bottom, margin = _8sdp)
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
            DefaultButton(
                onClick = {},
                text = "All Products",
                fontColor = Color.White,
                backgroundColor = Color(0xFFCC6363)
            )
        }


        LazyColumn(
            modifier = Modifier
                .constrainAs(categoryList) {
                    top.linkTo(topSection.bottom, margin = _32sdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            verticalArrangement = Arrangement.spacedBy(_16sdp)
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