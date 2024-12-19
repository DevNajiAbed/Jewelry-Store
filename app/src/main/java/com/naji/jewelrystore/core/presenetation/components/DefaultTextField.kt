package com.naji.jewelrystore.core.presenetation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naji.jewelrystore.R
import com.naji.jewelrystore.core.presenetation.ui.theme.Gray

@Composable
fun DefaultTextField(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    contentDescription: String? = null,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    cornerSize: Dp = dimensionResource(com.intuit.sdp.R.dimen._12sdp)
) {
    val _12ssp = dimensionResource(com.intuit.ssp.R.dimen._12ssp).value.sp

    TextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier,
        shape = RoundedCornerShape(cornerSize),
        textStyle = TextStyle(
            fontSize = _12ssp,
            color = Color.Black,
            fontFamily = FontFamily(Font(R.font.poppins_regular))
        ),
        placeholder = {
            Text(
                text = placeholder,
                fontSize = _12ssp,
                color = Gray,
                fontFamily = FontFamily(Font(R.font.poppins_regular))
            )
        },
        leadingIcon = {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = Gray
            )
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewDefaultTextField() {
    DefaultTextField(
        icon = Icons.Default.Email,
        placeholder = "Email",
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Go
        ),
        keyboardActions = KeyboardActions.Default,
        onValueChange = {},
        value = ""
    )
}