package coded.alchemy.dronedemo.ui.control

import android.util.Log
import androidx.lifecycle.viewModelScope
import coded.alchemy.dronedemo.data.SpeechRecognizer
import coded.alchemy.dronedemo.domain.DroneLandUseCase
import coded.alchemy.dronedemo.domain.DroneOrbitUseCase
import coded.alchemy.dronedemo.domain.DroneTakeOffUseCase
import coded.alchemy.dronedemo.domain.GetArmedValueUseCase
import coded.alchemy.dronedemo.domain.GetBatteryPercentageUseCase
import coded.alchemy.dronedemo.domain.GetDroneSpeedUseCase
import coded.alchemy.dronedemo.domain.GetFlightModeUseCase
import coded.alchemy.dronedemo.domain.GetGpsDataUseCase
import coded.alchemy.dronedemo.domain.GetPositionDataUseCase
import coded.alchemy.dronedemo.domain.MoveDroneUseCase
import coded.alchemy.dronedemo.domain.StopDroneUseCase
import coded.alchemy.dronedemo.ui.app.DroneDemoViewModel
import coded.alchemy.dronedemo.util.VoiceCommand
import io.mavsdk.action.Action
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


/**
 * ControlScreenViewModel.kt
 *
 * This class contains the drone connection logic.
 * @param getBatteryPercentageUseCase [GetBatteryPercentageUseCase] provides drone battery stats.
 * @property relativeAltitudeFloat [StateFlow] [Float] that observes altitude used.
 * @property absoluteAltitudeFloat [StateFlow] [Float] that observes
 * @property latitudeDegDouble [StateFlow] [Double] that observes the [drone] latitude.
 * @property longitudeDegDouble [StateFlow] [Double] that observes
 * @property flightMode [StateFlow] [String] that observes
 * @property satelliteCount [StateFlow] [Int] that observes
 * @property batteryRemaining [StateFlow] [Float] that observes
 * @property speed [StateFlow] [Float] that observes
 * @author Taji Abdullah
 * */
class ControlScreenViewModel(
    private val droneTakeOffUseCase: DroneTakeOffUseCase,
    private val droneLandUseCase: DroneLandUseCase,
    private val droneOrbitUseCase: DroneOrbitUseCase,
    private val moveDroneUseCase: MoveDroneUseCase,
    private val getPositionDataUseCase: GetPositionDataUseCase,
    private val getFlightModeUseCase: GetFlightModeUseCase,
    private val getArmedValueUseCase: GetArmedValueUseCase,
    private val getGpsDataUseCase: GetGpsDataUseCase,
    private val getDroneSpeedUseCase: GetDroneSpeedUseCase,
    private val getBatteryPercentageUseCase: GetBatteryPercentageUseCase,
    private val stopDroneUseCase: StopDroneUseCase
) : DroneDemoViewModel() {
    private val TAG = this.javaClass.simpleName
    val relativeAltitudeFloat: StateFlow<Float> = getPositionDataUseCase.relativeAltitudeFloat
    val absoluteAltitudeFloat: StateFlow<Float> = getPositionDataUseCase.absoluteAltitudeFloat
    val latitudeDegDouble: StateFlow<Double> = getPositionDataUseCase.latitudeDegDouble
    val longitudeDegDouble: StateFlow<Double> = getPositionDataUseCase.longitudeDegDouble
    val flightMode: StateFlow<String> = getFlightModeUseCase.flightMode
    val satelliteCount: StateFlow<Int> = getGpsDataUseCase.satelliteCount
    val batteryRemaining: StateFlow<Float> = getBatteryPercentageUseCase.batteryRemaining
    val speed: StateFlow<Float> = getDroneSpeedUseCase.speed
    private val _commandReceived = MutableSharedFlow<String>()
    private val commandReceived: SharedFlow<String> get() = _commandReceived
    private val vocalCommand: StateFlow<String> = SpeechRecognizer.resultText

    /**
     * This function gets called when this class is instantiated to start collecting
     * the telemetry data.
     * */
    init {
        getPositionDataUseCase()
        getFlightModeUseCase()
        getArmedValueUseCase()
        getGpsDataUseCase()
        getBatteryPercentageUseCase()
        getDroneSpeedUseCase()

        viewModelScope.launch {
            vocalCommand.collect { command ->
                _commandReceived.emit(command)
            }
        }

        viewModelScope.launch {
            commandReceived.collect { command ->
                handleCommand(command)
            }
        }
    }

    /**
     * Ensure nothing lives outside of the scope of this [DroneDemoViewModel] lifecycle.
     * */
    override fun onCleared() {
        droneTakeOffUseCase.cancel()
        droneLandUseCase.cancel()
        droneOrbitUseCase.cancel()
        moveDroneUseCase.cancel()
        getPositionDataUseCase.cancel()
        getFlightModeUseCase.cancel()
        getArmedValueUseCase.cancel()
        getGpsDataUseCase.cancel()
        getDroneSpeedUseCase.cancel()
        getBatteryPercentageUseCase.cancel()
        stopDroneUseCase.cancel()
        super.onCleared()
    }

    /**
     * This function makes the [drone] start to fly.
     * */
    fun takeoff() {
        Log.d(TAG, "takeoff: ")
        droneTakeOffUseCase()
    }

    /**
     * This function makes the [drone] land.
     * */
    fun land() {
        Log.d(TAG, "land: ")
        droneLandUseCase()
    }

    /**
     * This function calls [moveDrone] to move the [drone] up/ascend.
     * */
    fun moveUp() {
        Log.d(TAG, "moveUp: ")
        val newAltitude = absoluteAltitudeFloat.value + 10.0F
        moveDroneUseCase(
            latitude = latitudeDegDouble.value,
            longitude = longitudeDegDouble.value,
            altitude = newAltitude,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] down/descend.
     * */
    fun moveDown() {
        Log.d(TAG, "moveDown: ")
        val newAltitude = absoluteAltitudeFloat.value - 10.0F
        moveDroneUseCase(
            latitude = latitudeDegDouble.value,
            longitude = longitudeDegDouble.value,
            altitude = newAltitude,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] right.
     * */
    fun moveRight() {
        Log.d(TAG, "moveRight: ")
        val newLongitude = longitudeDegDouble.value + 1
        moveDroneUseCase(
            latitude = latitudeDegDouble.value,
            longitude = newLongitude,
            altitude = absoluteAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] left.
     * */
    fun moveLeft() {
        Log.d(TAG, "moveLeft: ")
        val newLongitude = longitudeDegDouble.value - 1
        moveDroneUseCase(
            latitude = latitudeDegDouble.value,
            longitude = newLongitude,
            altitude = absoluteAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] forward.
     * */
    fun moveForward() {
        Log.d(TAG, "moveForward: ")
        val newLatitude = latitudeDegDouble.value + 1
        moveDroneUseCase(
            latitude = newLatitude,
            longitude = longitudeDegDouble.value,
            altitude = absoluteAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function calls [moveDrone] to move the [drone] backwards.
     * */
    fun moveBackward() {
        Log.d(TAG, "moveBackward: ")
        val newLatitude = latitudeDegDouble.value - 1
        moveDroneUseCase(
            latitude = newLatitude,
            longitude = longitudeDegDouble.value,
            altitude = absoluteAltitudeFloat.value,
            yawDegree = 0F
        )
    }

    /**
     * This function is used to stop the [drone] from moving.
     * */
    fun stop() {
        Log.d(TAG, "stop: ")
        stopDroneUseCase()
    }

    /**
     * This function is used to make the [drone] orbit on the current location.
     * */
    fun orbit() {
        Log.d(TAG, "orbit: ")
        droneOrbitUseCase(
            radius = 25F,
            velocity = 50F,
            behaviour = Action.OrbitYawBehavior.HOLD_FRONT_TO_CIRCLE_CENTER,
            latitude = latitudeDegDouble.value,
            longitude = longitudeDegDouble.value,
            altitude = absoluteAltitudeFloat.value.toDouble()
        )
    }

    /**
     * This function starts listening for voice commands.
     * */
    fun listenForCommand() {
        Log.d(TAG, "listenForCommand: ")
        val speechRecognizer = SpeechRecognizer
        speechRecognizer.startListening()
    }

    /**
     * This function performs a function based on the given voice command.
     * */
    private fun handleCommand(command: String) {
        Log.d(TAG, "handleCommand: $command")
        viewModelScope.launch {
            when {
                VoiceCommand.TakeoffCommandSet.commands.any {  command.contains(it) } -> takeoff()
                VoiceCommand.LandCommandSet.commands.any { command.contains(it) } -> land()
                VoiceCommand.MoveUpCommandSet.commands.any { command.contains(it) } -> moveUp()
                VoiceCommand.MoveDownCommandSet.commands.any { command.contains(it) } -> moveDown()
                VoiceCommand.MoveRightCommandSet.commands.any { command.contains(it) } -> moveRight()
                VoiceCommand.MoveLeftCommandSet.commands.any { command.contains(it) } -> moveLeft()
                VoiceCommand.MoveForwardCommandSet.commands.any { command.contains(it) } -> moveForward()
                VoiceCommand.MoveBackwardCommandSet.commands.any { command.contains(it) } -> moveBackward()
                VoiceCommand.StopCommandSet.commands.any { command.contains(it) } -> stop()
                VoiceCommand.OrbitCommandSet.commands.any { command.contains(it) } -> orbit()
            }
        }
    }
}