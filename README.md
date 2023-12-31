# Android My Weather
A simple weather app that showcases the use of modern Android application development methodologies. My Weather app uses the [Open Meteo](https://open-meteo.com/) API to get the weather forecast. Open Meteo offers free access for non-commercial use and no developer key required.

## Screenshot
![Screenshot1](/screenshots/Screenshot1.png)

## Tech
* Kotlin
  - Couroutines - perform background operations
  - Flow - data flow across all app layers
* Retrofit - networking
* UI
  - Jetpack compose - modern, native UI kit
  - Material Design 3 - application design system providing UI components
  - SplashScreen 
* Dependencies
  - Hilt - Android dependency injection
* Data
  - Room - persistence library that provides an abstraction layer over SQLite
  - DataStore - key-value pair data storage solution

## Features not yet implemented
* ~~Splash screen~~
* Add/delete location
* Temperature unit (F/C) settings
* Arrange and save order of locations
* Error handling
* Location’s weather detail screen that shows hourly weather forecast
* Weather forecast based on current location
* Widget
* ...
