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

import app.ma3.ui.screens.*

import app.ma3.data.repository.RouteData
import app.ma3.data.preferences.TokenManager

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    onSplashFinished: () -> Unit = {}
) {
    val context = LocalContext.current
    val tokenManager = TokenManager(context)

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH,
        modifier = modifier
    ) {

        composable(Routes.SPLASH) {
            val accessToken by tokenManager.accessToken.collectAsState(initial = null)

            SplashScreen(
                onTimeout = { isLoggedIn ->
                    onSplashFinished()

                    val destination = if (isLoggedIn) Routes.HOME else Routes.SIGN_IN
                    navController.navigate(destination) {
                        popUpTo(Routes.SPLASH) { inclusive = true }
                    }
                },
                tokenManager = tokenManager
            )
        }

        // ---------------------- AUTH SCREENS ---------------------- //
        composable(Routes.SIGN_IN) {
            SignInScreen(
                onNavigateToSignUp = { navController.navigate(Routes.SIGN_UP) },
                onSignInSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.SIGN_IN) { inclusive = true }
                    }
                }
            )
        }

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

        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToRouteResults = { originLat, originLon, destLat, destLon ->
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
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

        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigateBack = { navController.navigateUp() },
                onLogout = {
                    navController.navigate(Routes.SIGN_IN) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable(Routes.HELP) {
            HelpScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(Routes.ROUTE_RESULTS) {
            val entry = navController.previousBackStackEntry?.savedStateHandle

            val originLat = entry?.get<Double>("originLat")
            val originLon = entry?.get<Double>("originLon")
            val destLat = entry?.get<Double>("destLat")
            val destLon = entry?.get<Double>("destLon")

            RouteResultsScreen(
                originLat = originLat,
                originLon = originLon,
                destLat = destLat,
                destLon = destLon,
                onNavigateBack = { navController.navigateUp() },
                onNavigateToRouteDetails = { route ->
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("selectedRoute", route)

                    navController.navigate(Routes.ROUTE_DETAILS)
                }
            )
        }

        composable(Routes.ROUTE_DETAILS) {
            val selectedRoute =
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.get<RouteData>("selectedRoute")

            RouteDetailsScreen(
                routeData = selectedRoute,
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}

