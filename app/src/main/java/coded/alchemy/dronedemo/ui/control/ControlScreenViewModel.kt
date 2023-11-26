package coded.alchemy.dronedemo.ui.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ControlScreenViewModel(private val droneRepository: DroneRepository) : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private val drone = droneRepository.drone

    private val _relativeAltitudeFloat = MutableStateFlow(Float.MIN_VALUE)
    val relativeAltitudeFloat: StateFlow<Float> = _relativeAltitudeFloat

    private val _absoluteAltitudeFloat = MutableStateFlow(Float.MIN_VALUE)
    val absoluteAltitudeFloat: StateFlow<Float> = _absoluteAltitudeFloat

    private val _latitudeDegDouble = MutableStateFlow(Double.MIN_VALUE)
    val latitudeDegDouble: StateFlow<Double> = _latitudeDegDouble

    private val _longitudeDegDouble = MutableStateFlow(Double.MIN_VALUE)
    val longitudeDegDouble: StateFlow<Double> = _longitudeDegDouble

    private val _flightMode = MutableStateFlow("")
    val flightMode: StateFlow<String> = _flightMode

    init {
        getTelemetryData()
    }

    fun takeoff() {
        Log.d(TAG, "takeoff: ")
        viewModelScope.launch(Dispatchers.IO) {
            drone.action.arm().andThen(drone.action.takeoff()).subscribe(
                {
                    // onNext - handle the result
                },
                { error ->
                    Log.e(TAG, "takeoff: ", error)
                }
            )
        }
    }

    fun land() {
        Log.d(TAG, "land: ")
        viewModelScope.launch(Dispatchers.IO) {
            drone.action.land().subscribe(
                {
                    // onNext - handle the result
                },
                { error ->
                    Log.e(TAG, "land: $error", error)
                }
            )
        }
    }

    fun moveUp() {
        Log.d(TAG, "moveUp: ")
        val newAltitude = _absoluteAltitudeFloat.value + 10.0F
        moveDrone(
            latitude = _latitudeDegDouble.value,
            longitude = _longitudeDegDouble.value,
            altitude = newAltitude,
            yawDegree = 0F
        )
    }

    fun moveDown() {
        Log.d(TAG, "moveDown: ")
        val newAltitude = _absoluteAltitudeFloat.value - 10.0F
        moveDrone(
            latitude = _latitudeDegDouble.value,
            longitude = _longitudeDegDouble.value,
            altitude = newAltitude,
            yawDegree = 0F
        )
    }

    fun moveRight() {
        Log.d(TAG, "moveForward: ")
        val newLongitude = _longitudeDegDouble.value + 2
        moveDrone(
            latitude = _latitudeDegDouble.value,
            longitude = newLongitude,
            altitude = _relativeAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    private fun moveDrone(latitude: Double, longitude: Double, altitude: Float, yawDegree: Float) {
        Log.d(TAG, "moveDrone: ")
        viewModelScope.launch(Dispatchers.IO) {
            drone.action.gotoLocation(latitude, longitude, altitude, yawDegree).subscribe(
                {
                    // onNext - handle the result
                },
                { error ->
                    Log.e(TAG, "moveDrone: $error", error)
                }
            )
        }
    }

    private fun getTelemetryData() {
        Log.d(TAG, "getTelemetryData: ")
        getPositionData()
        getFlightMode()
        getArmedValue()
    }

    private fun getPositionData() {
        Log.d(TAG, "getPositionData: ")
        viewModelScope.launch(Dispatchers.IO) {
            droneRepository.drone.telemetry.position
                .subscribe(
                    { position: Telemetry.Position ->
                        _relativeAltitudeFloat.value = position.relativeAltitudeM
                        _absoluteAltitudeFloat.value = position.absoluteAltitudeM
                        _latitudeDegDouble.value = position.latitudeDeg
                        _longitudeDegDouble.value = position.longitudeDeg
                    },
                    { error ->
                        Log.e(TAG, "Error in position telemetry subscription", error)
                    })
        }
    }

    private fun getFlightMode() {
        Log.d(TAG, "getFlightMode: ")
        viewModelScope.launch(Dispatchers.IO) {
            droneRepository.drone.telemetry.flightMode.distinctUntilChanged()
                .subscribe(
                    { flightMode: Telemetry.FlightMode ->
                        Log.d(TAG, "flight mode: $flightMode")
                        _flightMode.value = flightMode.toString()
                    },
                    { error ->
                        Log.e(TAG, "Error in flight mode telemetry subscription", error)
                    })
        }
    }

    private fun getArmedValue() {
        Log.d(TAG, "getArmedValue: ")
        viewModelScope.launch(Dispatchers.IO) {
            droneRepository.drone.telemetry.armed.distinctUntilChanged()
                .subscribe(
                    { armed: Boolean ->
                        Log.d(TAG, "armed: $armed")
                    },
                    { error ->
                        Log.e(TAG, "Error in armed telemetry subscription", error)
                    }
                )
        }
    }
}