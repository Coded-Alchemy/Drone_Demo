package coded.alchemy.dronedemo.ui.connection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import io.mavsdk.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

/**
 * ConnectionScreenViewModel.kt
 *
 * This class contains the drone connection logic.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @param serverRepository [ServerRepository] gives access to [ServerRepository.mavServer].
 * @property isConnected [Boolean] observable by [StateFlow] to expose [_buttonClicked] value
 * to determine if a [System] is connected.
 * @author Taji Abdullah
 * TODO: Improve this class by introducing a UseCase class.
 * */
class ConnectionScreenViewModel(
    private val droneRepository: DroneRepository,
    private val serverRepository: ServerRepository
) : ViewModel() {
    private val TAG = this.javaClass.simpleName
    var buttonClicked = false

//    fun connect() : Flow<ConnectionState> = flow {
//        Log.d(TAG, "connect: ")
////        emit(ConnectionState.Connecting)
//        viewModelScope.launch(Dispatchers.IO) {
//            when (_isConnected.value) {
//                 true -> emit(ConnectionState.Connected)
//                else -> emit(ConnectionState.Error("Something went wrong..."))
//            }
//        }
//    }.flowOn(Dispatchers.IO)

    fun initiateDroneConnection() : Flow<ConnectionState> = flow {
        Log.d(TAG, "initiateDroneConnection: ")
        if (buttonClicked) {
            emit(ConnectionState.Connecting)
            connectToDrone { result ->
                viewModelScope.launch(Dispatchers.IO) {
                    if (result) {
                        Log.d(TAG, "Drone connected successfully.")
                        emit(ConnectionState.Connected)
                    } else {
                        Log.e(TAG, "Failed to connect to the drone.")
                        emit(ConnectionState.Error("Something went wrong."))
                    }
                    buttonClicked = false
                }
            }
        }

    }.flowOn(Dispatchers.IO)

    private fun connectToDrone(onConnectionResult: (Boolean) -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val port = serverRepository.mavServer().run()
                val drone = System(serverRepository.host, port)
                
                droneRepository.drone = drone
                onConnectionResult(true)
            } catch (e: Exception) {
                Log.e(TAG, "connectToDrone: $e")
                onConnectionResult(false)
            }
        }
    }

//    private fun connectToDrone(): Boolean {
//        viewModelScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "connectToDrone: ")
//            return try {
//                val port = serverRepository.mavServer().run()
//                val drone = System(serverRepository.host, port)
//
//
//                droneRepository.drone = drone
//                Log.d(TAG, "connectToDrone: success")
//                true
//            } catch (e: Exception) {
//                Log.e(TAG, "connectToDrone: $e")
//                false
//            }
//        }
//    }
}

