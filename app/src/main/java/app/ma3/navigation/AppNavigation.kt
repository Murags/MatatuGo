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

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                onNavigateToProfile = { navController.navigate(Routes.PROFILE) },
                onNavigateToRouteDetails = { navController.navigate(Routes.ROUTE_DETAILS) },
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
                onNavigateBack = { navController.navigateUp() }
            )
        }
    }
}
