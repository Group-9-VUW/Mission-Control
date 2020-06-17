### 3.4 Performance requirements

#### How often the position of the rocket is updated on the map<br>
This will be a tick-based update where each tick can be a specified 
number of milliseconds, say 500ms for example. Every 500ms, the positional data
of the rocket can be retrieved and then be processed at mission control to
then be displayed on the map. At a tick rate of 500ms, the rockets position will be
updated 120 times over the course of 1 minute. 
The latency between the rocket and mission control will need to be considered. As the rocket
travels further away, the connection is likely to get weaker, so a variable tick-rate may be considered. 

#### No 'go' given on failure probabilities of greater than X%
As part of our go no go functionality, we will be calculating a probability of failure based on current conditions. 
These conditions can range from the current weather (i.e. wind speed or temperature) to the strength of the connection between 
the rocket and mission control. Each condition could be given weights (that are calibrated beforehand) which signify their 
overall impact as a cause of failure during the rocket launch. 
We will set a probability threshold at a value that will be decided in the future. If the probability of failure is greater than or equal
to our predefined threshold, then the rocket will be given the "no go", signifying that the chance of failure is too high.  

#### Number of Simultaneous users
Our program can have infinitely many users. However, each computer can only support one instance at a time and that instance can only support one rocket at a time. 

#### Amount and Type of Information to be handled 
The system should be able to handle as much information as the USB serial can send at its optimal performance. 
If the serial can transmit a high amount of data that we cannot cater to due to our technical
limitations, then we will aim to get as close to this as possible.

As for the type of information to be handled, our system will be data agnostic meaning that it can
parse and output any of the three major data file formats which are:
- JSON
- XML
- CSV

The format of the incoming data will be dependent on what format the Avionics team decide on. 

#### Map Data Caching 
An internet connection at the launch site is not guaranteed. So, prior to launch, our software should be able to 
cache map information of a 2km radius of the launch site. That way, map data at launch can be accessed without 
the need for an internet connection. 

#### Minimum Map Precision 
Our software should have a minimum precision of 3 metres on map features. That is, features must be no more than 3 metres away from their true position (i.e. the rockets positional data should always be within 3 metres of it.)

#### Maximum GUI response time
The GUI must have a response of time of less than 25 milliseconds. External factors such as network requests are exempt from this requirement. 

#### Rocket Data Incorporation
Once data is received from the rocket (whether it is pre, during or post launch), it must be incorporated (parsed and interpreted) into our system in under 100 milliseconds. 

#### Maximum System Memory Consumption
At its peak workload conditions, the mission control software should not be consuming more than 750 megabytes of memory at any given time. 
