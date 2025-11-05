package app.ma3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.ma3.ui.screens.HomeScreen
import app.ma3.ui.screens.ProfileScreen
import app.ma3.ui.screens.RouteDetailsScreen
import app.ma3.ui.screens.SignInScreen
import app.ma3.ui.screens.SignUpScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SIGN_IN, // Start at Sign-In for now
        modifier = modifier
    ) {
        // Sign In Screen
        composable(Routes.SIGN_IN) {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate(Routes.SIGN_UP) },
                onSignInSuccess = { navController.navigate(Routes.HOME) }
            )
        }

        // Sign Up Screen
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onNavigateToSignIn = { navController.navigate(Routes.SIGN_IN) },
                onSignUpSuccess = { navController.navigate(Routes.HOME) }
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

