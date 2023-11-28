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

/**
 * MainActivity.kt
 *
 * Application entry point. This application uses Jetpack Compose for the UI.
 * @property serverRepository is a private [ServerRepository] dependency injection.
 * @property droneRepository is a private [DroneRepository] dependency injection.
 * @author Taji Abdullah
 * */
class MainActivity : ComponentActivity() {
    private val TAG = this.javaClass.simpleName
    private val serverRepository: ServerRepository by inject()
    private val droneRepository: DroneRepository by inject()

    /**
     * [onCreate] sets up the Composable UI.
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContent {
            DroneDemoTheme {
                DroneDemoApp()
            }
        }
    }

    /**
     * [onStop] is used to destroy resources utilizing the applications lifecycle.
     * */
    override fun onStop() {
        Log.d(TAG, "onStop: ")
        releaseResources()
        super.onStop()
    }

    private fun releaseResources() {
        Log.d(TAG, "releaseResources: ")
        try {
            droneRepository.drone.dispose()
            serverRepository.mavServer().stop()
            serverRepository.mavServer().destroy()
        } catch (e: Exception) {
            Log.w(TAG, "releaseResources: Things have not been instantiated yet.")
        }
    }
}
