package coded.alchemy.dronedemo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coded.alchemy.dronedemo.ui.connection.ConnectionScreen
import coded.alchemy.dronedemo.ui.control.ControlScreen

/**
 * Navigation.kt
 *
 * This file is concerned with application navigation from screen to screen.
 * @author Taji Abdullah
 * */

/**
 * This [Composable] function provides a [NavHostController], sets the first screen upon
 * application entry, and is responsible for navigation.
 *
 * @param modifier
 * @param navController
 * @param startDestination
 * */
@Composable
fun DroneDemoNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.ConectionScreen.route
) {
    val navigate = remember(navController) { NavigationDestination(navController) }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = startDestination) {
            ConnectionScreen(navController)
        }
        composable(route = Screen.ControlScreen.route) {
            ControlScreen()
        }
    }
}