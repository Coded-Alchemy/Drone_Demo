package coded.alchemy.dronedemo.ui.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.ui.navigation.Route
import org.koin.androidx.compose.koinViewModel

/**
 * ConnectionScreen.kt
 *
 * This [Composable] function declares the [ConnectionScreen].
 * This screen provides the UI to connect to a MavLink System.
 * @author Taji Abdullah
 * */
@Composable
fun ConnectionScreen(navController: NavHostController, viewModel: ConnectionScreenViewModel = koinViewModel()) {
    val screenState by viewModel.initiateDroneConnection().collectAsState(initial = ConnectionState.Default)


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (screenState) {
            is ConnectionState.Default -> ConnectButton(viewModel)
            is ConnectionState.Connecting -> ProgressView()
            is ConnectionState.Connected -> navController.navigate(Route.CONTROL_SCREEN)
            is ConnectionState.Error -> {}
        }

    }
}

/**
 * This [Composable] provides the [ElevatedButton] responsible for connecting to a MavLink System.
 * TODO: enhance with screen state functionality to remedy the double tap needed to navigate away.
 * */
@Composable
fun ConnectButton(viewModel: ConnectionScreenViewModel) {
    ElevatedButton(onClick = {
        viewModel.buttonClicked = true
    }) {
        Text(stringResource(id = R.string.btn_connect))
    }
}

/**
 * [Composable] to provide a [LinearProgressIndicator].
 * */
@Composable
fun ProgressView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(
            modifier = Modifier.width(128.dp),
            color = MaterialTheme.colorScheme.surfaceVariant,
            trackColor = MaterialTheme.colorScheme.secondary
        )
    }
}

