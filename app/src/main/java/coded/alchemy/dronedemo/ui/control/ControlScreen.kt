package coded.alchemy.dronedemo.ui.control

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}

@Composable
fun FlightButtons() {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedButton(onClick = {

        }) {
            Text(stringResource(id = R.string.btn_connect))
        }
    }
}