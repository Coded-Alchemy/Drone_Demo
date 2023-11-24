package coded.alchemy.dronedemo.data

import io.mavsdk.mavsdkserver.MavsdkServer

class ServerRepository {

    private val mavServer: MavsdkServer = MavsdkServer()
    val host = "localhost"

    fun mavServer() = mavServer

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: DroneRepository? = null

        fun getInstance() =
            instance ?: synchronized(this) {
                instance ?: DroneRepository().also {  instance = it }
            }
    }
}