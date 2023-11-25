package coded.alchemy.dronedemo.ui.connection

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import io.mavsdk.System
import io.mavsdk.telemetry.Telemetry
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ScreenViewModel(private val droneRepository: DroneRepository, private val serverRepository: ServerRepository) : ViewModel() {

    private val TAG = this.javaClass.simpleName
    private val disposables = CompositeDisposable()

    var canNavigate = false

    fun connect() {
        Log.d(TAG, "connect: ")
            try {
                viewModelScope.launch(Dispatchers.IO) {

                    val port = serverRepository.mavServer().run()
                    droneRepository.drone = System(serverRepository.host, port)
                    canNavigate = true

                    disposables.add(
                        droneRepository.drone.telemetry.flightMode.distinctUntilChanged()
                            .subscribe { flightMode: Telemetry.FlightMode ->
                                Log.d(TAG, "flight mode: $flightMode")
                            }
                    )
                    disposables.add(
                        droneRepository.drone.telemetry.armed.distinctUntilChanged()
                            .subscribe { armed: Boolean ->
                                Log.d(TAG, "armed: $armed")
                            }
                    )
                    disposables.add(
                        droneRepository.drone.telemetry.position
                            .subscribe { position: Telemetry.Position ->
//                        val latLng =
//                            LatLng(position.latitudeDeg, position.longitudeDeg)
//                        viewModel.currentPositionLiveData.postValue(latLng)
                            })


                }
            } catch (exception: Exception) {
                Log.e(TAG, exception.toString() )
            }
    }
}