package coded.alchemy.dronedemo.domain.usecase

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.core.Core
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * GetConnectionStateUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] heartbeat data.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetConnectionStateUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    private val _isDroneConnected = MutableStateFlow<Boolean?>(false)
    val isDroneConnected: StateFlow<Boolean?> get() = _isDroneConnected

    operator fun invoke() {
        scope.launch {
            droneRepository.drone.core.connectionState.distinctUntilChanged().subscribe(
                { state: Core.ConnectionState ->
                    Log.d(TAG, "Connection state: ${state.isConnected}")
                    _isDroneConnected.value = state.isConnected
                },
                { error ->
                    Log.e(TAG, "Error in connection state subscription $error", error)
                }
            )
        }
    }
}