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
import coded.alchemy.dronedemo.util.appendMph
import coded.alchemy.dronedemo.util.appendPercentSign
import coded.alchemy.dronedemo.util.calculateMphFromVelocity
import coded.alchemy.dronedemo.util.formatToTenthsAndHundredths
import coded.alchemy.dronedemo.util.formatToTenths
import org.koin.androidx.compose.koinViewModel

/**
 * ControlScreen.kt
 *
 * This [Composable] function declares the [ControlScreen].
 * This screen provides the UI to control a MavLink System.
 * @author Taji Abdullah
 * */
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

/**
 * This [Composable] provides the [TelemetryPanel] responsible for displaying telemetry
 * data from a MavLink System.
 * */
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
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
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
                Text(droneSpeed.calculateMphFromVelocity().formatToTenths().appendMph())
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
                Text(batteryPercentage.formatToTenthsAndHundredths().appendPercentSign())
            }
        }
    }
}

/**
 * This [Composable] provides the [FlightButtons] responsible controlling a MavLink System.
 * it is comprised of [TakeOffLandButtons], [ElevationButtons], and [DirectionalButtons].
 * */
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
            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.default_padding)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TakeOffLandButtons(viewModel)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ElevationButtons(viewModel)
                DirectionalButtons(viewModel)
            }
        }
    }
}

/**
 * This [Composable] provides the [TakeOffLandButtons] responsible for the takeoff and
 * landing of a MavLink System.
 * */
@Composable
fun TakeOffLandButtons(viewModel: ControlScreenViewModel) {
    Row {
        ElevatedButton(onClick = {
            viewModel.takeoff()
        }) {
            Text(stringResource(id = R.string.btn_takeoff))
        }

        ElevatedButton(onClick = {
            viewModel.orbit()
        }) {
            Text(stringResource(id = R.string.btn_orbit))
        }

        ElevatedButton(onClick = {
            viewModel.land()
        }) {
            Text(stringResource(id = R.string.btn_land))
        }
    }
}

/**
 * This [Composable] provides the [ElevationButtons] responsible for altitude of a MavLink System.
 * */
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

/**
 * This [Composable] provides the [DirectionalButtons] responsible for moving a MavLink System
 * backward, forward, left, and right.
 * */
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