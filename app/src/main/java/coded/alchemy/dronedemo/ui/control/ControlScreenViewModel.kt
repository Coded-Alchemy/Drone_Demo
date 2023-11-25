package coded.alchemy.dronedemo.ui.control

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.DroneRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ControlScreenViewModel(private val droneRepository: DroneRepository) : ViewModel() {
    private val TAG = this.javaClass.simpleName
    private val drone = droneRepository.drone

    fun takeoff() {
        Log.e(TAG, "takeoff: ")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                drone.action.arm().andThen(drone.action.takeoff()).subscribe()
            } catch (e: Exception) {
                Log.e(TAG, "takeoff: $e")
            }
        }
    }

    fun land() {
        Log.e(TAG, "land: ")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                drone.action.land().subscribe()
            } catch (e: Exception) {
                Log.e(TAG, "land: $e")
            }
        }
    }
}