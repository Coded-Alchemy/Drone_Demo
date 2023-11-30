package coded.alchemy.dronedemo.network

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * NetworkMonitor.kt
 *
 * This object provides network monitoring functionality and behaves like a singleton.
 *
 * @author Taji Abdullah
 * */
object NetworkMonitor {
    private val TAG = this.javaClass.simpleName

    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> get() = _isConnected

    val networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    val networkCallback = object : ConnectivityManager.NetworkCallback() {

        /**
         * Network is available for use.
         * @param network [Network]
         * */
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            Log.i(TAG, "networkCallback: onAvailable $network")
            _isConnected.value = true
        }

        /**
         * Network capabilities have changed for the network.
         * @param network [Network]
         * @param networkCapabilities [NetworkCapabilities]
         * */
        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            Log.i(TAG, "networkCallback: onCapabilitiesChanged $network")
            val unmetered = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)
        }

        /**
         * lost network connection.
         * @param network [Network]
         * */
        override fun onLost(network: Network) {
            super.onLost(network)
            Log.w(TAG, "networkCallback: onLost $network")
            _isConnected.value = false
        }
    }
}