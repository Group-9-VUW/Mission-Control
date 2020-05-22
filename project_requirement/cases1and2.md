#### 1   Get remote weather data from internet<br>
	The software must be able to pull weather data from an API (such as openweathermap or NOAA) and supply that data to other parts of the system. 
    This data must be as accurate and up to date as possible, since incorrect data can have a severe impact on functionality. 
    Determining probable landing locations and the "go no go" functions are two important aspects of the system 
    that heavily relay on accurate weather data, so data correctness should be ensured. 
    
    Alternatively, there is an API on the National Oceanic and Atmospheric Association (NOAA) website. 
    This API allows for 5 requests per second and a total of 10,000 requests per day. All responses from this API
    are in JSON. 
    
#### 2   Enter local weather data<br>
    An internet connection is not guaranteed at our launch site, so it is appropriate to consider methods to
    manually gather local weather data and input it into our software. Our software should have a form with specified inputs
    that will need to be gathered (i.e. temperature, wind speed). Specific units (preferably metric) should be used to avoid any confusion
    and invalid calculations. 
    
    Specialised tools are available for people to gather their own weather data. To calculate the wind speed, 
    an anemometer can be used. An anemometer has multiple arms attached to a vertical rod. As the wind blows, the cups on the edge 
    of the arms rotate and the anemometer counts the number of revolutions per second to calculate the wind speed.
    Thermometers can be utilised to measure the temperature, and barometers can be used to measure air pressure.
    There are multiple methods and tools to measure the weather manually and if needed, will be decided in future meetings.

    
    https://openweathermap.org/api 
    https://www.ncdc.noaa.gov/cdo-web/webservices/v2
    https://www.nationalgeographic.org/encyclopedia/anemometer/