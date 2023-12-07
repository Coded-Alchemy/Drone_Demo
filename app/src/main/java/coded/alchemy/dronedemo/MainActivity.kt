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
import coded.alchemy.dronedemo.network.NetworkMonitor
import coded.alchemy.dronedemo.ui.app.DroneDemoApp
import coded.alchemy.dronedemo.ui.theme.DroneDemoTheme
import org.koin.android.ext.android.inject
import org.vosk.Model
import org.vosk.android.RecognitionListener
import org.vosk.android.SpeechService
import org.vosk.android.SpeechStreamService
import org.vosk.android.StorageService
import java.io.IOException

/**
 * MainActivity.kt
 *
 * Application entry point. This application uses Jetpack Compose for the UI.
 * @author Taji Abdullah
 * */
class MainActivity : ComponentActivity(), RecognitionListener {
    private val TAG = this.javaClass.simpleName

    private lateinit var model: Model
    private lateinit var speechService: SpeechService
    private lateinit var speechStreamService: SpeechStreamService

    private val getRecordAudioPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissible ->
            Log.d(TAG, "getRecordAudioPermission: ")
            when {
                permissible -> {
                    Log.d(TAG, "permission granted")
                    initSpeechModel()
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

    /**
     * [onStop] is used to destroy resources utilizing the applications lifecycle.
     * */
    override fun onStop() {
        Log.d(TAG, "onStop: ")
        shutDownDrone()
        super.onStop()
    }

    override fun onPartialResult(hypothesis: String?) {
        TODO("Not yet implemented")
    }

    override fun onResult(hypothesis: String?) {
        TODO("Not yet implemented")
    }

    override fun onFinalResult(hypothesis: String?) {
        TODO("Not yet implemented")
    }

    override fun onError(exception: Exception?) {
        TODO("Not yet implemented")
    }

    override fun onTimeout() {
        TODO("Not yet implemented")
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

    private fun initSpeechModel() {
        Log.d(TAG, "initSpeechModel: ")
        // TODO: run on IO Dispatcher
        StorageService.unpack(
            this,
            "model-en-us",
            "model",
            { unpackedModel: Model ->
                model = unpackedModel
                // Additional logic if needed after unpacking the model
            },
            { exception: IOException ->
                Log.e(TAG, "initModel: $exception")
            }
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
        val serverRepository: ServerRepository by inject()
        val droneRepository: DroneRepository by inject()
        droneRepository.drone.dispose()
        serverRepository.mavServer().stop()
        serverRepository.mavServer().destroy()
    }
}
