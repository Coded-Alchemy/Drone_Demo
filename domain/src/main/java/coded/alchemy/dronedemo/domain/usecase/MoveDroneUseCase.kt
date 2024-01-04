package coded.alchemy.dronedemo.domain.usecase

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import kotlinx.coroutines.launch

/**
 * MoveDroneUseCase.kt
 *
 * This class is responsible for providing [DroneRepository.drone] directional movement functionality.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class MoveDroneUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName

    operator fun invoke(latitude: Double, longitude: Double, altitude: Float, yawDegree: Float) {
        Log.d(TAG, "invoke: ")
        scope.launch {
            droneRepository.drone.action.gotoLocation(latitude, longitude, altitude, yawDegree)
                .subscribe(
                    {
                        // onNext - handle the result
                    },
                    { error ->
                        Log.e(TAG, "moveDrone: $error", error)
                    }
                )
        }
    }
}