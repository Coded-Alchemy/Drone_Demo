package coded.alchemy.dronedemo.ui.navigation

import androidx.annotation.StringRes
import coded.alchemy.dronedemo.R

sealed class Screen(val route: String, @StringRes val caption: Int) {
    data object ConnectionScreen : Screen(route = Route.CONNECTION_SCREEN, caption = 0)
    data object ControlScreen : Screen(route = Route.CONTROL_SCREEN, R.string.screen_control)
    data object MissionScreen : Screen(route = Route.MISSION_SCREEN, R.string.screen_mission)
    data object LogScreen : Screen(route = Route.LOG_SCREEN, R.string.screen_logs)
}

/**
 * List of screens that populate the bottomNav bar.
 * */
fun navbarAccessibleScreens() = listOf(
    Screen.ControlScreen,
    Screen.MissionScreen,
    Screen.LogScreen
)
