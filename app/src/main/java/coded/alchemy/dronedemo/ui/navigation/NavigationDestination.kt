package coded.alchemy.dronedemo.ui.navigation

import androidx.navigation.NavHostController

internal class NavigationDestination(navController: NavHostController) {
    val toControlScreen = {
        navController.navigate(Screen.ControlScreen.route)
    }
}