package coded.alchemy.dronedemo.di

import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import coded.alchemy.dronedemo.domain.ConnectToDroneUseCase
import coded.alchemy.dronedemo.domain.GetBatteryPercentageUseCase
import coded.alchemy.dronedemo.domain.GetDroneSpeedUseCase
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
}