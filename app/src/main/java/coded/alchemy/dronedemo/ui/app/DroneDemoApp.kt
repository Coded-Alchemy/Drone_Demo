package coded.alchemy.dronedemo.ui.app

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.di.appModule
import coded.alchemy.dronedemo.ui.navigation.DroneDemoNavHost
import coded.alchemy.dronedemo.ui.navigation.Screen
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
        modules(appModule)
    }) {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        var visibleAppbar by rememberSaveable { mutableStateOf(false) }

        var visibleNavbar by remember { mutableStateOf(false) }
        var visibleFab by remember { mutableStateOf(false) }

        val snackBarHostState = remember { SnackbarHostState() }
        val snackBarChannel = remember { Channel<String>(Channel.Factory.CONFLATED) }

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
                visibleFab = false
            }
        }


        Scaffold(modifier = modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
            topBar = { DroneDemoAppbar(modifier = modifier, appBarVisible = visibleAppbar) },
            snackbarHost = { DroneDemoSnackbarHost(modifier = modifier, snackbarHostState = snackBarHostState) },
            floatingActionButton = { DroneDemoFab(channel = snackBarChannel, modifier = modifier, isVisible = visibleFab) }
        ) { innerPadding ->
            DroneDemoNavHost(
                modifier = modifier.padding(innerPadding),
                navController = navController,
                snackBarMessageChannel = snackBarChannel
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
    AnimatedVisibility(visible = appBarVisible,
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
fun DroneDemoSnackbarHost(modifier: Modifier = Modifier, snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState) { data ->
        Snackbar(snackbarData = data, /*containerColor = MaterialTheme.colorScheme.primaryContainer*/)
    }
}

@Composable
fun DroneDemoFab(modifier: Modifier = Modifier, channel: Channel<String>, isVisible: Boolean) {
    var clickCount by remember { mutableIntStateOf(0) }

    if (isVisible) {
        ExtendedFloatingActionButton(
            onClick = {
                // offset snackbar data to the business logic
//                channel.trySend(++clickCount)
            },
            content = {
                Text(
                    "Snackbar demo",
//                    modifier = modifier.padding(innerPadding).fillMaxSize().wrapContentSize()
                )
            }
        )
    }
}