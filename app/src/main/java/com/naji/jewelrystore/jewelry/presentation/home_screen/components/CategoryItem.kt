package com.naji.jewelrystore.jewelry.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.naji.jewelrystore.core.presenetation.ui.theme.Primary
import com.naji.jewelrystore.jewelry.domain.model.Category

@Composable
fun CategoryItem(
    modifier: Modifier = Modifier,
    category: Category,
    onItemClick: (Category) -> Unit
) {
    val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)

    val _32ssp = dimensionResource(com.intuit.ssp.R.dimen._32ssp).value.sp

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(_16sdp))
            .background(Color.White)
            .clickable { onItemClick(category) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = category.imgUrl,
            contentDescription = category.name,
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(_8sdp))
        Text(
            text = category.name,
            color = Primary,
            fontSize = _32ssp
        )
    }
}

@Preview
@Composable
private fun PreviewCategoryItem() {
    CategoryItem(
        modifier = Modifier,
        category = Category(name = "Ring", imgUrl = ""),
        onItemClick = {}
    )
}