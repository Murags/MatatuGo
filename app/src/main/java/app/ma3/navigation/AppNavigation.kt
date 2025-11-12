package app.ma3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.ma3.ui.screens.*

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    // --- NEW PARAMETER: Callback to signal MainActivity that the splash is visually complete ---
    onSplashFinished: () -> Unit = {}
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH, // Start with Splash Screen
        modifier = modifier
    ) {
        // Splash Screen
        composable(Routes.SPLASH) {
            SplashScreen(
                onTimeout = {
                    // 1. IMPORTANT: Signal MainActivity that the splash screen is done
                    // This keeps the main content view attached until navigation completes.
                    onSplashFinished()

                    // 2. Navigate to Sign-In after splash delay
                    navController.navigate(Routes.SIGN_IN) {
                        popUpTo(Routes.SPLASH) { inclusive = true } // Prevent back to splash
                    }
                }
            )
        }

        // Sign-In Screen
        composable(Routes.SIGN_IN) {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate(Routes.SIGN_UP) },
                onSignInSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGN_IN) { inclusive = true } // clear auth screens
                    }
                }
            )
        }

        // Sign-Up Screen
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onNavigateToSignIn = { navController.navigate(Routes.SIGN_IN) },
                onSignUpSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGN_UP) { inclusive = true }
                    }
                }
            )
        }

        // Home Screen
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToRouteDetails = { navController.navigate(Routes.ROUTE_DETAILS) }
            )
        }

        // Profile Screen
        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        // Route Details Screen
        composable(Routes.ROUTE_DETAILS) {
            RouteDetailsScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
