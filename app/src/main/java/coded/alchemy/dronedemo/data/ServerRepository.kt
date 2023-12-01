package coded.alchemy.dronedemo.data

import io.mavsdk.mavsdkserver.MavsdkServer

/**
 * ServerRepository.kt
 *
 * This Singleton class is the single source of truth for the [MavsdkServer] that
 * runs on the Android device.
 * To improve the usage of this class run it from a Service instead of its current implementation.
 * @see <a href="https://en.wikipedia.org/wiki/Single_source_of_truth">Single source of truth</a>.
 * @property [mavServer] the [MavsdkServer] this app runs to communicate with drones.
 * @property [host] the IP Address of the Android device running this application.
 * @author Taji Abdullah
 * TODO: Run this from a background service.
 * */
class ServerRepository {

    private val mavServer: MavsdkServer = MavsdkServer()
    val host = "localhost"

    /**
     * This function provides access to [mavServer].
     * */
    fun mavServer() = mavServer

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