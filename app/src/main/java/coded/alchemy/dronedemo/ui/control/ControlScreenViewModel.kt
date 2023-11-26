package coded.alchemy.dronedemo.ui.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ControlScreenViewModel(private val droneRepository: DroneRepository) : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private val drone = droneRepository.drone
    private val disposables = CompositeDisposable()

    private val _relativeAltitudeFloat = MutableStateFlow(Float.MIN_VALUE)
    val relativeAltitudeFloat: StateFlow<Float> = _relativeAltitudeFloat

    private val _latitudeDegDouble = MutableStateFlow(Double.MIN_VALUE)
    val latitudeDegDouble: StateFlow<Double> = _latitudeDegDouble

    private val _longitudeDegDouble = MutableStateFlow(Double.MIN_VALUE)
    val longitudeDegDouble: StateFlow<Double> = _longitudeDegDouble

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
        Log.e(TAG, "land: ")
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

    private fun getTelemetryData() {
        Log.d(TAG, "getTelemetryData: ")
        getPositionData()

        viewModelScope.launch(Dispatchers.IO) {
            disposables.add(
                droneRepository.drone.telemetry.flightMode.distinctUntilChanged()
                    .subscribe(
                        { flightMode: Telemetry.FlightMode ->
                            Log.d(TAG, "flight mode: $flightMode")
                        },
                        { error ->
                            Log.e(TAG, "Error in flight mode telemetry subscription", error)
                        })
            )

            disposables.add(
                droneRepository.drone.telemetry.armed.distinctUntilChanged()
                    .subscribe(
                        { armed: Boolean ->
                            Log.d(TAG, "armed: $armed")
                        },
                        { error ->
                            Log.e(TAG, "Error in armed telemetry subscription", error)
                        }
                    )
            )


        }
    }

    private fun getPositionData() {
        viewModelScope.launch(Dispatchers.IO) {
            droneRepository.drone.telemetry.position
                .subscribe(
                    { position: Telemetry.Position ->
                        _relativeAltitudeFloat.value = position.relativeAltitudeM
                        _latitudeDegDouble.value = position.latitudeDeg
                        _longitudeDegDouble.value = position.longitudeDeg
                    },
                    { error ->
                        Log.e(TAG, "Error in position telemetry subscription", error)
                    })
        }
    }
}