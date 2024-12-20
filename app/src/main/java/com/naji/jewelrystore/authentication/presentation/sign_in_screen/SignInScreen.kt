package com.naji.jewelrystore.authentication.presentation.sign_in_screen

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.naji.jewelrystore.R
import com.naji.jewelrystore.core.presenetation.components.DefaultButton
import com.naji.jewelrystore.core.presenetation.components.DefaultTextField
import com.naji.jewelrystore.core.presenetation.ui.theme.Primary
import com.naji.jewelrystore.core.presenetation.ui.theme.Secondary
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.material3.CircularProgressIndicator

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel(),
    navigateToHomeScreen: () -> Unit = {},
    navigateToSignUpScreen: () -> Unit = {},
    changeNavigationBarVisibility: (Boolean) -> Unit
) {
    // Hide the NavigationBar
    changeNavigationBarVisibility(false)

    val state = viewModel.state.collectAsState()
    val uiAction = viewModel.uiAction

    SignInScreen(
        modifier = modifier,
        state = state,
        uiAction = uiAction,
        onAction = viewModel::onAction,
        navigateToHomeScreen = navigateToHomeScreen,
        navigateToSignUpScreen = navigateToSignUpScreen,
        onNavigationBarVisibilityChange = changeNavigationBarVisibility
    )
}

@Composable
private fun SignInScreen(
    modifier: Modifier,
    state: State<SignInScreenState>,
    uiAction: SharedFlow<SignInViewModel.UiAction>,
    onAction: (SignInScreenAction) -> Unit,
    navigateToHomeScreen: () -> Unit,
    navigateToSignUpScreen: () -> Unit,
    onNavigationBarVisibilityChange: (Boolean) -> Unit
) {
    val context = LocalContext.current

    LaunchedEffect(true) {
        uiAction.collectLatest{ action ->
            when(action) {
                is SignInViewModel.UiAction.ShowToast -> {
                    Toast.makeText(context, action.message, Toast.LENGTH_SHORT).show()
                }
                SignInViewModel.UiAction.OnSignInSuccess -> {
                    // Show the NavigationBar when we close SignUpScreen
                    onNavigationBarVisibilityChange(true)
                    navigateToHomeScreen()
                }
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(Secondary.copy(alpha = 0.5f))
    ) {
        val (
            logo,
            usernameTitle,
            usernameField,
            passwordTitle,
            passwordField,
            signInBtn,
            signUpSection,
            loadingIndicator
        ) = createRefs()

        val _100sdp = dimensionResource(com.intuit.sdp.R.dimen._100sdp)
        val _80sdp = dimensionResource(com.intuit.sdp.R.dimen._80sdp)
        val _16sdp = dimensionResource(com.intuit.sdp.R.dimen._16sdp)
        val _8sdp = dimensionResource(com.intuit.sdp.R.dimen._8sdp)
        val _50sdp = dimensionResource(com.intuit.sdp.R.dimen._50sdp)

        val _12ssp = dimensionResource(com.intuit.ssp.R.dimen._12ssp).value.sp

        val usernameFocusRequester = FocusRequester()
        val passwordFocusRequester = FocusRequester()
        val keyboardController = LocalSoftwareKeyboardController.current

        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "App's logo",
            modifier = Modifier
                .constrainAs(logo) {
                    top.linkTo(parent.top, margin = _80sdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .size(_100sdp)
        )

        Text(
            text = "Username",
            modifier = Modifier
                .constrainAs(usernameTitle) {
                    top.linkTo(logo.bottom, margin = _50sdp)
                    start.linkTo(parent.start, margin = _16sdp)
                },
            fontWeight = FontWeight.Bold,
            fontSize = _12ssp,
            color = Color.Black.copy(0.75f)
        )
        DefaultTextField(
            icon = Icons.Default.Person,
            placeholder = "Username",
            value = state.value.username,
            onValueChange = {
                onAction(SignInScreenAction.OnUsernameChange(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    passwordFocusRequester.requestFocus()
                }
            ),
            modifier = Modifier
                .constrainAs(usernameField) {
                    top.linkTo(usernameTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(_8sdp)
                .focusRequester(usernameFocusRequester)
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
            value = state.value.password,
            onValueChange = {
                onAction(SignInScreenAction.OnPasswordChange(it))
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Go
            ),
            keyboardActions = KeyboardActions(
                onGo = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .constrainAs(passwordField) {
                    top.linkTo(passwordTitle.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(_8sdp)
                .focusRequester(passwordFocusRequester)
        )

        if(state.value.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .constrainAs(loadingIndicator) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(passwordField.bottom)
                        bottom.linkTo(signInBtn.top)
                    },
                color = Primary
            )
        }

        DefaultButton(
            modifier = Modifier
                .constrainAs(signInBtn) {
                    top.linkTo(passwordField.bottom, margin = _50sdp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = "Sign in",
            onClick = {
                if(!state.value.isLoading)
                    onAction(SignInScreenAction.PerformSignIn)
            }
        )

        Row(
            modifier = Modifier
                .constrainAs(signUpSection) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, margin = _16sdp)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Don't have an account?",
                color = Color.Black,
                fontSize = _12ssp
            )
            Spacer(modifier = Modifier.width(_8sdp))
            Text(
                text = "Sign up",
                color = Primary,
                fontSize = _12ssp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .clickable {
                        // Show the NavigationBar when we close SignUpScreen
                        onNavigationBarVisibilityChange(true)
                        navigateToSignUpScreen()
                    }
            )
        }
    }
}