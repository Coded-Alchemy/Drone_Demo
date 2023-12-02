package coded.alchemy.dronedemo.ui.navigation

sealed class Screen(val route: String) {
    object ConectionScreen : Screen(route = Route.CONNECTION_SCREEN)
    object ControlScreen : Screen(route = Route.CONTROL_SCREEN)
}
