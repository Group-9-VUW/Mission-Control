### 3.1 External interfaces

#### Rocket
The rocket itself will communicate with mission control through LoRa (Long Range WAN) over USB serial. The data pipeline from the rocket can be described as follows:
```
Rocket Avionics <-> LoRa module <-> LoRa module <-> ground station electronics <-> USB serial <-> Laptop
```
#### Weather Data
As internet is not guaranteed in the field, weather data will be loaded prior to launch. 

##### OpenWeatherMap
Weather data is available from [OpenWeatherMap](https://openweathermap.org/api). Current wind speed (m/s), direction(degrees) and gust speed(m/s) is available through their web API. Temperature, humidity, visibility and rain data is also available. The data is provided in JSON format.
```
api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={your api key}
```
##### NOAA
NOAA has a [Global Forecast System](https://www.ncdc.noaa.gov/data-access/model-data/model-datasets/global-forcast-system-gfs) that provides historical records and weather predictions. This data is available from the NOAA National Operational Model Archive and Distribution System ([NOMADS](https://www.ncdc.noaa.gov/nomads/documentation/user-guide)) through FTP. This data may require a large amount of preparation in order to be useful and may require a degree of meteorological expertise to accurately parse. Additionally, there is a [JSON API](https://www.ncdc.noaa.gov/cdo-web/webservices/v2#gettingStarted) for climate data access.

The Cambridge University Spaceflight Wiki has produced a [balloon flight predictor](http://predict.habhub.org/) using this dataset. This open source implementation may provide some insight into handling this data.

#### Map Data
Map data is available from [OpenStreetMap](https://www.openstreetmap.org/). This low level data can be accessed through their API. This is important for providing relevant contextual information regarding the rocket's flight path.

#### Simulation
The mission control software must be able to consume simulation data in order to determine whether or not launch conditions are met. The simulation will be based upon the [OpenRocket](http://openrocket.info/) software. [Simulation listeners](http://wiki.openrocket.info/Simulation_Listeners) may be used to extract data from the simulation, such as a [roll control listener](http://www.soupwizard.com/openrocket/code-coverage/eclemma-20121216/OpenRocket/src/net.sf.openrocket.simulation.listeners.example/RollControlListener.java.html).

#### Launch Control
The mission control software is not responsible for issuing the launch command; the rocket will be launched through a seperate mechanical system. Mission control indicates whether or not launch conditions are met ("go or no go signal"). 

#### User Interface
The mission control software is to be controlled using a keyboard and mouse driven desktop GUI, displayed on a normal laptop screen with a resolution of at least 1024x768.

Data is to be displayed in a human readable format and should be augmented with graphical visualisations.