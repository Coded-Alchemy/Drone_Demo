package coded.alchemy.dronedemo.di

import coded.alchemy.dronedemo.ui.connection.ConnectionScreenViewModel
import coded.alchemy.dronedemo.ui.control.ControlScreenViewModel
import coded.alchemy.dronedemo.ui.log.LogScreenViewModel
import coded.alchemy.dronedemo.ui.mission.MissionScreenViewModel
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
    viewModelOf(::MissionScreenViewModel)
}