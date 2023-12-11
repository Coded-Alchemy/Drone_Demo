package coded.alchemy.dronedemo.util

/**
 * VoiceCommand.kt
 *
 * Sealed class that contains data objects of permissible voice commands.
 * @author Taji Abdullah
 * */
sealed class VoiceCommand(val commands: Set<String>) {
    data object TakeoffCommandSet : VoiceCommand(setOf("take off", "lift off", "launch", "liftoff"))
    data object LandCommandSet : VoiceCommand(setOf("land", "touch down", "touchdown"))
    data object MoveUpCommandSet : VoiceCommand(setOf("move up", "fly up"))
    data object MoveDownCommandSet : VoiceCommand(setOf("move down", "fly down"))
    data object MoveLeftCommandSet : VoiceCommand(setOf("move left", "fly left"))
    data object MoveRightCommandSet : VoiceCommand(setOf("move right", "fly right"))
    data object MoveForwardCommandSet : VoiceCommand(setOf("move forward", "fly forward"))
    data object MoveBackwardCommandSet : VoiceCommand(setOf("move backward", "fly backward", "move back", "fly back"))
    data object OrbitCommandSet : VoiceCommand(setOf("orbit", "circle"))
    data object StopCommandSet : VoiceCommand(setOf("stop", "hold", "hold position"))
}
