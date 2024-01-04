package coded.alchemy.dronedemo.domain.usecase

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * GetGpsDataUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] GPS data.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetGpsDataUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    private val _satelliteCount = MutableStateFlow(Int.MIN_VALUE)
    val satelliteCount: StateFlow<Int> = _satelliteCount

    operator fun invoke() {
        scope.launch {
            droneRepository.drone.telemetry.gpsInfo.distinctUntilChanged().subscribe(
                { gpsInfo: Telemetry.GpsInfo ->
                    _satelliteCount.value = gpsInfo.numSatellites
                },
                { error ->
                    Log.e(TAG, "Error in GPS telemetry subscription $error", error)
                }
            )
        }
    }
}