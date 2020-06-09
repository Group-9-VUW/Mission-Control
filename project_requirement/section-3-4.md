### 3.4 Performance requirements

#### How often the position of the rocket is updated on the map<br>
This will be a tick-based update where each tick can be a specified 
number of milliseconds, say 500ms for example. Every 500ms, the positional data
of the rocket can be retrieved and then be processed at mission control to
be then displayed on the map. At a tick rate of 500ms, the rockets position will be
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
Each program will support one user. However, our system will support multiple instances running simultaneously. 

#### Amount and Type of Information to be handled 
The system should be able to handle as much information as the USB serial can send at its optimal performance. 
If the serial can transmit a high amount of data that we cannot cater to due to our technical
limitations, then we will aim to get as close as possible.

As for the type of information to be handled, our system will be data agnostic meaning that it can
parse and output any of the three major data file formats which are:
- JSON
- XML
- CSV

The format of the incoming data will be dependent on what format the Avionics team decide on. 

#### Map Data Caching 
An internet connection at the launch site is not guaranteed. So prior to launch, our software should be able to 
cache map information of a 2k radius of the launch site. That way, map data at launch can be accessed without 
the need for an internet connection. 

#### Minimum Map Precision 
Our software should have a minimum precision of 3 meteres on map features. 
That is, features must be no more than 3 metres away from their true position (i.e. the rocket location should always be within 3 meteres of it.)

#### Maximum GUI response time
The GUI must have a response of time of less than 25 milliseconds. External factors such as network requests are exempt from this requirement. 

#### Rocket Data Incorporation
Once data is recieved from the rocket (whether it is pre, during or post launch), it must be incorporated (parsed and interpreted) within our system under 100 milliseconds. 

#### Maximum System Memory Consumption
At its peak workload conditions, the mission control software should not be consuming more than 750 megabytes of memory at any given time. 

See 9.5.13. for most systems this will be around one page. Hardware projects also see section 9.4.6.

> **9.5.13 Performance requirements** <br>
> Specify both the static and the dynamic numerical requirements placed on the software or on human interaction with the software as a whole. 
> 
> Static numerical requirements may include the following:
> 
> a) The number of terminals to be supported;  
> b) The number of simultaneous users to be supported;  
> c) Amount and type of information to be handled.
> 
> Static numerical requirements are sometimes identified under a separate section entitled Capacity.
> 
> Dynamic numerical requirements may include, for example, the numbers of transactions and tasks and the amount of data to be processed within certain time periods for both normal and peak workload conditions. The performance requirements should be stated in measurable terms.
> 
>  For example, "_95 % of the transactions shall be processed in less than 1 second._" rather than, "An operator shall not have to wait for the transaction to complete."
> 
> NOTE Numerical limits applied to one specific function are normally specified as part of the processing subparagraph description of that function.