package com.naji.jewelrystore.core.presenetation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naji.jewelrystore.R

@Composable
fun DefaultButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    cornerSize: Dp = dimensionResource(com.intuit.sdp.R.dimen._12sdp)
) {
    val _20sdp = dimensionResource(com.intuit.sdp.R.dimen._20sdp)
    val _5sdp = dimensionResource(com.intuit.sdp.R.dimen._5sdp)

    val _12ssp = dimensionResource(com.intuit.ssp.R.dimen._12ssp).value.sp

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(cornerSize)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(
                    start = _20sdp, end = _20sdp,
                    top = _5sdp, bottom = _5sdp
                ),
            color = Color.Black,
            fontSize = _12ssp,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        )
    }
}

@Preview
@Composable
private fun PreviewDefaultButton() {
    DefaultButton(
        onClick = {},
        text = "Login"
    )
}