package coded.alchemy.dronedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coded.alchemy.dronedemo.ui.theme.DroneDemoTheme
import io.mavsdk.MavsdkEventQueue
import io.mavsdk.mavsdkserver.MavsdkServer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DroneDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        // Create an instance of MavsdkServer
        val server = MavsdkServer()
        val SYSTEM_ADDRESS = "192.168.0.24"
        val MAVSDK_SERVER_PORT = 14540

        // Use MavsdkEventQueue.executor() to get an executor and execute the server run operation
        MavsdkEventQueue.executor().execute {
            server.run(SYSTEM_ADDRESS, MAVSDK_SERVER_PORT)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DroneDemoTheme {
        Greeting("Android")
    }
}