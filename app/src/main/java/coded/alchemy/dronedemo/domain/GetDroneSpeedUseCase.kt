package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * GetDroneSpeedUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] speed.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetDroneSpeedUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    private val _speed = MutableStateFlow(Float.MIN_VALUE)
    val speed: StateFlow<Float> = _speed
    operator fun invoke() {
        scope.launch {
            droneRepository.drone.telemetry.rawGps.subscribe(
                { metrics: Telemetry.RawGps ->
                    _speed.value = metrics.velocityMS
                },
                { error ->
                    Log.e(TAG, "Error in raw GPS subscription $error", error)
                }
            )
        }
    }
}