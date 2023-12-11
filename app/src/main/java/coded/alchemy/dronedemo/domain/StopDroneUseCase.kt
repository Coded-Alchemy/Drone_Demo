package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import kotlinx.coroutines.launch

/**
 * StopDroneUseCase.kt
 *
 * This class is responsible for providing [DroneRepository.drone] stop movement functionality.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class StopDroneUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName

    operator fun invoke() {
        Log.d(TAG, "invoke: ")
        scope.launch {
            droneRepository.drone.action.hold()
        }
    }
}