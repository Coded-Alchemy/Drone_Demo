package coded.alchemy.dronedemo.di

import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import coded.alchemy.dronedemo.ui.connection.ScreenViewModel


val appModule = module {
    viewModelOf(::ScreenViewModel)
    single { DroneRepository() }
    single { ServerRepository() }
}