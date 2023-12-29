package coded.alchemy.dronedemo.ui.mission

import android.util.Log
import coded.alchemy.dronedemo.domain.StartMissionUseCase
import coded.alchemy.dronedemo.ui.app.DroneDemoViewModel

class MissionScreenViewModel(private val startMissionUseCase: StartMissionUseCase): DroneDemoViewModel() {
    private val TAG = this.javaClass.simpleName

    /**
     * Ensure nothing lives outside of the scope of this [MissionScreenViewModel] lifecycle.
     * */
    override fun onCleared() {
        startMissionUseCase.cancel()
    }

    fun startMission() {
        Log.d(TAG, "startMission: ")
        startMissionUseCase()
    }
}