package com.naji.jewelrystore.authentication.presentation.signup_screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.naji.jewelrystore.R
import com.naji.jewelrystore.core.presenetation.components.DefaultButton
import com.naji.jewelrystore.core.presenetation.components.DefaultTextField
import com.naji.jewelrystore.core.presenetation.ui.theme.Primary
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary

@Preview
@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Secondary.copy(alpha = 0.5f))
    ) {
        val (
            logo,
            emailTitle,
            emailField,
            usernameTitle,
            usernameField,
            passwordTitle,
            passwordField,
            signUpBtn,
            signInSection
        ) = createRefs()

        val _100sdp = dimensionResource(com.intuit.sdp.R.dimen._100sdp)
        val _80sdp = dimensionResource(com.intuit.sdp.R.dimen._80sdp)
        val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
        val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)
        val _50sdp = dimensionResource(com.intuit.sdp.R.dimen._50sdp)

        val _12ssp = dimensionResource(com.intuit.ssp.R.dimen._12ssp).value.sp

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App's logo",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = _80sdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.size(_100sdp)
        )

        Text(
            text = "Email",
            modifier = Modifier
                .constrainAs(emailTitle) {
                    top.linkTo(logo.bottom, margin = _50sdp)
                    start.linkTo(parent.start, margin = _16sdp)
                },
            fontWeight = FontWeight.Bold,
            fontSize = _12ssp,
            color = Color.Black.copy(0.75f)
        )
        DefaultTextField(
            icon = Icons.Default.Email,
            placeholder = "Email",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .constrainAs(emailField) {
                    top.linkTo(emailTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth()
                .padding(_8sdp)
        )

        Text(
            text = "Your name",
            modifier = Modifier
                .constrainAs(usernameTitle) {
                    top.linkTo(emailField.bottom, margin = _16sdp)
                    start.linkTo(parent.start, margin = _16sdp)
                },
            fontWeight = FontWeight.Bold,
            fontSize = _12ssp,
            color = Color.Black.copy(0.75f)
        )
        DefaultTextField(
            icon = Icons.Default.Person,
            placeholder = "Your name",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .constrainAs(usernameField) {
                    top.linkTo(usernameTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth()
                .padding(_8sdp)
        )

        Text(
            text = "Password",
            modifier = Modifier
                .constrainAs(passwordTitle) {
                    top.linkTo(usernameField.bottom, margin = _16sdp)
                    start.linkTo(parent.start, margin = _16sdp)
                },
            fontWeight = FontWeight.Bold,
            fontSize = _12ssp,
            color = Color.Black.copy(0.75f)
        )
        DefaultTextField(
            icon = ImageVector.vectorResource(R.drawable.ic_key),
            placeholder = "Password",
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions.Default,
            modifier = Modifier
                .constrainAs(passwordField) {
                    top.linkTo(passwordTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }.fillMaxWidth()
                .padding(_8sdp)
        )

        DefaultButton(
            modifier = Modifier
                .constrainAs(signUpBtn) {
                    top.linkTo(passwordField.bottom, margin = _50sdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Sign up",
            onClick = {
                Log.i("nji", "Clicked!")
            }
        )

        Row(
            modifier = Modifier
                .constrainAs(signInSection) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = _16sdp)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Already have an account?",
                color = Color.Black,
                fontSize = _12ssp
            )
            Spacer(modifier = Modifier.width(_8sdp))
            Text(
                text = "Sign in",
                color = Primary,
                fontSize = _12ssp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {

                    }
            )
        }
    }
}