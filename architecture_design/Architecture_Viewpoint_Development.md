### 4.2 Development

#### 4.2.1 Design Pattern

Project is based on the MVC (model, view, controller) design pattern. The Controller package depends on the other packages, while the other packages don't depend on anything else. This means that only the Controller package is affected by run-on package changes. This pattern was chosen as it was known by all team members allowing conversation about the various components to be easy to understand for everyone, and it forces separation of most packages, reducing dependancies.

#### 4.2.2 Model Kind

The Component Diagram is a Structural Model which is a representation that shows the arrangement of elements with respect to each other and where necessary shows the interfaces between elements and with external entities. Such a model enables consolidating or identifying the physical interfaces between system elements in a level of the system hierarchy and between levels of the system hierarchy, as well as those with external entities to the concerned system (in its environment/context).

The external entities in the system are the Monte Carlo system that will be created by another student team (teams 13 - 18) and the Observer which creates a back loop from the Model to the Controller.

#### 4.2.3 Components

##### Controller

Controls the GUI, recieves the information from the users (via the interface), and mediates the information passes between model and view.

##### Model

Controls the logic of the program, holds all the saved information (apart from info saved into files).

##### View

Controls what is shown to the user (interface).

##### Persistence

Saves information to files, reads information from files.

##### Network

Gets information from network-connected external entities, sends information to network-connected external entities.

##### Monte Carlo (NOT OUR CODE)

Simulates the launch to check for probability of acceptable launch. 

##### Observer

Communicates information back to the Controller from the Model, follows the Observer Design Pattern.

#### 4.2.3 Model v 1.2
![](architecture_design/First_Draft.png "")