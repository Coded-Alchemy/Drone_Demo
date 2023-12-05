package coded.alchemy.dronedemo.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.rememberNavController
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.di.appModule
import coded.alchemy.dronedemo.ui.navigation.DroneDemoNavHost
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
        val visibleAppbar by remember { mutableStateOf(false) }

        Surface(
            modifier = modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(modifier = modifier,
                topBar = { DroneDemoAppbar(modifier = modifier, appBarVisible = visibleAppbar) }
            ) { innerPadding ->
                DroneDemoNavHost(
                    navController = navController,
                    modifier = modifier.padding(innerPadding)
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