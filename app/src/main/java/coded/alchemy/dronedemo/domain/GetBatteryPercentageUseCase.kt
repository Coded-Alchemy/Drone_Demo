package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * GetBatteryPercentageUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] battery percentage.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetBatteryPercentageUseCase(private val droneRepository: DroneRepository): DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    private val _batteryRemaining = MutableStateFlow(Float.MIN_VALUE)
    val batteryRemaining: StateFlow<Float> = _batteryRemaining

    operator fun invoke() {
        scope.launch {
            droneRepository.drone.telemetry.battery.distinctUntilChanged().subscribe(
                { battery: Telemetry.Battery ->
                    _batteryRemaining.value = battery.remainingPercent
                },
                { error ->
                    Log.e(TAG, "Error in battery telemetry subscription $error", error)
                }
            )
        }
    }
}