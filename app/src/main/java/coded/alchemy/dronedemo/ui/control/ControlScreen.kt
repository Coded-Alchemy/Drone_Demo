package coded.alchemy.dronedemo.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import coded.alchemy.dronedemo.R
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
        TelemetryPanel()
        FlightButtons()
    }
}

@Composable
fun TelemetryPanel() {
    Row {
        Column {
            Text(text = "Altitude")

            // THis needs to be corrected
            val altitudeState = remember { mutableStateOf(TextFieldValue()) }
            Text(altitudeState.value.text)
        }
        Column {
            Text(text = "Speed")

            // THis needs to be corrected
            val altitudeState = remember { mutableStateOf(TextFieldValue()) }
            Text(altitudeState.value.text)
        }
        Column {
            Text(text = "GPS")

            // THis needs to be corrected
            val altitudeState = remember { mutableStateOf(TextFieldValue()) }
            Text(altitudeState.value.text)
        }
        Column {
            Text(text = "Battery")

            // THis needs to be corrected
            val altitudeState = remember { mutableStateOf(TextFieldValue()) }
            Text(altitudeState.value.text)
        }
    }
}

@Composable
fun FlightButtons() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TakeOffLandButtons()
        Row {
            ElevationButtons()
            DirectionalButtons()
        }
    }
}

@Composable
fun TakeOffLandButtons() {
    Row {
        ElevatedButton(onClick = {

        }) {
            Text(stringResource(id = R.string.btn_takeoff))
        }

        ElevatedButton(onClick = {

        }) {
            Text(stringResource(id = R.string.btn_land))
        }
    }
}


@Composable
fun ElevationButtons() {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedButton(onClick = {

        }) {
            Text(stringResource(id = R.string.btn_up))
        }

        ElevatedButton(onClick = {

        }) {
            Text(stringResource(id = R.string.btn_down))
        }
    }
}

@Composable
fun DirectionalButtons() {
    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = {

        }) {
            Text(stringResource(id = R.string.btn_forward))
        }

        Row {
            ElevatedButton(onClick = {

            }) {
                Text(stringResource(id = R.string.btn_left))
            }
            ElevatedButton(onClick = {

            }) {
                Text(stringResource(id = R.string.btn_right))
            }
        }

        ElevatedButton(onClick = {

        }) {
            Text(stringResource(id = R.string.btn_down))
        }
    }
}