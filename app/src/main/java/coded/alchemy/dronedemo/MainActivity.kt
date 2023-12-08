package coded.alchemy.dronedemo

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import coded.alchemy.dronedemo.data.SpeechRecognizer
import coded.alchemy.dronedemo.network.NetworkMonitor
import coded.alchemy.dronedemo.ui.app.DroneDemoApp
import coded.alchemy.dronedemo.ui.theme.DroneDemoTheme
import org.koin.android.ext.android.inject

/**
 * MainActivity.kt
 *
 * Application entry point. This application uses Jetpack Compose for the UI.
 * @author Taji Abdullah
 * */
class MainActivity : ComponentActivity() {
    private val TAG = this.javaClass.simpleName

    private var speechRecognizer = SpeechRecognizer

    private val getRecordAudioPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissible ->
            Log.d(TAG, "getRecordAudioPermission: ")
            when {
                permissible -> {
                    Log.d(TAG, "permission granted")
                    speechRecognizer.initSpeechModel(this)
                }

                !shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO) -> {
                    Log.d(TAG, "permission denied, dont ask again")
                }

                else -> {
                    Log.d(TAG, "permission denied")
                }
            }
        }

    /**
     * [onCreate] starts the monitoring of network connectivity.
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setUpNetworkMonitoring()
        checkRecordAudioPermission()
    }

    /**
     * [onResume] sets up the Composable UI.
     * */
    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: ")
        setContent {
            DroneDemoTheme {
                DroneDemoApp()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        speechRecognizer.pause(true)
    }

    /**
     * [onStop] is used to destroy resources utilizing the applications lifecycle.
     * */
    override fun onStop() {
        Log.d(TAG, "onStop: ")
        shutDownDrone()
        speechRecognizer.destroy()
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

    private fun checkRecordAudioPermission() {
        Log.d(TAG, "checkRecordAudioPermission: ")
        if (shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO)) {
            Log.w(TAG, "checkRecordAudioPermission: permission denied, surface explanation to user")
        } else {
            getRecordAudioPermission.launch(Manifest.permission.RECORD_AUDIO)
        }
    }

    /**
     * Shut down drone resources.
     * */
    private fun shutDownDrone() {
        Log.d(TAG, "shutDownDrone: ")
        try {
            val serverRepository: ServerRepository by inject()
            val droneRepository: DroneRepository by inject()
            droneRepository.drone.dispose()
            serverRepository.mavServer().stop()
            serverRepository.mavServer().destroy()
        } catch (e: Exception) {
            Log.e(TAG, "shutDownDrone: $e")
        }
    }
}
