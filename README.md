# Android My Weather
A simple weather app that showcases the use of modern Android application development methodologies.
My Weather app uses the [Open Meteo](https://open-meteo.com/) API to get the weather forecast. Open Meteo offers free access for non-commercial use and no developer key required.
My Weather app also uses the [Google Place Autocomplete](https://developers.google.com/maps/documentation/places/web-service/autocomplete) to search cities based on the query input and [Google Geocoding Autocomplete](https://developers.google.com/maps/documentation/geocoding/overview#place-id) to get lat/long of the city that's selected.

To use the API, you will need to obtain a free developer API key. See the Google API Documentation for instructions.
Once you have the key, add this line to the gradle.properties file, either in your user home directory (usually ~/.gradle/gradle.properties on Linux and Mac) or in the project's root folder:
```
google_api_key=<your Google access key>
```


## Screenshot
![Screenshot1](/screenshots/Screenshot1.png)
![Screenshot2](/screenshots/Screenshot2.png)
![Screenshot3](/screenshots/Screenshot3.png)
![Screenshot4](/screenshots/Screenshot4.png)
![Screenshot5](/screenshots/Screenshot5.png)

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
* ~~Add/delete location~~
* ~~Temperature unit (F/C) settings~~
* Arrange and save order of locations
* Error handling
* Location’s weather detail screen that shows hourly weather forecast
* Weather forecast based on current location
* Widget
* ...
