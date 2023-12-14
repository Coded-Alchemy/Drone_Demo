package coded.alchemy.dronedemo.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.integerResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.ui.connection.ConnectionScreen
import coded.alchemy.dronedemo.ui.control.ControlScreen
import kotlinx.coroutines.channels.Channel

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
    startDestination: String = Screen.ConnectionScreen.route,
    snackBarMessageChannel: Channel<String>,
    setFabOnClick: (() -> Unit) -> Unit
) {
    val navigate = remember(navController) { NavigationDestination(navController) }
    val tweenTime = integerResource(id = R.integer.tween_time)

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(
            route = startDestination,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(tweenTime)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(tweenTime)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(tweenTime)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(tweenTime)
                )
            }) {
            ConnectionScreen(navController = navController, snackBarMessageChannel = snackBarMessageChannel)
        }
        composable(
            route = Screen.ControlScreen.route,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(tweenTime)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(tweenTime)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(tweenTime)
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(tweenTime)
                )
            }) {
            ControlScreen(modifier = modifier, snackBarMessageChannel = snackBarMessageChannel, setFabOnClick = setFabOnClick)
        }
    }
}