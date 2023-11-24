package coded.alchemy.dronedemo.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DemoScreen(viewModel: ScreenViewModel = koinViewModel()) {
    Column {
        ConnectButton(viewModel)
        ArmButton(viewModel)
    }
}

@Composable
fun ConnectButton(viewModel: ScreenViewModel) {
    ElevatedButton(onClick = { viewModel.connect() }) {
        Text("Start Server")
    }
}

@Composable
fun ArmButton(viewModel: ScreenViewModel) {
    ElevatedButton(onClick = { /*onClick()*/ }) {
        Text("Arm Drone")
    }
}

@Preview
@Composable
fun DemoScreenPreview() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
//            EButton()
        }
    }
}
