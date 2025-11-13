package app.ma3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.ma3.ui.screens.HomeScreen
import app.ma3.ui.screens.ProfileScreen
import app.ma3.ui.screens.RouteDetailsScreen
import app.ma3.ui.screens.RouteResultsScreen
import app.ma3.ui.screens.SignInScreen
import app.ma3.ui.screens.SignUpScreen
import app.ma3.ui.screens.HelpScreen
import app.ma3.data.repository.RouteData
import app.ma3.data.preferences.TokenManager

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val tokenManager = TokenManager(context)
    val accessToken by tokenManager.accessToken.collectAsState(initial = null)
    val isLoggedIn = !accessToken.isNullOrEmpty()

    val startDestination = if (isLoggedIn) Routes.HOME else Routes.SIGNIN

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToRouteResults = { originLat, originLon, destLat, destLon ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.apply {
                            set("originLat", originLat)
                            set("originLon", originLon)
                            set("destLat", destLat)
                            set("destLon", destLon)
                        }
                    navController.navigate(Routes.ROUTE_RESULTS)
                },
                onNavigateToHelp = { navController.navigate(Routes.HELP) }
            )
        }

        composable(Routes.SIGNIN) {
            SignInScreen(
                onSignInSuccess = { navController.navigate(Routes.HOME) },
                onNavigateToSignUp = { navController.navigate(Routes.SIGN_UP) }
            )
        }

        composable(Routes.SIGN_UP) {
            SignUpScreen(
                onNavigateToSignIn = { navController.navigate(Routes.SIGNIN) },
                onSignUpSuccess = { navController.navigate(Routes.HOME) }
            )
        }

        // Profile Screen
        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigateBack = { navController.navigateUp() },
                onLogout = {
                    navController.navigate(Routes.SIGNIN) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Route Details Screen
        composable(Routes.ROUTE_DETAILS) {
            val selectedRoute = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<RouteData>("selectedRoute")

            RouteDetailsScreen(
                onNavigateBack = { navController.navigateUp() },
                routeData = selectedRoute
            )
        }

        composable(Routes.ROUTE_RESULTS) {
            val originLat = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("originLat")
            val originLon = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("originLon")
            val destLat = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("destLat")
            val destLon = navController.previousBackStackEntry
                ?.savedStateHandle
                ?.get<Double>("destLon")

            RouteResultsScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToRouteDetails = { route ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedRoute", route)
                    navController.navigate(Routes.ROUTE_DETAILS)
                },
                originLat = originLat,
                originLon = originLon,
                destLat = destLat,
                destLon = destLon
            )
        }

        composable(Routes.HELP) {
            HelpScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
