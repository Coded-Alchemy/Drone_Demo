package coded.alchemy.dronedemo.ui.connection

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.ui.navigation.Route
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConnectionScreen(navController: NavHostController, viewModel: ScreenViewModel = koinViewModel()) {
    Column(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConnectButton(navController, viewModel)
    }
}

@Composable
fun ConnectButton(navController: NavHostController, viewModel: ScreenViewModel) {

    val canNav = rememberSaveable { mutableStateOf(false) }

    ElevatedButton(onClick = {
        viewModel.connect()
        if (viewModel.canNavigate) {
            navController.navigate(Route.CONTROL_SCREEN)
        }
    }) {
        Text(stringResource(id = R.string.btn_connect))
    }
}

