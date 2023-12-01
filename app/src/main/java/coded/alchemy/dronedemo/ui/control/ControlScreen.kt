package coded.alchemy.dronedemo.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.util.appendMph
import coded.alchemy.dronedemo.util.appendPercentSign
import coded.alchemy.dronedemo.util.calculateMphFromVelocity
import coded.alchemy.dronedemo.util.formatToTenthsAndHundredths
import coded.alchemy.dronedemo.util.formatToTenths
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
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
    val droneLatitude by viewModel.latitudeDegDouble.collectAsState()
    val droneLongitude by viewModel.longitudeDegDouble.collectAsState()
    val relativeAltitudeFloatState by viewModel.relativeAltitudeFloat.collectAsState()
    val satelliteCountState by viewModel.satelliteCount.collectAsState()
    val batteryPercentage by viewModel.batteryRemaining.collectAsState()
    val droneSpeed by viewModel.speed.collectAsState()
    val isNetworkConnected by viewModel.isNetworkConnected.collectAsState()

    if (isNetworkConnected) {
        ConstraintLayout {
            val (mapCard, telemetryCard, buttonCard) = createRefs()

            Card(
                modifier =
                Modifier
                    .padding(all = dimensionResource(id = R.dimen.default_padding))
                    .height(intrinsicSize = IntrinsicSize.Max)
                    .constrainAs(mapCard) {
                        top.linkTo(parent.top, margin = 16.dp)
                        bottom.linkTo(telemetryCard.top, margin = 16.dp)
                    },
                elevation = CardDefaults.cardElevation(
                    defaultElevation = dimensionResource(id = R.dimen.card_elevation)
                )
            ) {
                val dronePosition = LatLng(droneLatitude, droneLongitude)
                val cameraPositionState = rememberCameraPositionState {
                    position = CameraPosition.fromLatLngZoom(LatLng(droneLatitude, droneLongitude), 100f)
                }
                GoogleMap(
                    cameraPositionState = cameraPositionState
                ) {
                    Marker(
                        state = MarkerState(position = dronePosition),
                        title = "Drone",
                        snippet = "Drone Marker"
                    )
                }
            }


            Card(
                modifier =
                Modifier
                    .padding(all = dimensionResource(id = R.dimen.default_padding))
                    .fillMaxWidth()
                    .constrainAs(telemetryCard) {
                        bottom.linkTo(buttonCard.top, margin = 16.dp)
                    },
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

            Card(
                modifier =
                Modifier
                    .padding(all = dimensionResource(id = R.dimen.default_padding))
                    .fillMaxWidth()
                    .constrainAs(buttonCard) {
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    },
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
    } else {
        DisconnectedNetworkMessage()
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
        ElevatedButton(onClick = {
            viewModel.stop()
        }) {
            Text(stringResource(id = R.string.btn_stop))
        }

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

/**
 * [Composable] to provide a a message when wifi is needed.
 * */
@Composable
fun DisconnectedNetworkMessage() {
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
