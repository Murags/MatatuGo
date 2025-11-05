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
import app.ma3.ui.screens.RouteResultsScreen
import app.ma3.ui.screens.HelpScreen
import app.ma3.signin.SignInScreen

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.SIGNIN,
        modifier = modifier
    ) {

        // This is retired should be used when you have new screen and dont want to go through whole flow
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToRouteResults = { navController.navigate(Routes.ROUTE_RESULTS) },
                onNavigateToHelp = { navController.navigate(Routes.HELP) }
            )
        }

        composable(Routes.SIGNIN) {
            SignInScreen(
                onNavigateToHomeScreen = { navController.navigate(Routes.HOME) },
                onNavigateToRouteResults = { navController.navigate(Routes.ROUTE_RESULTS) }
            )
        }

        composable(Routes.PROFILE) {
            ProfileScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(Routes.ROUTE_DETAILS) {
            RouteDetailsScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }

        composable(Routes.ROUTE_RESULTS) {
            RouteResultsScreen(
                onNavigateBack = { navController.navigateUp() },
                onNavigateToRouteDetails = { navController.navigate(Routes.ROUTE_DETAILS) }
            )
        }

        composable(Routes.HELP) {
            HelpScreen(
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}