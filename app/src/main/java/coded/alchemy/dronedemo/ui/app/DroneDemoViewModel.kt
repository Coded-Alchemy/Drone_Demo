package coded.alchemy.dronedemo.ui.app

import androidx.lifecycle.ViewModel
import coded.alchemy.dronedemo.network.NetworkMonitor
import kotlinx.coroutines.flow.StateFlow

/**
 * DroneDemoViewModel.kt
 *
 * This class is the base [ViewModel] for the application.
 * @author Taji Abdullah
 * */
open class DroneDemoViewModel : ViewModel() {
    private val TAG = this.javaClass.simpleName

    val isNetworkConnected: StateFlow<Boolean> = NetworkMonitor.isConnected
}