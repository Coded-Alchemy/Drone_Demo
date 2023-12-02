package coded.alchemy.dronedemo.ui.connection

import android.util.Log
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import coded.alchemy.dronedemo.ui.app.DroneDemoViewModel
import io.mavsdk.System
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ConnectionScreenViewModel.kt
 *
 * This class contains the drone connection logic.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @param serverRepository [ServerRepository] gives access to [ServerRepository.mavServer].
 * @property isDroneConnected [Boolean] observable by [StateFlow] to expose [_isDroneConnected] value
 * to determine if a [System] is connected.
 * @author Taji Abdullah
 * TODO: Improve this class by introducing a UseCase class.
 * */
class ConnectionScreenViewModel(
    private val droneRepository: DroneRepository,
    private val serverRepository: ServerRepository
) : DroneDemoViewModel() {
    private val TAG = this.javaClass.simpleName
    private val _isDroneConnected = MutableStateFlow<Boolean?>(false)
    val isDroneConnected: StateFlow<Boolean?> get() = _isDroneConnected

    fun connect() {
        Log.d(TAG, "connect: ")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val port = serverRepository.mavServer().run()
                droneRepository.drone = System(serverRepository.host, port)
                _isDroneConnected.emit(true)
            } catch (exception: Exception) {
                Log.e(TAG, exception.toString())
                _isDroneConnected.emit(false)
                serverRepository.mavServer().stop()
                serverRepository.mavServer().destroy()
            }
        }
    }
}

