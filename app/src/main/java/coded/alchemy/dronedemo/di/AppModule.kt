package coded.alchemy.dronedemo.di


import coded.alchemy.dronedemo.domain.ConnectToDroneUseCase
import coded.alchemy.dronedemo.domain.DroneLandUseCase
import coded.alchemy.dronedemo.domain.DroneOrbitUseCase
import coded.alchemy.dronedemo.domain.DroneTakeOffUseCase
import coded.alchemy.dronedemo.domain.GetArmedValueUseCase
import coded.alchemy.dronedemo.domain.GetBatteryPercentageUseCase
import coded.alchemy.dronedemo.domain.GetDroneSpeedUseCase
import coded.alchemy.dronedemo.domain.GetFlightLogsUseCase
import coded.alchemy.dronedemo.domain.GetFlightModeUseCase
import coded.alchemy.dronedemo.domain.GetGpsDataUseCase
import coded.alchemy.dronedemo.domain.GetPositionDataUseCase
import coded.alchemy.dronedemo.domain.MoveDroneUseCase
import coded.alchemy.dronedemo.ui.connection.ConnectionScreenViewModel
import coded.alchemy.dronedemo.ui.control.ControlScreenViewModel
import coded.alchemy.dronedemo.ui.log.LogScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

/**
 * AppModule.kt
 *
 * This file provides the Koin dependency injection module for the application.
 * @author Taji Abdullah
 * */
val appModule = module {
    viewModelOf(::ConnectionScreenViewModel)
    viewModelOf(::ControlScreenViewModel)
    viewModelOf(::LogScreenViewModel)
}