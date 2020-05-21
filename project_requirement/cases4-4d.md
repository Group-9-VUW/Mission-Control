##  3.2 Functions

4.   Go/No go function <br>

The system must have a go/no go function. This function will define the area that is acceptable for the rocket to land in. The system must turn the acceptable area, a tolerance and the probabilities of landing in the acceptable area into a go/no go signal. If the system produces the no go signal, the rocket will be blocked from lauching. The goal of the go/no go system is to minimize the risk of injury or damage to the equipment by preventing a launch from taking place if certain conditions are not met. The minimum viable product is comprised of this use case, among others.

| Name | Go/No Go  |
| ------ | ------ |
| Participating Actors | Launcher (an individual who intends to launch a rocket) |
| Event flow | 1. The launcher indicates to the system that they intend to launch<br> 2. The system defines an acceptable landing area<br> 3. The system uses the probability of landing in an acceptable area with a tolerance to determine whether to send the go or no go signal<br> 4. The system indicates to the launcher that it is safe to launch <br>5. The launcher tells the system to launch the rocket <br>6. The system performs launch checks <br>7. The system sends a signal to the rocket to begin the launch procedure <br>8. The rocket is launched after a countdown<br> **Alternative: not safe to launch** <br>4a. The system sends a no go signal to the rocket, preventing it from launching <br>4b. The launcher is told that the launch cannot commence. <br> **Alternative: pre-launch checks fail** <br> Go to 4a. <br> **Alternative: the launcher cancels the launch** <br> 8a. The launcher indicates to the system that the launch should be cancelled <br>8b. The system cancels the launch <br> 8c. The launcher is notified that the launch has been cancelled |
| Entry Conditions | The system must be connected to a rocket over serial. <br> The system must have access to a map of the area. <br> The system must have a list of probable landing areas and their probabilities. |
| Exit Conditions | The rocket has been launched (go) or the launch was cancelled or not approved (no go). <br> The launcher has been notified of the outcome. |
| Special Requirements | The system and the rocket must be able to communicate to each other. <br>The system must be compatable with the operating system it is being run on. |

4a. The system must define acceptable areas. That is, the system must determine an area surrounding the launch zone that it is acceptable for the rocket to land in. The area that the system defines must be an area that does not include residential ares, and does not breach rocketry regulations.

4b. The system must use the acceptable areas it defines with the probability of landing in an acceptable area with a tolerance to determine whether it is safe to launch. If the probability of the rocket landing in an acceptable area is too low, the system will send a no go signal to the rocket, indicating that a launch is not approved. If the probability of the rocket landing in an acceptable area is high enough, the system will allow the user to launch the rocket.

4c. The system must perform pre-launch checks before the user goes to the launch site. These checks will ensure that, with the current weather, the launch site chosen by the user will be acceptable. These checks will ensure that the location the user chooses as a launch site is not in a residential area, and will not result in a break of rocketry regulations.

4d. The system must perform launch checks on location at the launch site. These checks will ensure that the correct go/no go decision has been reached given the actual launch site and current local weather conditions. Additionally, the checks at this stage will involve communicating with the rocket to check that all the rocket's systems are nominal before the system allows the user to begin the launch procedure.
