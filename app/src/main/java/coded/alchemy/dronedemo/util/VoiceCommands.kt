package coded.alchemy.dronedemo.util

sealed class VoiceCommand(val commands: Set<String>) {
    data object TakeoffCommandSet : VoiceCommand(setOf("take off", "lift off", "launch", "liftoff"))
    data object LandCommandSet : VoiceCommand(setOf("land", "touch down", "touchdown"))
    data object MoveUpCommandSet : VoiceCommand(setOf("move up", "fly up"))
    data object MoveDownCommandSet : VoiceCommand(setOf("move down", "fly down"))
    data object StopCommandSet : VoiceCommand(setOf("stop", "hold"))
}
