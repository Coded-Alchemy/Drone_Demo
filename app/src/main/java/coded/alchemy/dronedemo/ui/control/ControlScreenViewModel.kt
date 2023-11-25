package coded.alchemy.dronedemo.ui.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.telemetry.Telemetry
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ControlScreenViewModel(private val droneRepository: DroneRepository) : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private val drone = droneRepository.drone
    private val disposables = CompositeDisposable()

    init {
        getTelemetryData()
    }

    private fun getTelemetryData() {
        Log.d(TAG, "getTelemetryData: ")
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

            disposables.add(
                droneRepository.drone.telemetry.position
                    .subscribe(
                        { position: Telemetry.Position ->
                            //                        val latLng =
                            //                            LatLng(position.latitudeDeg, position.longitudeDeg)
                            //                        viewModel.currentPositionLiveData.postValue(latLng)
                        },
                        { error ->
                            Log.e(TAG, "Error in position telemetry subscription", error)
                        })
                    )
        }
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
}