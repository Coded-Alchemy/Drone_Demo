package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.action.Action
import kotlinx.coroutines.launch

/**
 * DroneOrbitUseCase.kt
 *
 * This class is responsible for providing the [DroneRepository.drone] orbit functionality.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class DroneOrbitUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName

    operator fun invoke(
        radius: Float,
        velocity: Float,
        behaviour: Action.OrbitYawBehavior,
        latitude: Double,
        longitude: Double,
        altitude: Double
    ) {
        scope.launch {
            droneRepository.drone.action.doOrbit(
                radius,
                velocity,
                behaviour,
                latitude,
                longitude,
                altitude
            ).subscribe(
                {
                    // onNext - handle the result
                },
                { error ->
                    Log.e(TAG, "orbit: $error", error)
                }
            )
        }
    }
}