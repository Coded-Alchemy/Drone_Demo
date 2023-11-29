# Drone Demo

This application connects to and controls a Mavlink drone running PX4 in a simulation environment.

---

## Screenshots
![image](https://github.com/Coded-Alchemy/Drone_Demo/assets/3044292/9d7083da-1253-47b2-af62-be3acf6fc605)

---

## Features

- This project uses the MVVM archetecture paradim.

- Code is contained in one module instead of being sperated into modules that represent clean code archetecture layers to help stay within time constraints.

- The MavServer should be ran from a background service, this was omited for time constaints.

- StateFLow is used for reactive programming to observe Drone state in real time.

- Koin is used for Dependency Injection.

- Jetpack Compose is used to declare the UI, because this is part of MAD (Modern Android Design).


---

### Technologies Used

- Jetpack Compose
- Koin (Dependency Injection)
- Mavlink SDK
- Kotlin StateFlow

---

## Requirements

#### Android Studio

The latest version of Android Studio is required. The latest version can be  
downloaded from [here](https://developer.android.com/studio/).

#### QGround Control

#### Docker

#### Simulation Environment
