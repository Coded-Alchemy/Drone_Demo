package coded.alchemy.dronedemo.ui.mission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import kotlinx.coroutines.channels.Channel
import org.koin.androidx.compose.koinViewModel

/**
 * MissionScreen.kt
 *
 * This [Composable] function declares the [MissionScreen].
 * This screen provides the UI to create mission plans.
 * @author Taji Abdullah
 * */
@Composable
fun MissionScreen(
    modifier: Modifier = Modifier,
    setFabOnClick: (() -> Unit) -> Unit,
    snackBarMessageChannel: Channel<String>,
    viewModel: MissionScreenViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        setFabOnClick { viewModel.startMission() }
    }

}