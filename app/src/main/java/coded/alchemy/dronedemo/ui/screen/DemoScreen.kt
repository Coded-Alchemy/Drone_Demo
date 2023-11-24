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

@Composable
fun DemoScreen(onConnect: () -> Unit, onArm: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            ConnectButton(onConnect)
            ArmButton(onArm)
        }
    }
}

@Composable
fun ConnectButton(onClick: () -> Unit) {
    ElevatedButton(onClick = { onClick() }) {
        Text("Start Server")
    }
}

@Composable
fun ArmButton(onClick: () -> Unit) {
    ElevatedButton(onClick = { onClick() }) {
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
