package com.naji.jewelrystore.jewelry.presentation.products_screen

import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.naji.jewelrystore.core.presenetation.components.DefaultButton
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary
import com.naji.jewelrystore.jewelry.presentation.products_screen.components.ProductItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductsScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductsScreenViewModel = hiltViewModel(),
    categoryName: String
) {

    val state by viewModel.state.collectAsState()
    val uiAction = viewModel.uiAction

    ProductsScreen(
        modifier = modifier,
        categoryName = categoryName,
        state = state,
        uiAction = uiAction,
        onAction = viewModel::onAction
    )
}

//@Preview(showSystemUi = true)
@Composable
private fun ProductsScreen(
    modifier: Modifier = Modifier.statusBarsPadding(),
    categoryName: String = "Ring",
    state: ProductsScreenState = ProductsScreenState(),
    uiAction: SharedFlow<ProductsScreenViewModel.UiAction>,
    onAction: (ProductsScreenAction) -> Unit = {}
) {
    val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _24ssp = dimensionResource(com.intuit.ssp.R.dimen._24ssp).value.sp

    val context = LocalContext.current

    LaunchedEffect(true) {
        uiAction.collectLatest { action ->
            when (action) {
                is ProductsScreenViewModel.UiAction.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    if(state.isLoading) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Secondary.copy(0.75f)),
//            contentAlignment = Alignment.Center
//        ) {
//            CircularProgressIndicator()
//        }
//    } else {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Secondary.copy(0.75f))
    ) {
        Text(
            text = categoryName,
            fontSize = _24ssp,
            color = Color(0xFFCC6363),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(_16sdp)
        )
        Spacer(Modifier.height(_16sdp))
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(_8sdp),
            modifier = Modifier
                .padding(horizontal = _8sdp)
        ) {
            items(state.products) { product ->
                ProductItem(
                    product = product,
                    addToShoppingCart = { productId, categoryId ->
                        onAction(ProductsScreenAction.AddProductToShoppingCart(productId, categoryId))
                    }
                )
            }
        }
    }
//    }
}