####6 Suggesting launch angle 
The application can determine the correct launch angle for the rocket to follow the desired trajectory. This will take into account environmental factors such as wind speed. This is important to reduce the amount of in flight adjustment required, immproving rocket stability.

This data may be calculated from the mission control software itself, or passed on from the Monte Carlo simulation.

####7 Arm ejection charge
Ejection charges are controlled by the avionics team, with their detonation time determined by the the rocket's flight stage through barometric pressure or time. Upon detonation of a charge, this information should be displayed in the mission control software.

There are a number of ways that mission control can determine whether charges have been detonated. Firing the charges results in a large voltage drop, which when corroberated with a predetermined expected detonation time, changes in pressure and expected change in flight path provides an indication that the ejection charge has been deployed successfully.

If an ejection charge malfunction has been detected through a severe deviation from expected flight path or lack of expected voltage drop, mission control should alert the user of this.
