package coded.alchemy.dronedemo.ui.connection

sealed class ConnectionState {
    data object Default : ConnectionState()
    data object Connecting : ConnectionState()
    data object Connected : ConnectionState()
    data class Error(val message: String) : ConnectionState()

}
