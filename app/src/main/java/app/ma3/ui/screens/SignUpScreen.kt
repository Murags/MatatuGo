package app.ma3.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import app.ma3.ui.theme.MatatuOrange
import app.ma3.ui.theme.MatatuYellow
import app.ma3.ui.viewmodel.AuthViewModel
import app.ma3.ui.viewmodel.AuthViewModelFactory
import app.ma3.ui.viewmodel.SignupState

/**
 * MatatuGo Sign-Up Screen
 *
 * Displays:
 * - Gradient background
 * - App title and tagline
 * - Name, Email, and Password input fields
 * - Create Account button
 * - Inline "Already have an account? Sign In" link
 */
@Composable
fun SignUpScreen(
    onNavigateToSignIn: () -> Unit = {},
    onSignUpSuccess: () -> Unit = {}
) {
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory(context)
    )
    val signupState by viewModel.signupState.collectAsState()

    LaunchedEffect(signupState) {
        if (signupState is SignupState.Success) {
            onSignUpSuccess()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MatatuYellow.copy(alpha = 0.2f),
                        MatatuOrange.copy(alpha = 0.1f)
                    )
                )
            )
            .padding(horizontal = 24.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Title
            Text(
                text = "Join MatatuGo",
                style = MaterialTheme.typography.headlineLarge,
                color = MatatuOrange,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            // Tagline
            Text(
                text = "Sign up to explore Kenya’s matatu routes.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B6B6B),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Name Field
            var fullName by remember { mutableStateOf("") }
            var passwordVisible by remember { mutableStateOf(false) }

            TextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("Full Name", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Filled.Person, contentDescription = "Name icon", tint = Color.Gray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MatatuOrange
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            // Email Field
            var email by remember { mutableStateOf("") }

            TextField(
                value = email,
                onValueChange = { email = it },
                placeholder = { Text("Email Address", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Filled.Email, contentDescription = "Email icon", tint = Color.Gray)
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MatatuOrange
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )

            // Password Field
            var password by remember { mutableStateOf("") }

            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Password", color = Color.Gray, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(Icons.Filled.Lock, contentDescription = "Password icon", tint = Color.Gray)
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Gray
                        )
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, RoundedCornerShape(12.dp)),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MatatuOrange
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            if (signupState is SignupState.Error) {
                Text(
                    text = (signupState as SignupState.Error).message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Create Account Button
            Button(
                onClick = {
                    viewModel.signup(
                        name = fullName.trim(),
                        email = email.trim(),
                        password = password
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MatatuOrange),
                enabled = signupState !is SignupState.Loading
            ) {
                if (signupState is SignupState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.White
                    )
                } else {
                    Text(
                        text = "Create Account",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Inline "Already have an account? Sign In"
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color(0xFF6B6B6B),
                    fontSize = 14.sp
                )
                TextButton(onClick = { onNavigateToSignIn() }) {
                    Text(
                        text = "Sign In",
                        color = MatatuOrange,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

/**
 * Preview function for Android Studio.
 * Displays the Sign-Up screen without launching the app.
 */
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen()
}
