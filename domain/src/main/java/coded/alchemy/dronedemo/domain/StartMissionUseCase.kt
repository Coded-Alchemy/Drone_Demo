package coded.alchemy.dronedemo.domain

import android.util.Log
import coded.alchemy.dronedemo.data.DroneRepository
import io.mavsdk.mission.Mission
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch


/**
 * StartMissionUseCase.kt
 *
 * This class is responsible for [DroneRepository.drone] missions.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class StartMissionUseCase(
    private val droneRepository: DroneRepository
) : DroneDemoUseCase() {
    private val TAG = this.javaClass.simpleName

    private val drone = droneRepository.drone

    // lat long list
    private var missionItemList: MutableList<Mission.MissionItem> = mutableListOf()

    operator fun invoke() {
        Log.d(TAG, "invoke: ")
        missionItemList.add(generateMissionItem(47.39803986, 8.54557254))
        missionItemList.add(generateMissionItem(47.39853996, 8.54567354))
        missionItemList.add(generateMissionItem(47.39884006, 8.54607554))

        var missionPlan = Mission.MissionPlan(missionItemList)

        scope.launch {
            drone.mission
                .setReturnToLaunchAfterMission(true)
                .andThen(drone.mission.uploadMission(missionPlan)
                    .doOnComplete { Log.d(TAG, "Mission uploaded") }
                    .doOnError { throwable: Throwable? ->
                        Log.e(TAG, "Error in mission upload subscription $throwable", throwable)
                    })
                .andThen(drone.mission.downloadMission()
                    .doOnSubscribe { disposable: Disposable? ->
                        Log.d(TAG, "Downloading mission")
                    }
                    .doAfterSuccess { disposable: Mission.MissionPlan? ->
                        Log.d(TAG, "Mission downloaded")
                    }
                    .doOnError { throwable: Throwable? ->
                        Log.e(TAG, "Error in mission download subscription $throwable", throwable)
                    }
                ).toCompletable()
                .andThen(drone.action.arm().onErrorComplete())
                .andThen(drone.mission.startMission()
                    .doOnComplete { Log.d(TAG, "Mission started") }
                    .doOnError { throwable: Throwable? ->
                        Log.e(TAG, "Error in start mission subscription ${throwable?.cause}", throwable)
                    })
                .subscribe(
                    {}
                ) { throwable: Throwable? ->
//                    Log.e(TAG, "Error mission subscription $throwable", throwable)
                }
        }
    }

    fun generateMissionItem(latitudeDeg: Double, longitudeDeg: Double): Mission.MissionItem {
        return Mission.MissionItem(
            latitudeDeg,
            longitudeDeg,
            10f,
            50f,
            true,
            Float.NaN,
            Float.NaN,
            Mission.MissionItem.CameraAction.NONE,
            5F,
            Double.NaN,
            Float.NaN,
            Float.NaN,
            Float.NaN
        )
    }
}