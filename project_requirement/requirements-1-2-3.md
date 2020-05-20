#### 1.3.2 Product functions

The Mission Control Software has a role in facilitating pre-launch, mid-flight, and post-flight responsibilities.

The primary pre-launch function of the Mission Control Software is to select potential launch sites. This is done at the user's home or place of work where they have
an internet connection, in which the user will select launch sites that they have access too, and the software will use current weather conditions to predict whether
a launch there would be safe. Safety is determined by their being a sufficiently low probability that the rocket will land in a populated area or otherwise breach 
rocketry regulations. Once at the selected landing site, a second round of simulations is ran using the weather data available from the ground of the launch site, 
to determine a final go or no go for launch. Probability is calculated by consulting the Monte Carlo Simulation Package.

If a go is given, the Mission Control Software is responsible for remotely arming the rocket's ejection charge and setting an optimal orientation for the rocket that will
minimize how far it lands from the launch site. This is accomplished by communicating with the Rocket Avionics Package.

When in flight, the Mission Control Software monitors the rocket over radio, recording flight paramaters such as position, orientation, velocity, and acceleration, as
well as stage information. This data is saved for futher dissection by the user.

After the rocket lands, the Mission Control Software will provide the user with the location of the rocket so that they can safely recover it for potential re-use (and
to avoid contamination of the environemnt)

A minmim viable version of the Mission Control Software would encompass the launch site selection and providing the location of the rocket. 