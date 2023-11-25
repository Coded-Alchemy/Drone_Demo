package coded.alchemy.dronedemo.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.ui.connection.ConnectButton
import coded.alchemy.dronedemo.ui.navigation.Route


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControlScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FlightButtons()
    }
}

@Composable
fun TelemetryPanel() {

}

@Composable
fun FlightButtons() {
    Row {
        ElevationButtons()
        DirectionalButtons()
    }
}


@Composable
fun ElevationButtons() {
    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight(),
//        horizontalAlignment = Alignment.CenterHorizontally
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