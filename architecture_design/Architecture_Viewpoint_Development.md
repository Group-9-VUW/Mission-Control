### 4.2 Development

#### 4.2.1 Idenfitying and Supplementary Information

Project is based on the MVC (model, view, controller) design pattern. The Controller package depends on the other packages, while the other packages don't depend on anything else. This means that only the Controller package is affected by run-on package changes.

*As specified by organization and/or project*

#### 4.2.2 Identification of its Governing Viewpoint

*Will be decided longer*

#### 4.2.3 Architecture Model

*Addresses all the corcerns framed by its governing viewpoint and cover the whole system from that viewpoint*

##### Model Kind

The Component Diagram is a Structural Model which is a representation that shows the arrangement of elements with respect to each other and where necessary shows the interfaces between elements and with external entities. Such a model enables consolidating or identifying the physical interfaces between system elements in a level of the system hierarchy and between levels of the system hierarchy, as well as those with external entities to the concerned system (in its environment/context).

The external entities in the system are the Monte Carlo system that will be created by another student team (teams 13 - 18) and the Observer which creates a back loop from the Model to the Controller.

##### V1.2
![](architecture_design/First_Draft.png "")

#### 4.2.4 Known Issues within a View

The Controller package depends on a lot of other packages, meaning it might have problems when its dependencies change.

*With Respect to its governing viewpoint*