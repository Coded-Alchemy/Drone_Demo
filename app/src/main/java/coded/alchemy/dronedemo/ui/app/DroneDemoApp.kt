package coded.alchemy.dronedemo.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import coded.alchemy.dronedemo.di.appModule
import coded.alchemy.dronedemo.ui.connection.ConnectionScreen
import coded.alchemy.dronedemo.ui.navigation.DroneDemoNavHost
import org.koin.compose.KoinApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DroneDemoApp() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold { innerPadding ->
                DroneDemoNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
            }
        }
    }
}