package coded.alchemy.dronedemo.ui.connectionscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel


@Composable
fun ConnectionScreen(viewModel: ScreenViewModel = koinViewModel()) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConnectButton(viewModel)
    }
}

@Composable
fun ConnectButton(viewModel: ScreenViewModel) {
    ElevatedButton(onClick = { viewModel.connect() }) {
        Text("Connect Drone")
    }
}

