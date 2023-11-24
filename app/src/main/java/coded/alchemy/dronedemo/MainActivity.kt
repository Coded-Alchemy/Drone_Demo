package coded.alchemy.dronedemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import coded.alchemy.dronedemo.ui.app.DroneDemoApp
import coded.alchemy.dronedemo.ui.screen.DemoScreen
import coded.alchemy.dronedemo.ui.theme.DroneDemoTheme
import io.mavsdk.MavsdkEventQueue
import io.mavsdk.System
import io.mavsdk.mavsdkserver.MavsdkServer
import io.mavsdk.telemetry.Telemetry
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val TAG = this.javaClass.simpleName

    private lateinit var drone: System

//    private val mavServer: MavsdkServer = MavsdkServer()

//    private val disposables: List<Disposable> = ArrayList()
    private val disposables = CompositeDisposable()


    var MAVSDK_SERVER_PORT = 14540



    val QGC_PORT = 14550

    val DOCKER_IP = "172.17.0.2"

    val EMULATOR_LOOPBACK_IP = "10.0.2.2"

    val LAPTOP_IP = "192.168.0.24"

    val PHONE_IP = "192.168.0.63"

    val em = "10.0.2.16"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: ")
        setContent {
            DroneDemoTheme {
                DroneDemoApp()
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        Log.d(TAG, "onResume: ")
//        runServer()
//    }

    override fun onPause() {
        super.onPause()
    }

//    override fun onStop() {
//        Log.d(TAG, "onResume: ")
//        mavServer.stop()
//        super.onStop()
//    }

//    override fun onDestroy() {
//        mavServer.destroy()
//        super.onDestroy()
//    }

//    private fun runServer() {
//        Log.d(TAG, "runServer: ")
//        val SYSTEM_ADDRESS = "udp://$PHONE_IP:$MAVSDK_SERVER_PORT"
//
//        MavsdkEventQueue.executor().execute {
//            Log.d(TAG, "execute")
//
//            GlobalScope.launch(Dispatchers.IO) {
//                MAVSDK_SERVER_PORT = mavServer.run()
//
//                drone = System("localhost", MAVSDK_SERVER_PORT)
//            }
//        }
//    }


    private fun connectDrone() {
        Log.d(TAG, "connectDrone: connect drone")
        drone = System("localhost", MAVSDK_SERVER_PORT)

        disposables.add(
            drone.telemetry.flightMode.distinctUntilChanged()
                .subscribe { flightMode: Telemetry.FlightMode ->
                    Log.d(TAG, "flight mode: $flightMode")
                }
        )
        disposables.add(
            drone.telemetry.armed.distinctUntilChanged()
                .subscribe { armed: Boolean ->
                    Log.d(TAG, "armed: $armed")
                }
        )
        disposables.add(
            drone.telemetry.position
                .subscribe { position: Telemetry.Position ->
//                        val latLng =
//                            LatLng(position.latitudeDeg, position.longitudeDeg)
//                        viewModel.currentPositionLiveData.postValue(latLng)
                })
    }

    private fun armDrone() {
        Log.d(TAG, "armDrone: arm drone")
        drone.action.arm().subscribe()
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DroneDemoTheme {

    }
}