package coded.alchemy.dronedemo.domain

import coded.alchemy.dronedemo.data.DroneRepository

/**
 * StartMissionUseCase.kt
 *
 * This class is responsible for [DroneRepository.drone] missions.
 * @param droneRepository [DroneRepository] gives access to [DroneRepository.drone].
 * @author Taji Abdullah
 * */
class StartMissionUseCase(private val droneRepository: DroneRepository) : DroneDemoUseCase() {
}