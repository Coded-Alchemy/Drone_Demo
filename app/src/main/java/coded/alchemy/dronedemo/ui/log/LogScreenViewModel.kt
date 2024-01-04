package coded.alchemy.dronedemo.ui.log

import coded.alchemy.dronedemo.domain.usecase.GetFlightLogsUseCase
import coded.alchemy.dronedemo.ui.app.DroneDemoViewModel
import io.mavsdk.log_files.LogFiles
import kotlinx.coroutines.flow.StateFlow

class LogScreenViewModel(getFlightLogsUseCase: GetFlightLogsUseCase): DroneDemoViewModel() {
    val logEntries: StateFlow<List<LogFiles.Entry>> = getFlightLogsUseCase.logEntries

    init {
        getFlightLogsUseCase()
    }
}