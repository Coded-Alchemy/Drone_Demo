package coded.alchemy.dronedemo.ui.control

import android.util.Log
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.ui.app.DroneDemoViewModel
import io.mavsdk.action.Action
import io.mavsdk.mission.Mission
import io.mavsdk.telemetry.Telemetry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ControlScreenViewModel.kt
 *
 * This class contains the drone connection logic.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @property relativeAltitudeFloat [StateFlow] [Float] that observes
 * [_relativeAltitudeFloat] to expose it publicly. This is the [drone] altitude used.
 * @property absoluteAltitudeFloat [StateFlow] [Float] that observes
 * [_absoluteAltitudeFloat] to expose it publicly. This is not currently used.
 * @property latitudeDegDouble [StateFlow] [Double] that observes
 * [_latitudeDegDouble] to expose it publicly. This is the [drone] latitude.
 * @property longitudeDegDouble [StateFlow] [Double] that observes
 * [_longitudeDegDouble] to expose it publicly. This is the [drone] longitude.
 * @property flightMode [StateFlow] [String] that observes
 * [_flightMode] to expose it publicly. This is the [drone] current flight mode.
 * @property satelliteCount [StateFlow] [Int] that observes
 * [_satelliteCount] to expose it publicly. This is the [drone] satellite count.
 * @property batteryRemaining [StateFlow] [Float] that observes
 * [_batteryRemaining] to expose it publicly. This is the [drone] battery percentage.
 * @property speed [StateFlow] [Float] that observes
 * [_speed] to expose it publicly. This is the [drone] velocity.
 * @author Taji Abdullah
 * TODO: Improve this class by introducing UseCase classes to abstract business logic.
 * */
class ControlScreenViewModel(private val droneRepository: DroneRepository) : DroneDemoViewModel() {
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

    private val _satelliteCount = MutableStateFlow(Int.MIN_VALUE)
    val satelliteCount: StateFlow<Int> = _satelliteCount

    private val _batteryRemaining = MutableStateFlow(Float.MIN_VALUE)
    val batteryRemaining: StateFlow<Float> = _batteryRemaining

    private val _speed = MutableStateFlow(Float.MIN_VALUE)
    val speed: StateFlow<Float> = _speed

    /**
     * This function gets called when this class is instantiated to start collecting
     * the telemetry data.
     * */
    init {
        getTelemetryData()
    }

    /**
     * This function makes the [drone] start to fly.
     * */
    fun takeoff() {
        Log.d(TAG, "takeoff: ")
        viewModelScope.launch(Dispatchers.IO) {
            drone.action.arm().andThen(drone.action.takeoff()).subscribe(
                {
                    // onNext - handle the result
                },
                { error ->
                    Log.e(TAG, "takeoff: $error", error)
                }
            )
        }
    }

    /**
     * This function makes the [drone] land.
     * */
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

    /**
     * This function calls [moveDrone] to move the [drone] down/ascend.
     * */
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

    /**
     * This function calls [moveDrone] to move the [drone] down/descend.
     * */
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

    /**
     * This function calls [moveDrone] to move the [drone] right.
     * */
    fun moveRight() {
        Log.d(TAG, "moveRight: ")
        val newLongitude = _longitudeDegDouble.value + 1
        moveDrone(
            latitude = _latitudeDegDouble.value,
            longitude = newLongitude,
            altitude = _relativeAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] left.
     * */
    fun moveLeft() {
        Log.d(TAG, "moveLeft: ")
        val newLongitude = _longitudeDegDouble.value - 1
        moveDrone(
            latitude = _latitudeDegDouble.value,
            longitude = newLongitude,
            altitude = _relativeAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] forward.
     * */
    fun moveForward() {
        Log.d(TAG, "moveForward: ")
        val newLatitude = _latitudeDegDouble.value + 1
        moveDrone(
            latitude = newLatitude,
            longitude = _longitudeDegDouble.value,
            altitude = _relativeAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] backwards.
     * */
    fun moveBackward() {
        Log.d(TAG, "moveBackward: ")
        val newLatitude = _latitudeDegDouble.value - 1
        moveDrone(
            latitude = newLatitude,
            longitude = _longitudeDegDouble.value,
            altitude = _relativeAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function is used to stop the [drone] from moving.
     * */
    fun stop() {
        Log.d(TAG, "stop: ")
        moveDrone(
            latitude = _latitudeDegDouble.value,
            longitude = _longitudeDegDouble.value,
            altitude = _absoluteAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function is used to move the [drone] in various directions.
     * */
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

    /**
     * This function is used to make the [drone] orbit on the current location.
     * */
    fun orbit() {
        val radius = 50F
        val velocity = 50F
        val behaviour = Action.OrbitYawBehavior.HOLD_FRONT_TO_CIRCLE_CENTER
        val lat = _latitudeDegDouble.value
        val lon = _longitudeDegDouble.value
        val alt = _absoluteAltitudeFloat.value.toDouble()

        viewModelScope.launch(Dispatchers.IO) {
            drone.action.doOrbit(radius, velocity, behaviour, lat, lon, alt).subscribe(
                {
                    // onNext - handle the result
                },
                { error ->
                    Log.e(TAG, "orbit: $error", error)
                }
            )
        }
    }

    /**
     * This function starts the observation of the [drone] telemetry data.
     * */
    private fun getTelemetryData() {
        Log.d(TAG, "getTelemetryData: ")
        getPositionData()
        getFlightMode()
        getArmedValue()
        getGpsData()
        getBatteryData()
        getSpeed()
    }

    /**
     * This function gets [drone] position data.
     * Altitude, Latitude, Longitude comes from here.
     * */
    private fun getPositionData() {
        Log.d(TAG, "getPositionData: ")
        viewModelScope.launch(Dispatchers.Default) {
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

    /**
     * This function gets [drone] flight mode.
     * */
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
                        Log.e(TAG, "Error in flight mode telemetry subscription $error", error)
                    })
        }
    }

    /**
     * This function gets [drone] armed/disarmed value.
     * */
    private fun getArmedValue() {
        Log.d(TAG, "getArmedValue: ")
        viewModelScope.launch(Dispatchers.IO) {
            droneRepository.drone.telemetry.armed.distinctUntilChanged()
                .subscribe(
                    { armed: Boolean ->
                        Log.d(TAG, "armed: $armed")
                    },
                    { error ->
                        Log.e(TAG, "Error in armed telemetry subscription $error", error)
                    }
                )
        }
    }

    /**
     * This function gets number of satellites the [drone] is picking up.
     * */
    private fun getGpsData() {
        Log.d(TAG, "getGpsData: ")
        viewModelScope.launch(Dispatchers.IO) {
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

    /**
     * This function gets the [drone] battery percentage to display on the UI.
     * */
    private fun getBatteryData() {
        Log.d(TAG, "getBatteryData: ")
        viewModelScope.launch(Dispatchers.IO) {
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

    /**
     * This function gets the speed of the [drone] to display on the UI.
     * */
    private fun getSpeed() {
        Log.d(TAG, "getSpeed: ")
        viewModelScope.launch(Dispatchers.IO) {
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