package com.naji.jewelrystore.shopping_cart.presentation.shopping_cart_screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naji.jewelrystore.core.presenetation.ui.theme.Primary
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary
import com.naji.jewelrystore.jewelry.presentation.products_screen.ProductsScreenAction
import com.naji.jewelrystore.jewelry.presentation.products_screen.components.ProductItem
import com.naji.jewelrystore.shopping_cart.presentation.shopping_cart_screen.components.ShoppingCartItem
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingCartScreenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val uiAction = viewModel.uiAction

    ShoppingCartScreen(
        modifier = modifier,
        state = state,
        uiAction = uiAction,
        onAction = viewModel::onAction
    )
}

@Composable
private fun ShoppingCartScreen(
    modifier: Modifier = Modifier,
    state: ShoppingCartScreenState,
    uiAction: SharedFlow<ShoppingCartScreenViewModel.UiAction>,
    onAction: (ShoppingCartScreenAction) -> Unit
) {
    val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _24ssp = dimensionResource(com.intuit.ssp.R.dimen._24ssp).value.sp
    val _14ssp = dimensionResource(com.intuit.ssp.R.dimen._14ssp).value.sp

    val context = LocalContext.current

    LaunchedEffect(true) {
        uiAction.collectLatest { action ->
            when (action) {
                is ShoppingCartScreenViewModel.UiAction.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }

                is ShoppingCartScreenViewModel.UiAction.RedirectToWhatsApp -> {
                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = Uri.parse(action.url)
                    }
                    try {
                        context.startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        Toast.makeText(context, "WhatsApp not installed", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Secondary.copy(0.75f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Primary
            )
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Secondary.copy(0.75f))
        ) {
            Text(
                text = "Shopping Cart",
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
                    .padding(vertical = _8sdp)
                    .weight(1f)
            ) {
                items(state.products) { product ->
                    ShoppingCartItem(
                        modifier = Modifier
                            .padding(horizontal = _8sdp),
                        product = product,
                        onChecked = { product, checked ->
                            onAction(
                                ShoppingCartScreenAction.OnProductSelectChange(
                                    product,
                                    checked
                                )
                            )
                        }
                    )
                }
            }
            Spacer(Modifier.height(_16sdp))
            Button(
                onClick = {
                    onAction(ShoppingCartScreenAction.OrderViaWhatsApp)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF128c7e)
                ),
                shape = RoundedCornerShape(_8sdp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = _8sdp)
            ) {
                Text(
                    text = "Order via WhatsApp",
                    color = Color.White,
                    modifier = Modifier
                        .padding(vertical = _8sdp),
                    fontSize = _14ssp
                )
            }
        }
    }
}