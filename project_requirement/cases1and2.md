1   Enter local weather data<br>
2   Get remote weather data from internet<br>
	The software must be able to pull weather data from an API (such as openweathermap) and supply that data to other parts of the system. 
    This data must be as accurate and up to date as possible, since incorrect data can have a severe impact on functionality. 
    Determining probable landing locations and the "go no go" functions are two important aspects of the system 
    that heavily relay on accurate weather data, so data correctness should be ensured. 

    Weather data is avialible in JSON, XML or HTML formats, so parsing libraries will be required to extract the required information. 
    In the case that there is no network connectivity at the launch site, weather forecast data can also be retrieved (although this requires a paid subscription
    to the api website.)

    https://openweathermap.org/api 

