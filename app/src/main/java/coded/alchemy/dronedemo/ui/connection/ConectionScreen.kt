package coded.alchemy.dronedemo.ui.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val isNetworkConnected by viewModel.isNetworkConnected.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isNetworkConnected) {
            ConnectButton(navController, viewModel)
        } else {
            disconnectedNetworkMessage()
        }
    }
}

/**
 * This [Composable] provides the [ElevatedButton] responsible for connecting to a MavLink System.
 * TODO: enhance with screen state functionality to remedy the double tap needed to navigate away.
 * */
@Composable
fun ConnectButton(navController: NavHostController, viewModel: ConnectionScreenViewModel) {
    ElevatedButton(onClick = {
        viewModel.connect()
        when (viewModel.isDroneConnected.value) {
            true -> navController.navigate(Route.CONTROL_SCREEN)
            else -> {}
        }
    }) {
        Text(stringResource(id = R.string.btn_connect))
    }
}

/**
 * [Composable] to provide a a message when wifi is needed.
 * */
@Composable
fun disconnectedNetworkMessage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Wifi is needed to connect to drone.",
            fontSize = 25.sp,
            fontWeight = FontWeight.W700,
            modifier = Modifier.padding(10.dp)
        )
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

