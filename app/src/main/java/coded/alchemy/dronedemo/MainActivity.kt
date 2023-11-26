package coded.alchemy.dronedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import coded.alchemy.dronedemo.ui.app.DroneDemoApp
import coded.alchemy.dronedemo.ui.theme.DroneDemoTheme
import org.koin.android.ext.android.inject


class MainActivity : ComponentActivity() {
    private val TAG = this.javaClass.simpleName
    private val serverRepository: ServerRepository by inject()
    private val droneRepository: DroneRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContent {
            DroneDemoTheme {
                DroneDemoApp()
            }
        }
    }

    override fun onStop() {
        Log.d(TAG, "onStop: ")
        droneRepository.drone.dispose()
        serverRepository.mavServer().stop()
        serverRepository.mavServer().destroy()
        super.onStop()
    }
}
