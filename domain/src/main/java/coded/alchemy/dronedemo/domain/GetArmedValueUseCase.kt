package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import kotlinx.coroutines.launch

/**
 * GetArmedValueUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] armed value.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetArmedValueUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName

    operator fun invoke() {
        scope.launch {
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
}