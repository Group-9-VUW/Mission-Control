### 3.8 Physical and Environmental Requirements

#### 3.8.1 Physical Requirements

*Include constraints on weight, volume, and dimension.*

The project is purely software so is not constrained by physical limits.

#### 3.8.3 Environmental Conditions

*Address natural environment (wind, mold, etc); induced environment (motion, shock, etc); electromagnetic signal environment; self-induced environment (motion, shock, etc); threat; and cooperative environment.*

The system must be able to retrieve a signal from the rocket while it's on the ground. It is preferred that the signal is also retrieved while the rocket is in the air. Other radio signals or buildings could interfere with the transfer of the signal.

The system must be able to cooperate with the rocket api (designed by the avionics teams) and the monte carlo simulation (designed by the simulation teams). This includes communicating over USB Serial and to another software project (might be in a different language!).