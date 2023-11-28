package coded.alchemy.dronedemo

import android.app.Application
import androidx.compose.runtime.Composable
import coded.alchemy.dronedemo.di.appModule
import org.koin.core.context.GlobalContext.startKoin

/**
 * DroneDemoApplication.kt
 *
 * [Application] base class used to start Koin dependency injection.
 * @author Taji Abdullah
 * */
class DroneDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(appModule)
        }
    }
}