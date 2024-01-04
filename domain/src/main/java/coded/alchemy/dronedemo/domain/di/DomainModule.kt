package coded.alchemy.dronedemo.domain.di

import coded.alchemy.dronedemo.domain.usecase.ConnectToDroneUseCase
import coded.alchemy.dronedemo.domain.usecase.DroneLandUseCase
import coded.alchemy.dronedemo.domain.usecase.DroneOrbitUseCase
import coded.alchemy.dronedemo.domain.usecase.DroneTakeOffUseCase
import coded.alchemy.dronedemo.domain.usecase.GetArmedValueUseCase
import coded.alchemy.dronedemo.domain.usecase.GetBatteryPercentageUseCase
import coded.alchemy.dronedemo.domain.usecase.GetConnectionStateUseCase
import coded.alchemy.dronedemo.domain.usecase.GetDroneSpeedUseCase
import coded.alchemy.dronedemo.domain.usecase.GetFlightLogsUseCase
import coded.alchemy.dronedemo.domain.usecase.GetFlightModeUseCase
import coded.alchemy.dronedemo.domain.usecase.GetGpsDataUseCase
import coded.alchemy.dronedemo.domain.usecase.GetPositionDataUseCase
import coded.alchemy.dronedemo.domain.usecase.MoveDroneUseCase
import coded.alchemy.dronedemo.domain.usecase.StartMissionUseCase
import org.koin.dsl.module

/**
 * DomainModule.kt
 *
 * This file provides the Koin dependency injection module for the domain module.
 * @author Taji Abdullah
 * */
val domainModule = module {
    single { ConnectToDroneUseCase(serverRepository = get(), droneRepository = get(), getConnectionStateUseCase = get()) }
    single { GetBatteryPercentageUseCase(droneRepository = get()) }
    single { GetDroneSpeedUseCase(droneRepository = get()) }
    single { GetGpsDataUseCase(droneRepository = get()) }
    single { GetArmedValueUseCase(droneRepository = get()) }
    single { GetFlightModeUseCase(droneRepository = get()) }
    single { GetPositionDataUseCase(droneRepository = get()) }
    single { MoveDroneUseCase(droneRepository = get()) }
    single { DroneOrbitUseCase(droneRepository = get()) }
    single { DroneLandUseCase(droneRepository = get()) }
    single { DroneTakeOffUseCase(droneRepository = get()) }
    single { GetFlightLogsUseCase(droneRepository = get()) }
    single { StartMissionUseCase(droneRepository = get()) }
    single { GetConnectionStateUseCase(droneRepository = get()) }
}