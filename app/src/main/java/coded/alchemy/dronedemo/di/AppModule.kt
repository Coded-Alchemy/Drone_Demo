package coded.alchemy.dronedemo.di

import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import coded.alchemy.dronedemo.ui.connection.ConnectionScreenViewModel


val appModule = module {
    viewModelOf(::ConnectionScreenViewModel)
    single { DroneRepository() }
    single { ServerRepository() }
}