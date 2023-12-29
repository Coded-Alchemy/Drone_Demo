package coded.alchemy.dronedemo.data

import io.mavsdk.System

/**
 * DroneRepository.kt
 *
 * This Singleton class is the single source of truth for the [System] that
 * represents the drone this application connects to.
 * To add multiple drone control or swarm functionality add more [System] objects.
 * @see <a href="https://en.wikipedia.org/wiki/Single_source_of_truth">Single source of truth</a>.
 * @property [drone] the [System] this app will connect to.
 * @author Taji Abdullah
 * */
class DroneRepository() {

    lateinit var drone: System

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: DroneRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: DroneRepository().also { instance = it }
            }
    }
}