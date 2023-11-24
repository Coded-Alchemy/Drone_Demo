package coded.alchemy.dronedemo.ui.screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import io.mavsdk.System
import io.mavsdk.telemetry.Telemetry
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScreenViewModel(private val droneRepository: DroneRepository, private val serverRepository: ServerRepository) : ViewModel() {

    private val TAG = this.javaClass.simpleName
//    private var drone = droneRepository.drone
    private val server = serverRepository.mavServer()
    private val disposables = CompositeDisposable()

    fun connect() {
        Log.d(TAG, "connect: ")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val port = server.run()
                droneRepository.drone = System(serverRepository.host, port)

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
            } catch (e: Exception) {
                TODO("Not yet implemented")
            }
        }
    }
}