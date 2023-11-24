package coded.alchemy.dronedemo.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coded.alchemy.dronedemo.di.appModule
import coded.alchemy.dronedemo.ui.screen.DemoScreen
import org.koin.compose.KoinApplication

@Composable
fun DroneDemoApp() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            DemoScreen()
        }
    }
}