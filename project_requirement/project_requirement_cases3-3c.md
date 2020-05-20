# 3.2 Functions



3. Determine probable landing locations<br>

The program must use the local weather conditions, and an external Monte Carlo simulation in order to determine, and display to the user, the probable landing locations of the rocket if it were launched in the current conditions. The minimum viable product is comprised of this use case, among others.

| Name | Determine Probable Landing Location |
| ------ | ------ |
| Participating Actors | Launcher (an individual who intends to launch a rocket) |
| Event flow | 1. Launcher indicates to the system that they intend to launch <br>2. The system uses GPS coordinates to get the local weather data from NOAA<br>3. The system sends the local weather data to an external Monte Carlo simulation <br>4. The system receives a set of probable landing locations from the Monte Carlo simulation <br>5. The system displays the probable landing locations graphically to the launcher <br>6. The system indicates to the launcher the probability of the landing zone being acceptable/safe <br>7. The system indicates to the launcher that it is safe to launch<br> **Alternative: not safe to launch**<br>7a. The system indicates to the launcher that it is unsafe to launch<br>7b. The system suggests changes for the launcher to make to change the probable landing locations<br>7c. The launcher makes changes to the parameters (e.g. rocket angle) <br>7d. The launcher enters their changes into the system <br> Return to Step 2 <br> **Alternative: launcher enters weather conditions** <br>2a. The launcher can choose to enter the local weather conditions manually<br> 2b. The system prompts the launcher to enter all the relevant local weather conditions <br>2c. The launcher enters the local weather conditions <br> Return to Step 3 |
| Entry Conditions | The system must be connected to a rocket over serial.<br> An external Monte Carlo simulation that the system can connect to exists. <br> The system can access NOAA or the launcher knows the specific local weather conditions. |
| Exit Conditions | The launcher is notified of the probable landing locations and whether it is safe to launch. |
| Special Requirements | The Monte Carlo simulation must simulate the launch correctly and determine probable landing locations accurately. <br> The system must be compatable with the operating system it is being run on. |