### 3.8 Physical and Environmental Requirements

#### 3.8.1 Physical Requirements

The device running the program must have enough space in its memory to download the program and save the files (log, weather and map data). This allows the program to run properly (or run at all).

The device must be able to recieve information through the USB serial, thus must have a USB port or be able to use an adapter to accept a USB device.

The device chosen to run the program on must be portable (eg. laptop) as it will be used in the field. The rocket and computer must be close enough to connect through USB Serial while the rocket is in flight.

The device must have enough charge to run the software. The program must work as efficiently as possible to reduce the power consumption.

#### 3.8.3 Environmental Conditions

The system must be able to retrieve a signal from the rocket while it's on the ground. It is preferred that the signal is also retrieved while the rocket is in the air. Other radio signals or buildings could interfere with the transfer of the signal.

The system must be able to cooperate with the rocket api (designed by the avionics teams) and the Monte Carlo simulation (designed by the simulation teams). This includes communicating over USB Serial and to another software project (might be in a different language!).