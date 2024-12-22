package com.naji.jewelrystore.shopping_cart.presentation.shopping_cart_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naji.jewelrystore.core.domain.model.Product

@Composable
fun ShoppingCartItem(
    modifier: Modifier = Modifier,
    product: Product,
    onChecked: (product: Product, checked: Boolean) -> Unit
) {
    val _80sdp = dimensionResource(com.intuit.sdp.R.dimen._80sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _16ssp = dimensionResource(com.intuit.ssp.R.dimen._16ssp).value.sp

    var checked by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(_80sdp)
            .clip(RoundedCornerShape(_8sdp))
            .background(Color.White)
            .clickable {
                checked = !checked
                onChecked(product, checked)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(_8sdp)
    ) {
        AsyncImage(
            model = product.imageUrl,
            contentDescription = product.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(_80sdp)
                .fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = product.name,
                fontSize = _16ssp,
                color = Color(0xFFCC6363),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(_8sdp))
            Text(
                text = "$${product.price}",
                color = Color.Black
            )
        }
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onChecked(product, it)
            },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFCC6363),
                uncheckedColor = Color(0xFFCC6363)
            )
        )
    }
}

@Preview
@Composable
private fun PreviewShoppingCartItem() {
    ShoppingCartItem(
        product = Product(name = "Product", imageUrl = "", price = 234.53f, categoryId = ""),
        onChecked = { _, _ -> }
    )
}