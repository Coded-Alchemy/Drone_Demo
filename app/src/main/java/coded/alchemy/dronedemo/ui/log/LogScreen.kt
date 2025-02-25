package coded.alchemy.dronedemo.ui.log

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coded.alchemy.dronedemo.R
import coded.alchemy.dronedemo.ui.mission.MissionScreen
import io.mavsdk.log_files.LogFiles
import org.koin.androidx.compose.koinViewModel

/**
 * LogScreen.kt
 *
 * This [Composable] function declares the [MissionScreen].
 * This screen provides the UI to view flight logs.
 * @author Taji Abdullah
 * */
@Composable
fun LogScreen(modifier: Modifier = Modifier, viewModel: LogScreenViewModel = koinViewModel(),
) {
    val logEntries by viewModel.logEntries.collectAsState(emptyList())

    Surface(
        modifier = Modifier.fillMaxSize()
            .padding(all = dimensionResource(id = R.dimen.dp_6)),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn {
            items(logEntries) { logEntry ->
                LogListItem(logEntry/*, viewModel::onLogItemClick*/)
            }
        }
    }
}

@Composable
fun LogListItem(
    logEntry: LogFiles.Entry,
//    onLogItemClick: (LogFiles.Entry) -> Unit
) {
    Card(
        modifier =
        Modifier
            .padding(all = dimensionResource(id = R.dimen.default_padding))
            .fillMaxWidth(),
//            .clickable { onLogItemClick(logEntry) }, // Use clickable without argument
        elevation = CardDefaults.cardElevation(
            defaultElevation = dimensionResource(id = R.dimen.card_elevation)
        )
    ) {
        Column(modifier = Modifier.padding(all = dimensionResource(id = R.dimen.default_padding))) {
            Text(
                logEntry.toString(),
                fontSize = 25.sp,
                color = Color.Black,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.default_padding))
            )
        }
    }
}