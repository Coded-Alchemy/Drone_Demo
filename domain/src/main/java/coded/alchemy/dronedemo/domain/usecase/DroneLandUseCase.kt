package coded.alchemy.dronedemo.domain.usecase

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import kotlinx.coroutines.launch

/**
 * DroneLandUseCase.kt
 *
 * This class is responsible for providing the [DroneRepository.drone] Landing functionality.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class DroneLandUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName

    operator fun invoke() {
        Log.d(TAG, "invoke: ")
        scope.launch {
            droneRepository.drone.action.land().subscribe(
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