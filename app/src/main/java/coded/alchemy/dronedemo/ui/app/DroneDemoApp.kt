package coded.alchemy.dronedemo.ui.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.data.di.dataModule
import coded.alchemy.dronedemo.di.appModule
import coded.alchemy.dronedemo.domain.di.domainModule
import coded.alchemy.dronedemo.ui.navigation.DroneDemoNavHost
import coded.alchemy.dronedemo.ui.navigation.Screen
import coded.alchemy.dronedemo.ui.navigation.navbarAccessibleScreens
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.compose.KoinApplication

/**
 * DroneDemoApp.kt
 *
 * This file provides the [Composable] UI application entry point.
 * Koin Dependency injection is instantiated here.
 * @author Taji Abdullah
 * */
@Composable
fun DroneDemoApp(modifier: Modifier = Modifier) {
    KoinApplication(application = {
        modules(appModule, dataModule, domainModule)
    }) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        var visibleAppbar by rememberSaveable { mutableStateOf(false) }
        var visibleNavbar by rememberSaveable { mutableStateOf(false) }
        var visibleFab by rememberSaveable { mutableStateOf(false) }

        val snackBarHostState = remember { SnackbarHostState() }
        val snackBarChannel = remember { Channel<String>(Channel.Factory.CONFLATED) }

        val (onFabClick, setOnFabClick) = remember { mutableStateOf<(() -> Unit)?>(null) }


        // This is for passing messages to the snackBar
        LaunchedEffect(snackBarChannel) {
            snackBarChannel.receiveAsFlow().collect { message ->
                val result = snackBarHostState.showSnackbar(
                    message = message,
                    actionLabel = "Action"
                )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        /* action has been performed */
                    }

                    SnackbarResult.Dismissed -> {
                        /* dismissed, no action needed */
                    }
                }
            }
        }

        // Control visibility for FAB and AppBars
        when (navBackStackEntry?.destination?.route) {
            Screen.ControlScreen.route -> {
                visibleAppbar = true
                visibleNavbar = true
                visibleFab = true
            }

            Screen.MissionScreen.route -> {
                visibleAppbar = true
                visibleNavbar = true
                visibleFab = true
            }

            Screen.LogScreen.route -> {
                visibleAppbar = true
                visibleNavbar = true
                visibleFab = false
            }
        }


        Scaffold(
            modifier = modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { DroneDemoAppbar(modifier = modifier, appBarVisible = visibleAppbar) },
            bottomBar = {
                navBackStackEntry?.let { backStackEntry ->
                    DroneDemoBottomNavigation(
                        navController = navController,
                        backStackEntry = backStackEntry,
                        isVisible = visibleNavbar
                    )
                }
            },
            snackbarHost = {
                DroneDemoSnackbarHost(
                    modifier = modifier,
                    snackbarHostState = snackBarHostState
                )
            },
            floatingActionButton = {
                navBackStackEntry?.let { backStack ->
                    DroneDemoFab(
                        channel = snackBarChannel,
                        modifier = modifier,
                        isVisible = visibleFab,
                        backStackEntry = backStack,
                        onFabClick = onFabClick
                    )
                }
            },
        ) { innerPadding ->
            DroneDemoNavHost(
                modifier = modifier.padding(innerPadding),
                navController = navController,
                snackBarMessageChannel = snackBarChannel,
                setFabOnClick = setOnFabClick
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DroneDemoAppbar(
    modifier: Modifier = Modifier,
    appBarVisible: Boolean
) {
    AnimatedVisibility(
        visible = appBarVisible,
        enter = slideInVertically(initialOffsetY = { -it }),
        exit = slideOutVertically(targetOffsetY = { -it })
    ) {
        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
            title = {
                Text(
                    stringResource(id = R.string.app_name),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        )
    }
}

@Composable
fun DroneDemoBottomNavigation(
    navController: NavHostController,
    backStackEntry: NavBackStackEntry,
    isVisible: Boolean
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { +it }),
        exit = slideOutVertically(targetOffsetY = { +it })
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            val currentRoute = backStackEntry.destination.route

            navbarAccessibleScreens().forEach { screen ->
                NavigationBarItem(
                    selected = currentRoute == screen.route,
                    label = {
                        Text(
                            text = stringResource(id = screen.caption),
                            fontWeight = FontWeight.SemiBold
                        )
                    },
                    onClick = {
                        navController.navigate(screen.route)
                    },
                    icon = { /*TODO*/ }
                )
            }
        }
    }
}

@Composable
fun DroneDemoSnackbarHost(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        Snackbar(snackbarData = data)
    }
}

@Composable
fun DroneDemoFab(
    modifier: Modifier = Modifier,
    channel: Channel<String>,
    isVisible: Boolean,
    backStackEntry: NavBackStackEntry,
    onFabClick: (() -> Unit)?
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(initialOffsetX = { -it }),
        exit = slideOutVertically(targetOffsetY = { +it })
    ) {
        ExtendedFloatingActionButton(
            onClick = {
                onFabClick?.invoke()
            },
            content = {
                when (backStackEntry?.destination?.route) {
                    Screen.ControlScreen.route -> Text(stringResource(id = R.string.btn_voice_command))
                    Screen.MissionScreen.route -> Text(stringResource(id = R.string.btn_start_mission))
                }
            }
        )
    }
}