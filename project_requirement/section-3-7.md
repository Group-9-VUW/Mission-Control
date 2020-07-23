## Systemic (Nonfunctional) Requirements 

### Availability
The mission control software will be available for use as long as the language level and libraries are non-deprecated.

### Security
Special care will be taken during development to ensure that the software has high security. After development has finished, the project team will no longer be updating the code base and hence the security level will remain the same.

### Effectiveness
The software will be as effective as the team can make it. During development, the aim will be to make the most effective and efficient version of the software. However, once the project is finished, new software for the same purpose by a different team may be available for use, which may surpass our software in terms of effectiveness and efficiency.

### Usability
Much like the effectiveness of the software, the project team will aim to make the software as usable as possible. Our stakeholder age range is quite vast (from primary school kids to the elderly) so we need to be certain that the mission control software is easily usable by multiple age groups.

### Interoperability with Existing Systems
The mission control software is designed to interpolate with other projects being developed simultaneously. These projects are the Rocket Systems Engineering project and the Monte Carlo Rocket Simulation project. Our software is not aiming to be interoperable with any other existing systems besides those previously mentioned.

### Retirement
If the decision to retire the software (i.e. it is deprecated) is made, then no sensitive data will need to be deleted / retained by the developers. This will rely upon on the stakeholders themselves as all logging data will be saved on their local machines as opposed to cloud storage.

### Obsolescence Management
As stated above, the software may be surpassed by newer effective software, which may leave our software obsolete. At this point, the development team may have a disclaimer on the repository to state that the software is now deprecated / obsolete. Unless other developers extend the code base, the software will forever be marked as such.

### Adaptability
Our software is going to be data agnostic, meaning that it will support multiple data formats. More specifically, these data formats are JSON, XML and CSV. As data formats evolve, a new and more effective data format may become available. While support for this new data format may be beneficial, the development team will not be updating the program to be able to handle it. This will again, be up to other developers and hobbyists who can extend the code base.

### Scalability
During development, the team will increase its functionality based on the Stakeholders needs. This will not be the case after development, but the open source nature of the software will allow for others to increase the scalability of the program.

### Survivability
The software will incorporate multiple fail safes and will utilise proper handling of safety critical systems. In the event of an attack or a failure, the system will try to save as much information as it can and will try to minimise harm to anyone that can be affected.

### Deployment 
The mission control software will be hosted on a repository from which users can download it from. Tags will be attached to each major version as development continues. 

### Documentation
All our code will be clearly documented with clear and concise comments. We will also include a readme file on the repository which will explain how to use the software. 

### Response Time
Our user interface should have a response time less than 25 milliseconds. However, external factors such as network requests are exempt from the requirements. 
### Privacy 
There is no persistent storage of the user’s data in our software. Regardless, the software will comply with all relevant privacy laws. 

### Testability
The process of testing our software should be as easy as possible. Testing is critical for ensuring the correctness of our software, so we will do everything we can to ensure that our software can be easily tested. 

### Platform Compatibility 
Our software should be compatible on as many platforms as possible. However, we will not be supporting old or deprecated versions of current software (I.e. the software will be compatible with Windows 10, but not Windows XP). The major platforms we will support are Windows 10, macOS and Linux. 

### Open Source 
The mission control software will be open source, it will be hosted in a Git repository. This allows users of the software to directly contribute to its code base after the team has finished their development. 
