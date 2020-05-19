1   Get remote weather data from internet<br>
	The software must be able to pull weather data from an API (such as openweathermap or NOAA) and supply that data to other parts of the system. 
    This data must be as accurate and up to date as possible, since incorrect data can have a severe impact on functionality. 
    Determining probable landing locations and the "go no go" functions are two important aspects of the system 
    that heavily relay on accurate weather data, so data correctness should be ensured. 
    
    Alternatively, there is an API on the National Oceanic and Atmospheric Association (NOAA) website. 
    This API allows for 5 requests per second and a total of 10,000 requests per day. All responses from this API
    are in JSON. 

2   Enter local weather data<br>
	Weather data is available in JSON, XML or HTML formats, so parsing libraries will be required to extract the required information. Public libraries to parse those 3 formats are available.  
    In the case that there is no network connectivity at the launch site, weather forecast data can also be retrieved prior to travel to the launch site (although this requires a paid subscription
    to the api website).
    
    
    https://openweathermap.org/api 
    https://www.ncdc.noaa.gov/cdo-web/webservices/v2
