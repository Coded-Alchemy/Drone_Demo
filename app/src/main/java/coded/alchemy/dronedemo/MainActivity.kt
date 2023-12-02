package coded.alchemy.dronedemo

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import coded.alchemy.dronedemo.network.NetworkMonitor
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

    /**
     * [onCreate] starts the monitoring of network connectivity.
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setUpNetworkMonitoring()
    }

    /**
     * [onResume] sets up the Composable UI.
     * */
    override fun onResume() {
        super.onResume()
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
        val serverRepository: ServerRepository by inject()
        val droneRepository: DroneRepository by inject()

        droneRepository.drone.dispose()
        serverRepository.mavServer().stop()
        serverRepository.mavServer().destroy()
        super.onStop()
    }

    /**
     * This function initializes the network connectivity monitoring capabilities.
     * */
    private fun setUpNetworkMonitoring() {
        Log.d(TAG, "setUpNetworkMonitoring: ")
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        connectivityManager?.requestNetwork(
            NetworkMonitor.networkRequest,
            NetworkMonitor.networkCallback
        )
    }
}
