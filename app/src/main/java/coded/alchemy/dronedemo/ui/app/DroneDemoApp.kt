package coded.alchemy.dronedemo.ui.app

import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.rememberNavController
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.di.appModule
import coded.alchemy.dronedemo.ui.navigation.DroneDemoNavHost
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
        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            val visibleAppbar by remember { mutableStateOf(false) }

            val snackbarHostState = remember { SnackbarHostState() }

            val channel = remember { Channel<Int>(Channel.Factory.CONFLATED) }
            LaunchedEffect(channel) {
                channel.receiveAsFlow().collect { index ->
                    val result = snackbarHostState.showSnackbar(
                        message = "Snackbar # $index",
                        actionLabel = "Action on $index"
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

            Scaffold(modifier = modifier.fillMaxSize(),
                topBar = { DroneDemoAppbar(modifier = modifier, appBarVisible = visibleAppbar) },
                snackbarHost = { DroneDemoSnackbarHost(modifier = modifier, snackbarHostState = snackbarHostState) },
                floatingActionButton = { DroneDemoFab(channel = channel, modifier = modifier) }
            ) { innerPadding ->
                DroneDemoNavHost(
                    modifier = modifier.padding(innerPadding),
                    navController = navController
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DroneDemoAppbar(
    modifier: Modifier = Modifier,
    appBarVisible: Boolean
) {
    if (appBarVisible) {
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
    SnackbarHost(hostState = snackbarHostState) {
//                        val backgroundColor = snackbarDelegate.snackbarBackgroundColor
        Snackbar(snackbarData = it, containerColor = MaterialTheme.colorScheme.primaryContainer)
    }
}

@Composable
fun DroneDemoFab(modifier: Modifier = Modifier, channel: Channel<Int>) {
    var clickCount by remember { mutableIntStateOf(0) }

    Row {
        ExtendedFloatingActionButton(
            onClick = {
                // offset snackbar data to the business logic
                channel.trySend(++clickCount)
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