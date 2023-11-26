package coded.alchemy.dronedemo.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.util.addMph
import coded.alchemy.dronedemo.util.addPercentSign
import coded.alchemy.dronedemo.util.calculateMphFromVelocity
import coded.alchemy.dronedemo.util.formatTenthsAndHundredths
import coded.alchemy.dronedemo.util.formatToTenths
import org.koin.androidx.compose.koinViewModel


@Composable
fun ControlScreen(viewModel: ControlScreenViewModel = koinViewModel()) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TelemetryPanel(viewModel)
        FlightButtons(viewModel)
    }
}

@Composable
fun TelemetryPanel(viewModel: ControlScreenViewModel) {
    val relativeAltitudeFloatState by viewModel.relativeAltitudeFloat.collectAsState()
    val satelliteCountState by viewModel.satelliteCount.collectAsState()
    val batteryPercentage by viewModel.batteryRemaining.collectAsState()
    val droneSpeed by viewModel.speed.collectAsState()

    Card(
        modifier =
        Modifier
            .padding(all = dimensionResource(id = R.dimen.default_padding))
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        )
    ) {
        Row {
            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.card_column_padding))
            ) {
                Text(text = "Altitude")
                Text("${relativeAltitudeFloatState.formatToTenths()} m")
            }

            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.card_column_padding))
            ) {
                Text(text = "Speed")
                Text(droneSpeed.calculateMphFromVelocity().formatToTenths().addMph())
            }

            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.card_column_padding))
            ) {
                Text(text = "GPS")
                Text(satelliteCountState.toString())
            }

            Column(
                modifier = Modifier.padding(dimensionResource(id = R.dimen.card_column_padding))
            ) {
                Text(text = "Battery")
                Text(batteryPercentage.formatTenthsAndHundredths().addPercentSign())
            }
        }

    }
}

@Composable
fun FlightButtons(viewModel: ControlScreenViewModel) {
    Card(
        modifier =
        Modifier
            .padding(all = dimensionResource(id = R.dimen.default_padding))
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TakeOffLandButtons(viewModel)
            Row {
                ElevationButtons(viewModel)
                DirectionalButtons(viewModel)
            }
        }
    }
}

@Composable
fun TakeOffLandButtons(viewModel: ControlScreenViewModel) {
    Row {
        ElevatedButton(onClick = {
            viewModel.takeoff()
        }) {
            Text(stringResource(id = R.string.btn_takeoff))
        }

        ElevatedButton(onClick = {
            viewModel.land()
        }) {
            Text(stringResource(id = R.string.btn_land))
        }
    }
}

@Composable
fun ElevationButtons(viewModel: ControlScreenViewModel) {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedButton(onClick = {
            viewModel.moveUp()
        }) {
            Text(stringResource(id = R.string.btn_up))
        }

        // TODO: Fix stop functionality: altitude throws it off.
        /*ElevatedButton(onClick = {
            viewModel.stopDrone = true
            viewModel.stop()
        }) {
            Text(stringResource(id = R.string.btn_stop))
        }*/

        ElevatedButton(onClick = {
            viewModel.moveDown()
        }) {
            Text(stringResource(id = R.string.btn_down))
        }
    }
}

@Composable
fun DirectionalButtons(viewModel: ControlScreenViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = {
            viewModel.moveForward()
        }) {
            Text(stringResource(id = R.string.btn_forward))
        }

        Row {
            ElevatedButton(onClick = {
                viewModel.moveLeft()
            }) {
                Text(stringResource(id = R.string.btn_left))
            }

            ElevatedButton(onClick = {
                viewModel.moveRight()
            }) {
                Text(stringResource(id = R.string.btn_right))
            }
        }

        ElevatedButton(onClick = {
            viewModel.moveBackward()
        }) {
            Text(stringResource(id = R.string.btn_backward))
        }
    }
}