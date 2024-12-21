package com.naji.jewelrystore.jewelry.presentation.products_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naji.jewelrystore.R
import com.naji.jewelrystore.jewelry.domain.model.Product

@Composable
fun ProductItem(
    modifier: Modifier = Modifier,
    product: Product,
    addToShoppingCart: (productId: String) -> Unit
) {
    val _80sdp = dimensionResource(com.intuit.sdp.R.dimen._80sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _16ssp = dimensionResource(com.intuit.ssp.R.dimen._16ssp).value.sp

    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(_80sdp)
            .clip(RoundedCornerShape(_8sdp))
            .background(Color.White),
        horizontalArrangement = Arrangement.spacedBy(_8sdp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier
                .width(_80sdp)
                .fillMaxHeight(),
            model = product.imageUrl,
            contentDescription = product.name,
            contentScale = ContentScale.Crop
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
        IconButton(onClick = { addToShoppingCart(product.id!!) }) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_shopping_cart),
                contentDescription = "Add to cart"
            )
        }
    }
}

@Preview
@Composable
private fun PreviewProductItem() {
    ProductItem(
        product = Product(name = "Ring", imageUrl = "", price = 343f),
        addToShoppingCart = {}
    )
}