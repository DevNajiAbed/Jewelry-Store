package com.naji.jewelrystore.jewelry.presentation.products_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.naji.jewelrystore.core.presenetation.components.DefaultButton
import com.naji.jewelrystore.jewelry.presentation.products_screen.components.ProductItem

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel = hiltViewModel(),
    categoryName: String,
    categoryId: String
) {
    // Send the categoryId to the viewModel
    viewModel.onAction(ProductsScreenAction.AddCategoryId(categoryId))

    val state by viewModel.state.collectAsState()

    ProductsScreen(
        modifier = modifier,
        categoryName = categoryName,
        categoryId = categoryId,
        state = state,
        onAction = viewModel::onAction
    )
}

@Preview(showSystemUi = true)
@Composable
private fun ProductsScreen(
    modifier: Modifier = Modifier.statusBarsPadding(),
    categoryName: String = "Ring",
    categoryId: String = "",
    state: ProductsScreenState = ProductsScreenState(),
    onAction: (ProductsScreenAction) -> Unit = {}
) {
    val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _24ssp = dimensionResource(com.intuit.ssp.R.dimen._24ssp).value.sp

    Column(
        modifier = modifier
            .padding(_16sdp)
    ) {
        Text(
            text = categoryName,
            fontSize = _24ssp,
            color = Color(0xFFCC6363),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(_16sdp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(_8sdp)
        ) {
            items(state.products) { product ->
                ProductItem(
                    product = product,
                    addToShoppingCart = {
                        onAction(ProductsScreenAction.AddProductToShoppingCart(it))
                    }
                )
            }
        }
    }
}