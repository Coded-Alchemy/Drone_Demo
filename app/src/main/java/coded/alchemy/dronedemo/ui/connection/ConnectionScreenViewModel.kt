package coded.alchemy.dronedemo.ui.connection

import android.util.Log
import coded.alchemy.dronedemo.domain.usecase.ConnectToDroneUseCase
import coded.alchemy.dronedemo.domain.usecase.GetConnectionStateUseCase
import coded.alchemy.dronedemo.ui.app.DroneDemoViewModel
import kotlinx.coroutines.flow.StateFlow

/**
 * ConnectionScreenViewModel.kt
 *
 * This class contains the drone connection logic.
 * @param connectToDroneUseCase [ConnectToDroneUseCase] provides drone connection functionality.
 * @property isDroneConnected [Boolean] observable by [StateFlow] to determine if a drone is connected.
 * @author Taji Abdullah
 * */
class ConnectionScreenViewModel(
    private val connectToDroneUseCase: ConnectToDroneUseCase
) : DroneDemoViewModel() {
    private val TAG = this.javaClass.simpleName
    val isDroneConnecting: StateFlow<Boolean> get() = connectToDroneUseCase.isDroneConnecting

    val isDroneConnected: StateFlow<Boolean?> get() = connectToDroneUseCase.isDroneConnected

    override fun onCleared() {
        connectToDroneUseCase.cancel()
        super.onCleared()
    }

    fun connect() {
        Log.d(TAG, "connect: ")
        connectToDroneUseCase()
    }
}

