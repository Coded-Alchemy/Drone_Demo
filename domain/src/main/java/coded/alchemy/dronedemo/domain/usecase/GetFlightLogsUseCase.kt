package coded.alchemy.dronedemo.domain.usecase

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.log_files.LogFiles
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * GetFlightLogsUseCase.kt
 *
 * This class is responsible for getting the [DroneRepository.drone] flight logs.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class GetFlightLogsUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName
    private val _logEntries = MutableStateFlow<List<LogFiles.Entry>>(emptyList())
    val logEntries: StateFlow<List<LogFiles.Entry>> = _logEntries

    operator fun invoke() {
        scope.launch {
            droneRepository.drone.logFiles.entries.subscribe(
                { log ->
                    _logEntries.value = log
                },
                { error ->
                    Log.e(TAG, "Error in Log Entry subscription $error", error)
                }
            )
        }
    }
}