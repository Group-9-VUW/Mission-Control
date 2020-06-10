### 3.1 Stakeholders

See ISO/IEC/IEEE 42010 clause 5.3 and ISO/IEC/IEEE 12207 clause 6.4.4.3(2).
List stakeholders

The stakeholders for this project are the general Hobby Rocket Community (i.e The New Zealand Rocketry Association) and Andre Geldenhuis.

Stakeholder concerns:

| **Stakeholder Concern**   |  **Effect Of Invalid Handling Of The Concern On The Stakeholder**  |
|---|---|
| System Integrity (Protection against unauthorised access)    | Unauthorised access could lead to unwanted rocket launches, potentially putting the stakeholders and civilians at risk of harm. Stakeholders could be held for ransom (the attacker could threaten to launch the rocket unless they get payment). As a result of these scenarios, the stakeholders trust in the developers will be diminished.   |
| Data Breaches  | If a stakeholder is designing a proprietary rocket, a data breach of logging information may lead to their design being stolen. |
|  Safety Hazards (I.e. while arming the ignition charge or incorrectly giving a GO for launch) | Serious injury can be caused, and the developers will be held liable. Possible harm to bystanders can be caused as the rocket can travel far in high winds.   |
|Use of deprecated libraries | The use of deprecated libraries should be avoided as they could possibly be removed in the future. Deprecated libraries are also known for having issues and being an inefficient method to complete their designated task. Using these libraries will affect performance of the stakeholder’s software and will leave it vulnerable to fatal errors. | 
|Use of open source libraries | Each open source library used in the software must be verified that it has a valid license. If this is not done before deployment, the software will under the law be no longer usable or distributable.   |

#### Software Life Cycle

##### Availability 
The mission control software will be available for use as long as the language level and libraries are non-deprecated. 

##### Security 
Special care will be taken during development to ensure that the software has high security. After development has finished, the project team will no longer be updating the code base and hence the security level will remain the same. 

##### Effectiveness 
The software will be as effective as the team can make it. During development, the aim will be to make the most effective and efficient version of the software. However, once the project is finished, new software for the same purpose by a different team may be available for use, which may surpass our software in terms of effectiveness and efficiency. 

##### Usability 
Much like the effectiveness of the software, the project team will aim to make the software as usable as possible. Our stakeholder age range is quite vast (from primary school kids to the elderly) so we need to be certain that the mission control software is easily usable by multiple age groups. 

##### Interoperability with Existing Systems
The mission control software is designed to interpolate with other projects being developed simultaneously. These projects are the Rocket Systems Engineering project and the Monte Carlo Rocket Simulation project. Our software is not aiming to be interoperable with any other existing systems besides those previously mentioned. 

##### Retirement 
If the decision to retire the software (i.e. it is deprecated) is made, then no sensitive data will need to be deleted / retained by the developers. This will rely upon on the stakeholders themselves as all logging data will be saved on their local machines as opposed to cloud storage. 

#### Support

Once the software is developed and deployed, support by the original development team will end for it. The code base will however be open source, so other developers and hobbyists may be available to extend or support the software. 

##### Obsolescence Management
As stated above, the software may be surpassed by newer effective software, which may leave our software obsolete. At this point, the development team may have a disclaimer on the repository to state that the software is now deprecated / obsolete. Unless other developers extend the code base, the software will forever be marked as such. 

#### Evolution of The Software System 

##### Adaptability 
Our software is going to be data agnostic, meaning that it will support multiple data formats. More specifically, these data formats are JSON, XML and CSV. As data formats evolve, a new and more effective data format may become available. While support for this new data format may be beneficial, the development team will not be updating the program to be able to handle it. This will again, be up to other developers and hobbyists who can extend the code base. 

##### Scalability 
During development, the team will increase its functionality based on the Stakeholders needs. This will not be the case after development, but the open source nature of the software will allow for others to increase the scalability of the program. 

##### Survivability 
The software will incorporate  multiple fail safes and will utilise proper handling of safety critical systems. In the event of an attack or a failure, the system will try to save as much information as it can and will try to minimise harm to anyone that can be affected. 

<br>
<br>
<br>
<br>
For most systems this will be about 2 pages, including a table mapping concerns to stakeholder.

Document Used (Delete This After Merge): https://ieeexplore-ieee-org.helicon.vuw.ac.nz/stamp/stamp.jsp?tp=&arnumber=8392559
* "stakeholder" refers to an individual or organization with an interest in the system"