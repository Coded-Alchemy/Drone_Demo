package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.action_server.ActionServer
import io.mavsdk.mission.Mission
import io.mavsdk.telemetry.TelemetryProto.FlightMode
import kotlinx.coroutines.launch

/**
 * StartMissionUseCase.kt
 *
 * This class is responsible for [DroneRepository.drone] missions.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class StartMissionUseCase(
    private val droneRepository: DroneRepository,
    private val getPositionDataUseCase: GetPositionDataUseCase
) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName

    private val drone = droneRepository.drone

    // lat long list
    private var missionItemList: MutableList<Mission.MissionItem> = mutableListOf()

    private val speedMs = 400f
    private val heightM = 60f

    private var missionItem = Mission.MissionItem(
        getPositionDataUseCase.latitudeDegDouble.value,
        getPositionDataUseCase.latitudeDegDouble.value,
        speedMs,
        heightM,
        true,
        Float.NaN,
        Float.NaN,
        Mission.MissionItem.CameraAction.NONE,
        Float.NaN,
        1.0,
        Float.NaN,
        Float.NaN,
        Float.NaN
    )


    operator fun invoke() {
        Log.d(TAG, "invoke: ")
        missionItemList.add(missionItem)
        var missionPlan = Mission.MissionPlan(missionItemList)

        scope.launch {
            drone.mission
                .setReturnToLaunchAfterMission(true)
                .andThen(drone.mission.uploadMission(missionPlan)
                    .doOnComplete { Log.d(TAG, "Mission uploaded") }
                    .doOnError { throwable: Throwable? ->
                        Log.e(TAG, "Error in mission upload subscription $throwable", throwable)
                    })
                .andThen(
                    drone.action.arm().andThen(drone.action.takeoff())
                        .onErrorComplete()
                )
                .andThen(drone.mission.startMission()
                    .doOnComplete { Log.d(TAG, "Mission started") }
                    .doOnError { throwable: Throwable? ->
                        Log.e(TAG, "Error in start mission subscription $throwable", throwable)
                    })
                .subscribe(
                    {}
                ) { throwable: Throwable? ->
//                    Log.e(TAG, "Error mission subscription $throwable", throwable)
                }
        }
    }

    private suspend fun setFlightModeOnServer() {
        // Set the desired flight mode (replace Action.FlightMode.MISSION with the desired flight mode)
//        drone.actionServer.setAllowableFlightModes(ActionServer.AllowableFlightModes(true))


//        drone.mission.


//        (ActionServer.FlightMode.MISSION)

    }
}