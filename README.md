# Drone Demo

This application connects to and controls a Mavlink drone running PX4 in a simulation environment. Manual scrolling is needed to view drone position on the map(see below).

---

## Screenshots

A video of the app functionality can be found in the asset folder in the root of the project.

---

## Features

- This project uses the MVVM architecture paradigm because this is the way.

- Code is contained in one module instead of being separated into modules that represent clean code architecture layers to help stay within time constraints.

- StateFlow is used for reactive programming to observe Drone state in real time.

- Koin is used for Dependency Injection.

- Jetpack Compose is used to declare the UI, because this is part of MAD (Modern Android Design).

- Google Maps are used to display drone location because they already have my info and the the other (good) map apis wanted payment information to gain access even for free tiers.


---

## Technologies Used

The following dependencies have been used.

- Jetpack Compose
- Koin (Dependency Injection)
- Mavlink SDK
- Kotlin StateFlow
- Google Maps

---

## Requirements

#### Android Studio

The latest version of Android Studio is required. The latest version can be  
downloaded from [here](https://developer.android.com/studio/).

#### Docker

[Docker Desktop](https://www.docker.com/products/docker-desktop/)

#### Google Maps API Key

Place your key in the local.properties file like this:

```
MAPS_API_KEY=<your key>
```

#### QGround Control

This is optional if you want to see the drone moving in the simulated environment. The app work will without this. [Download here]([Download and Install · QGroundControl User Guide](https://docs.qgroundcontrol.com/master/en/getting_started/download_and_install.html))

---

## Setup

- After installing Docker, run it.

- Ensure host machine and Android device are both on the same wifi network.

- From the terminal of the host machine run:

  ```
  docker run --rm -it jonasvautherin/px4-gazebo-headless:1.14.0 <phone ip address>    
  ```

- While thats running Install the app on the Android device. When both are completed the app should be able to connect to the drone in the simulated environment.


---

## Usage

**To connect to the drone:**

- Click the connect drone button twice, give a short pause before the second click.

**To fly the drone:**

- Use the negative button on the map to zoom all the way out.

- Scroll the map to move directly north, the drone is in Europe.

- When the drone has been located, zoom in close to be able to see its movement.

- Hit the take off button, the drone will be in a hover state.

- Hit the Up button, the drone will ascend about 10 meters. Do this a couple more times(I think the drone can crash into things in the simulator???)

- Once at a decent height that should avoid obstacles such as buildings or trees(or whatever I may have crashed into a few times????) other flight buttons can be explored.

- If the drone is landed in a weird location it might get into a weird state(I think it crashes????) (QGround Control is good to see open fields.)


**Notes**

- I once managed to have the drone end up upside down, this was determined by QGroundControl. The normal blue over green was reversed in the sensor display.

- I think I managed to get the drone stuck by flying forward from in hover height. This is my best guess, QGrondControl showed the drone marker on top of a tree, but the altitude was not high enough for the drone to be above a tree. The drone would not move up in altitude.

- Weird drone states are not handled at this time...


## Known Issues

This app has been tested on a OnePlus 9. There may be some unknown defects with other manufactures and or devices.

- If things are left running for a while, the app wont be able to reconnect because the port will still be in use.

- The size of the map needs to be larger.

- The map wont automatically relocate its camera position to view the Drone marker. Manual scrolling north from its current position is needed. Zooming out via the negative button is suggested to faster locate the drone marker placed on the map.

- exception messages are not surfaced to the user yet.

- The app currently wont recover from weird drone states.


---

## Improvements

- The MavServer should be ran from a background service, this was omitted for faster development.

- The server / drone connection process should return a value to prevent double clicking the connection button.

- Add voice capabilities to add vocal control of drone.

- Fix Map size.

- Figure out more MavSDK features to improve connection/disconnection experience. (Not much is documented for the Java language).

- Fix map camera position.

- Surface exception messages to user.