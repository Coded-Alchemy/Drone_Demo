package coded.alchemy.dronedemo.data

import io.mavsdk.System


class DroneRepository {

    lateinit var drone: System

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