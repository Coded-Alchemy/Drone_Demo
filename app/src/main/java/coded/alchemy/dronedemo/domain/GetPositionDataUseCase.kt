package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * GetPositionDataUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] position data.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetPositionDataUseCase(private val droneRepository: DroneRepository): DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    private val _relativeAltitudeFloat = MutableStateFlow(Float.MIN_VALUE)
    val relativeAltitudeFloat: StateFlow<Float> = _relativeAltitudeFloat

    private val _absoluteAltitudeFloat = MutableStateFlow(Float.MIN_VALUE)
    val absoluteAltitudeFloat: StateFlow<Float> = _absoluteAltitudeFloat

    private val _latitudeDegDouble = MutableStateFlow(Double.MIN_VALUE)
    val latitudeDegDouble: StateFlow<Double> = _latitudeDegDouble

    private val _longitudeDegDouble = MutableStateFlow(Double.MIN_VALUE)
    val longitudeDegDouble: StateFlow<Double> = _longitudeDegDouble

    operator fun invoke() {
        scope.launch(Dispatchers.Default) {
            try {
                droneRepository.drone.telemetry.position
                    .subscribe(
                        { position: Telemetry.Position ->
                            _relativeAltitudeFloat.value = position.relativeAltitudeM
                            _absoluteAltitudeFloat.value = position.absoluteAltitudeM
                            _latitudeDegDouble.value = position.latitudeDeg
                            _longitudeDegDouble.value = position.longitudeDeg
                        },
                        { error ->
                            Log.e(TAG, "Error in position telemetry subscription $error", error)
                        })
            } catch (e: Exception) {
                Log.e(TAG, "getPositionData: $e")
            }
        }
    }
}
