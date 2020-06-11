5. Rocket Status Display

The Mission Control Software must posses a display that shows all relevant data incoming from the rocket, specifically information about its state, position, and any other variable such as rotation and velocity that may be reported by the Rocket Avionics Package. This display must show this information in a way that a user can understand at a glance during live-fire conditions, where real-time understanding of the rocket's trajectory is important. This display should be accessible without continuous input from the user and therefore should be updated autonomously and shown all at once, without submenus.

| Name | Rocket Status Display  |
| ------ | ------ |
| Participating Actors | The rocket<br>The launcher |
| Event flow | 1. The user initializes the connection between the display and the rocket.<br>2. The system opens a static rocket status display, showing the rocket state as stationary and ready to launch, as well as its position on the map relative to its surroundings.<br>3. The user decides to initiate the launch.<br>4. The rocket begins sending continuous updates to the MCS.<br>5.The system interprets these updates, and updates the rocket status display with the new information.<br>6. The rocket finishes its flight.<br>7. The rocket status display marks the flight as over on screen, highlighting the final position of the rocket for collections.<br>8. The user collects the rocket and closes the status display.|
| Entry Conditions | The system must have access to a map of the immediate area.<br>The system must be connected to the rocket over serial. |
| Exit Conditions | Explicit termination by user input. |
| Special Requirements | Available screen resolution must be 1280 x 720 pixels.|

5a. 

The Rocket Status display must show the position of the rocket on a map, using satellite imagery to convey the position to the user with recognizable imagery and a colored icon to represent the rocket. The position of the rocket on the map must be updated autonomously with each update from the Rocket Avionics Package. The satellite imagery used for this function must be cacheable, so that it will function in a launch site without reliable internet access.

5b.

If the Rocket Avionics Package reports rotation and velocity, the Rocket Status Display will show a conceptual diagram of a rocket on a horizon, conveying its orientation. A vector diagram overlayed will show any velocity and acceleration data provided, also including the constant force of gravity, creating an incomplete force diagram allowing the user to visualize the rockets second-to-second status.

5c.

In the event that the connection between the MCS and the Rocket Avionics Package is severed unexpectedly, the Rocket Status display must report this in a timely manner to the user to avoid confusion with 'stuck' values. This warning must take up 25% of the screen area so that it is noticable.

5d.

The user shall have the option to record oncoming status data to a file in a format of their choice. This option shall be available from a menu bar. At least one of these formats should be in a ASCII/text format that is readable via text editor, complete with newlines for each new section of data.

5e.

The user should be able to play back files they've saved, creating a non-live version of the Rocket Status Display that will show a launch as though it were a real launch.