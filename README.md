![build](https://github.com/Group-9-VUW/Mission-Control/workflows/build/badge.svg) ![codecov](https://codecov.io/gh/Group-9-VUW/Mission-Control/branch/main/graph/badge.svg?token=ZL1LJ9XAVU) ![license](https://img.shields.io/badge/license-GLP-blue)

# Mission Control
#### Group 9

## How to run

You can not launch without saving the site beforehand. This loads the required data to make sure launch is as safe as possible.

### Choose and Save a Launch Site
- Run from main method in Main class
- Choose to create a new launch

Make sure that wifi is connected. Only one launch can be saved at one time to reduce space on your machine.

- If a message comes up that the Weather or Map is not available, try again later

If problem continues, try using a different wifi connection.

- Choose a location on the map where you want to launch

The map might take a while to load. Make sure that you are allowed to launch from your chosen site.

- In the 'Simulation' menu, choose the 'Enter Launch Rod Data'
- Enter the data about your launch rod
- Click the 'Run Simulation' button

Running the Simulation takes a while, so don't panic if nothing happens. It first gets the weather data, then runs the simulation, then retrieves the appropriate map data.

- If the Simulation is successful, most of the possible landing sites are on acceptable locations, save the launch
- Else click the 'Select Different Site' button which will return you to the select site map

### Launch the Rocket
- Run from main method in Main class
- Choose to run a saved launch

You don't need to be connected to the wifi.

Don't launch unless the rocket is disarmed else the software inside the rocket won't work, the parachute won't deploy, and the rocket will crash into the ground at dangerous speeds.

- In the 'Avionics' menu, choose to 'Connect to Rocket'
- Find the COM port where the rocket LORA is connected

If not shown, disconnect and try again.

- If you have local weather data, choose to 'Get Local Data' from the 'Simulation' menu, and enter the information

This data isn't currently used by the simulation.

- Run the Simulation again by choosing 'Run Simulation' in the 'Simulation' menu

This will be a lot quicker than when creating the launch as the data is already saved.

- Double check the area around you and the landing sites to check that it is safe to launch (i.e. no little children, etc.)
- Arm the rocket using the 'Arm' button
- To disarm the rocket, press the 'Disarm' button
- Move away from the rocket and launch

The program won't launch the rocket for you.

- Data from the rocket, and the rocket's position on the map, will be displayed as the rocket flies
