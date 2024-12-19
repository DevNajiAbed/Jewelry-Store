package com.naji.jewelrystore.jewelry.presentation.home_screen

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.naji.jewelrystore.R
import com.naji.jewelrystore.core.presenetation.components.DefaultButton
import com.naji.jewelrystore.core.presenetation.ui.theme.Primary
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary
import com.naji.jewelrystore.core.presenetation.ui.theme.Tertiary
import com.naji.jewelrystore.jewelry.domain.model.Category

@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val _100sdp = dimensionResource(com.intuit.sdp.R.dimen._100sdp)
    val _32sdp = dimensionResource(com.intuit.sdp.R.dimen._32sdp)
    val _24sdp = dimensionResource(com.intuit.sdp.R.dimen._24sdp)
    val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
    val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)
    val _5sdp = dimensionResource(com.intuit.sdp.R.dimen._5sdp)

    val _32ssp = dimensionResource(com.intuit.ssp.R.dimen._32ssp).value.sp
    val _16ssp = dimensionResource(com.intuit.ssp.R.dimen._16ssp).value.sp
    val _12ssp = dimensionResource(com.intuit.ssp.R.dimen._12ssp).value.sp
    val _8ssp = dimensionResource(com.intuit.ssp.R.dimen._8ssp).value.sp

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
            onClick = {

            },
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

        val categories = listOf(
            Category(
                title = "Bracelet",
                subtitle1 = "Glitter And Gold",
                subtitle2 = "Glitter And Gold",
                imgResId = R.drawable.bracelet
            ),
            Category(
                title = "Earring",
                subtitle1 = "Sparkling Avenues",
                subtitle2 = "Sparkling Avenues",
                imgResId = R.drawable.earring
            ),
            Category(
                title = "Ring",
                subtitle1 = "Amazing Designs",
                subtitle2 = "Amazing Designs",
                imgResId = R.drawable.ring
            ),
            Category(
                title = "Pendant",
                subtitle1 = "Eternal Diamonds",
                subtitle2 = "Eternal Diamonds",
                imgResId = R.drawable.pendant
            )
        )
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
            items(categories) { category ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = _16sdp)
                        .clip(RoundedCornerShape(_16sdp))
                        .background(Color.White)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = _16sdp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = category.title,
                                fontSize = _16ssp,
                                color = Color(0xFFCC6363)
                            )
                            Spacer(Modifier.height(_8sdp))
                            Text(
                                text = category.subtitle1,
                                fontSize = _12ssp,
                                color = Color.Black
                            )
                            Spacer(Modifier.height(_5sdp))
                            Text(
                                text = category.subtitle2,
                                fontSize = _8ssp,
                                color = Color.Gray
                            )
                        }
                        Image(
                            painter = painterResource(category.imgResId),
                            contentDescription = category.title,
                            modifier = Modifier
                                .size(_100sdp)
                                .aspectRatio(1f)
                                .clip(CircleShape)
                        )
                    }
                }
            }
        }
    }
}