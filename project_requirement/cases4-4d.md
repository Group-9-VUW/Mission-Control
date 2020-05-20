##  3.2 Functions

4.   Go/No go function <br>

The system must have a go/no go function. This function will define the area that is acceptable for the rocket to land in. The system must turn the acceptable area, a tolerance and the probabilities of landing in the acceptable area into a go/no go signal. If the system produces the no go signal, the rocket will be blocked from lauching. The goal of the go/no go system is to minimize the risk of injury or damage to the equipment by preventing a launch from taking place if certain conditions are not met. The minimum viable product is comprised of this use case, among others.

| Name | Go/No Go  |
| ------ | ------ |
| Participating Actors | Launcher (an individual who intends to launch a rocket) |
| Event flow | 1. The launcher indicates to the system that they intend to launch<br> 2. The system defines an acceptable landing area<br> 3. The system uses the probability of landing in an acceptable area with a tolerance to determine whether to send the go or no go signal<br> 4. The system indicates to the launcher that it is safe to launch <br>5. The launcher tells the system to launch the rocket <br>6. The system performs prelaunch checks <br>7. The system sends a signal to the rocket to begin the launch procedure <br>8. The rocket is launched after a countdown<br> **Alternative: not safe to launch** <br>4a. The system sends a no go signal to the rocket, preventing it from launching <br>4b. The launcher is told that the launch cannot commence. <br> **Alternative: prelaunch checks fail** <br> Go to 4a. <br> **Alternative: the launcher cancels the launch** <br> 8a. The system cancels the launch <br> 8b. The launcher is notified that the launch has been cancelled by the system |
| Entry Conditions |  |
| Exit Conditions |  |
| Special Requirements |  |