package coded.alchemy.dronedemo.di

import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import coded.alchemy.dronedemo.domain.ConnectToDroneUseCase
import coded.alchemy.dronedemo.domain.DroneLandUseCase
import coded.alchemy.dronedemo.domain.DroneOrbitUseCase
import coded.alchemy.dronedemo.domain.DroneTakeOffUseCase
import coded.alchemy.dronedemo.domain.GetArmedValueUseCase
import coded.alchemy.dronedemo.domain.GetBatteryPercentageUseCase
import coded.alchemy.dronedemo.domain.GetDroneSpeedUseCase
import coded.alchemy.dronedemo.domain.GetFlightModeUseCase
import coded.alchemy.dronedemo.domain.GetGpsDataUseCase
import coded.alchemy.dronedemo.domain.GetPositionDataUseCase
import coded.alchemy.dronedemo.domain.MoveDroneUseCase
import coded.alchemy.dronedemo.ui.connection.ConnectionScreenViewModel
import coded.alchemy.dronedemo.ui.control.ControlScreenViewModel
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
    single { DroneRepository() }
    single { ServerRepository() }
    single { ConnectToDroneUseCase( serverRepository = get(), droneRepository = get()) }
    single { GetBatteryPercentageUseCase( droneRepository = get()) }
    single { GetDroneSpeedUseCase( droneRepository = get()) }
    single { GetGpsDataUseCase( droneRepository = get()) }
    single { GetArmedValueUseCase( droneRepository = get()) }
    single { GetFlightModeUseCase( droneRepository = get()) }
    single { GetPositionDataUseCase( droneRepository = get()) }
    single { MoveDroneUseCase( droneRepository = get()) }
    single { DroneOrbitUseCase( droneRepository = get()) }
    single { DroneLandUseCase( droneRepository = get()) }
    single { DroneTakeOffUseCase( droneRepository = get()) }
}