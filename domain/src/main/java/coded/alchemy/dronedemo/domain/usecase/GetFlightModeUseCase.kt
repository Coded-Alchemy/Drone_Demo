package coded.alchemy.dronedemo.domain.usecase

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * GetFlightModeUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] flight mode.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetFlightModeUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    private val _flightMode = MutableStateFlow("")
    val flightMode: StateFlow<String> = _flightMode

    operator fun invoke() {
        scope.launch {
            droneRepository.drone.telemetry.flightMode.distinctUntilChanged()
                .subscribe(
                    { flightMode: Telemetry.FlightMode ->
                        Log.d(TAG, "flight mode: $flightMode")
                        _flightMode.value = flightMode.toString()
                    },
                    { error ->
                        Log.e(TAG, "Error in flight mode telemetry subscription $error", error)
                    })
        }
    }
}