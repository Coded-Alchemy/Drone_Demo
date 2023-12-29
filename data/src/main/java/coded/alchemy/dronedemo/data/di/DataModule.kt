package coded.alchemy.dronedemo.data.di

import coded.alchemy.dronedemo.data.DroneRepository
import coded.alchemy.dronedemo.data.ServerRepository
import coded.alchemy.dronedemo.data.SpeechRecognizer
import org.koin.dsl.module

/**
 * DataModule.kt
 *
 * This file provides the Koin dependency injection module for the data module.
 * @author Taji Abdullah
 * */
val dataModule = module {
    single { DroneRepository() }
    single { ServerRepository() }
    single { SpeechRecognizer }
}