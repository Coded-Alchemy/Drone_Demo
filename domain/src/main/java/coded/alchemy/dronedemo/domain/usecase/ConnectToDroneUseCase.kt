package coded.alchemy.dronedemo.domain.usecase

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import io.mavsdk.System
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ConnectToDroneUseCase.kt
 *
 * This class is responsible for connecting to the [DroneRepository.drone].
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @param serverRepository [ServerRepository] gives access to [ServerRepository.mavServer]
 * @author Taji Abdullah
 * */
class ConnectToDroneUseCase(
    private val droneRepository: DroneRepository,
    private val serverRepository: ServerRepository,
    private val getConnectionStateUseCase: GetConnectionStateUseCase
) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    val isDroneConnected: StateFlow<Boolean?> get() = getConnectionStateUseCase.isDroneConnected

    private val _isDroneConnecting = MutableStateFlow(false)
    val isDroneConnecting: StateFlow<Boolean> get() = _isDroneConnecting

    operator fun invoke() {
        Log.d(TAG, "invoke: ")
        _isDroneConnecting.value = true
        scope.launch {
            try {
                val port = serverRepository.mavServer().run()
                droneRepository.drone = System(serverRepository.host, port)
                getConnectionStateUseCase()
            } catch (exception: Exception) {
                Log.e(TAG, exception.toString())
                serverRepository.mavServer().stop()
                serverRepository.mavServer().destroy()
            }
        }
    }
}