package coded.alchemy.dronedemo.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

open class DroneDemoUseCase {
    protected val scope = CoroutineScope(Dispatchers.IO)
    fun cancel() = scope.cancel()
}