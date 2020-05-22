# ENGR 301: Project Requirements Document

The aim of this document is to specify the requirements of the system your group is to build. The focus of a requirements document is the problem you are attempting to solve:  not a first attempt at a solution to that problem. This document should communicate clearly to the supervisor, client and course coordinator what the system you build is going to do, and what constraints it must meet while doing so.

The document should also demonstrate your understanding of the main analysis principles and quality guidelines, and applicable standards, using tools and notations as necessary to communicate the requirements precisely, unambiguously and clearly in a written technical document. Page specifications below are *limits not targets* and refer to the pages in the PDF generated from the markdown. Because the size of your document is necessarily limited, you should ensure that you focus your efforts on those requirements that are most important to completing a successful system: if sections are at their page limit, indicate how many items would be expected in a complete specification. 

The ENGR 301 project proposal and requirements document should be based on the standard ISO/IEC/IEEE 29148:2011(E), primarily sections 8.4 and 9.5, plus section 9.4 for projects involving hardware and ISO 25010 SQuaRE for systemic requirements. While excerpts from the standard have been quoted within the template, to understand what is required it will be necessary to read earlier sections of the standards themselves. A supplementary treatment of requirements gathering in engineering projects may be found in [Requirements in Engineering Projects](https://victoria.rl.talis.com/items/F166DA94-DAD8-FBDB-0785-7A63C9BA3603.html?referrer=%2Flists%2F5886F297-2506-1F17-45D9-7F04CEE284EE.html%23item-F166DA94-DAD8-FBDB-0785-7A63C9BA3603) (Talis). The requirements document should contain the sections listed below, and conform to the formatting rules listed at the end of this brief. 

All team members are expected to contribute equally to the document and list their contributions in section 6 of the document. You should work on your document in your team's GitLab repository. While collective contributions are expected to be the exception rather than the rule, if more than one team member has contributed to a particular commit then all those team member IDs should be included in the first line of the git commit message. `git blame`, `git diff`, file histories, etc. will be tools used to assess individual contributions, so everyone is encouraged to contribute individually, commit early and commit often. Any team wishing to separate individually contributed sections into a single file before collation into the single proposal document for submission is welcome to do so.

---

<div style="page-break-after: always;"></div>

# ENGR 301 Project *NN* Project Proposal and Requirements Document
#### Author list, a comma-separated list of the names of each member of the team.

## 1. Introduction

One page overall introduction including sections 1.1 and 1.2.

### Client

Identify the client and their contact details

### 1.1 Purpose

One sentence describing the purpose of the system (9.5.1)

### 1.2 Scope

One paragraph describing the scope of the system (9.5.2)

### 1.3 Product overview 
#### 1.3.1 Product perspective

Mention avionics and monte carlo.

Larger system is abstractly the process of launching the rocket, include identifying launch sites and actually managing the rocket after it's launched.

Mission control <--> avionics is over USB serial<br>
Mission control <--> "Simulation listeners"

Mention the on-site wind reading

Include details about the user interface, such as where the data from the rocket is shown.

One page defining the system's relationship to other related products
(9.5.3. but not the subsections in the standard.)

> **9.5.3 Product perspective** <br>
> Define the system's relationship to other related products. 
> 
> If the product is an element of a larger system, then relate the requirements of that larger system to the functionality of the product covered by the software requirements specification.
> 
> If the product is an element of a larger system, then identify the interfaces between the product covered by the software requirements specification and the larger system of which the product is an element. 
>
> A block diagram showing the major elements of the larger system, interconnections, and external interfaces can be helpful.
> 
> Describe how the software operates within the following constraints:  
a) System interfaces;  
b) User interfaces;  
c) Hardware interfaces;  
d) Software interfaces;  
e) Communications interfaces;  
f) Memory;  
g) Operations;  
h) Site adaptation requirements.

#### 1.3.2 Product functions

One page summary of the main functions of the product (9.5.4), briefly characterising the minimum viable product.

Determine probable landing locations (..integration with monte carlo?)  <-- very important<br>
Go no go function <br>
Relaying rocket information to user interface <-- very important (specifically location, velocity, rotation, not so important)<br>
Arm ejection charge <br>
Read weather conditions locally and over the internet <-- very important. <br>
Suggesting launch angle<br>
Record/playback functionality<br>

#### 1.3.3 User characteristics   

Hobby rocket people

Customer needs to be mentioned here

One page identifying the main classes of users and their characteristics (9.5.5) 

#### 1.3.4 Limitations

Regulatory: Hobby rocket regulations<br>
Hardware: Lack of internet at launch site, max range and delay on the usb serial<br>
Interfaces to other applications: max range and delay on the usb serial?<br>
Find out what parallel operation means<br>
Find out whatever a limitation on an audit function is<br>
Control functions: inability to direct rocket in flight?<br>
Higher-order language requirements: Limitations due to swing<br>
Find out about the handshake protocols<br>
How many simultaneous connections via radio<br>
Quality requirements: <br>

One page on the limitations on the product (9.5.6)

## 2. References

References to other documents or standards. Follow the IEEE Citation  Reference scheme, available from the [IEEE website](https://www.ieee.org/) (please use the search box). (1 page, longer if required)

## 3. Specific requirements  

20 pages outlining the requirements of the system. You should apportion these pages across the following subsections to focus on the most important parts of your product.

### 3.1 External interfaces

The rocket itself will communicate with mission control through LoRa (Long Range WAN) over USB serial. The data pipeline from the rocket can be described as follows:

Rocket Avionics <-> LoRa module <-> LoRa module <-> ground station electronics <-> USB serial <-> Laptop

Weather data is available from [OpenWeatherMap](https://openweathermap.org/api). Current wind speed, direction and gust speed is available through their web API. Temperature, humidity, visibility and rain data is also available. The data is provided in JSON format.

NOAA has a [Global Forecast System](https://www.ncdc.noaa.gov/data-access/model-data/model-datasets/global-forcast-system-gfs) that provides historical records and weather predictions. This data is available from the NOAA National Operational Model Archive and Distribution System ([NOMADS](https://www.ncdc.noaa.gov/nomads/documentation/user-guide)) through FTP. This data may require a large amount of preparation in order to be useful and may require a degree of meteorological expertise to accurately parse. Additionally, there is a [JSON API](https://www.ncdc.noaa.gov/cdo-web/webservices/v2#gettingStarted) for climate data access.

The Cambridge University Spaceflight Wiki has produced a [balloon flight predictor](http://predict.habhub.org/) using this dataset. This open source implementation may provide some insight into handling this data.

Map data is available from [OpenStreetMap](https://www.openstreetmap.org/). This low level data can be accessed through their API. This is important for providing relevant contextual information regarding the rocket's flight path.

The mission control software must be able to consume simulation data in order to determine whether or not launch conditions are met. The simulation will be based upon the [OpenRocket](http://openrocket.info/) software. [Simulation listeners](http://wiki.openrocket.info/Simulation_Listeners) may be used to extract data from the simulation, such as a [roll control listener](http://www.soupwizard.com/openrocket/code-coverage/eclemma-20121216/OpenRocket/src/net.sf.openrocket.simulation.listeners.example/RollControlListener.java.html).

The mission control software is not responsible for issuing the launch command; the rocket will be launched through a seperate mechanical system. Mission control indicates whether or not launch conditions are met ("go or no go signal"). 

Confer with the other teams for the data formats, command formats, and endmessages
range accuracy and tolerance just a function of the sensors on the rocket.


See 9.5.10. for most systems this will be around one page. 

### 3.2 Functions

Read weather conditions locally and over the internet <-- very important. <br>
1   Enter local weather data<br>
2   Get remote weather data from internet<br>
	The software must be able to pull weather data from an API (such as openweathermap) and supply that data to other parts of the system. 
    This data must be as accurate and up to date as possible, since incorrect data can have a severe impact on functionality. 
    Determining probable landing locations and the "go no go" functions are two important aspects of the system 
    that heavily relay on accurate weather data, so data correctness should be ensured. 

    Weather data is avialible in JSON, XML or HTML formats, so parsing libraries will be required to extract the required information. 
    In the case that there is no network connectivity at the launch site, weather forecast data can also be retrieved (although this requires a paid subscription
    to the api website.)

    https://openweathermap.org/api 
   
3. Determine probable landing locations<br>

The system must use the local weather conditions, and an external Monte Carlo simulation in order to determine, and display to the user, the probable landing locations of the rocket if it were launched in the current conditions. The minimum viable product is comprised of this use case, among others.

| Name | Determine Probable Landing Location |
| ------ | ------ |
| Participating Actors | Launcher (an individual who intends to launch a rocket) |
| Event flow | 1. Launcher indicates to the system that they intend to launch <br>2. The system uses GPS coordinates to get the local weather data from NOAA<br>3. The system sends the local weather data to an external Monte Carlo simulation <br>4. The system receives a set of probable landing locations from the Monte Carlo simulation <br>5. The system displays the probable landing locations graphically to the launcher <br>6. The system indicates to the launcher the probability of the landing zone being acceptable/safe <br>7. The system indicates to the launcher that it is safe to launch<br> **Alternative: not safe to launch**<br>7a. The system indicates to the launcher that it is unsafe to launch<br>7b. The system suggests changes for the launcher to make to change the probable landing locations<br>7c. The launcher makes changes to the parameters (e.g. rocket angle) <br>7d. The launcher enters their changes into the system <br> Return to Step 2 <br> **Alternative: launcher enters weather conditions** <br>2a. The launcher can choose to enter the local weather conditions manually<br> 2b. The system prompts the launcher to enter all the relevant local weather conditions <br>2c. The launcher enters the local weather conditions <br> Return to Step 3 |
| Entry Conditions | The system must be connected to a rocket over serial.<br> An external Monte Carlo simulation that the system can connect to exists. <br> The system can access NOAA or the launcher knows the specific local weather conditions. |
| Exit Conditions | The launcher is notified of the probable landing locations and whether it is safe to launch. |
| Special Requirements | The Monte Carlo simulation must simulate the launch correctly and determine probable landing locations accurately. <br> The system must be compatable with the operating system it is being run on. |

3a. As a part of determining the probable landing locations, the system must connect to an external Monte Carlo simulation, written by another team. The system must send the local weather data obtained from NOAA, or entered manually by the user to the Monte Carlo simulation as well as additional information regarding the rocket.

3b. As another part of determining the probable landing locations, the system must be able to receive and store the data that results from the Monte Carlo simulation. This may involve receiving the data directly from the Monte Carlo simulation, or loading the data from a file that the Monte Carlo simulation saves to.

3c. The final part of determining the probable landing locations is displaying the data. The system must display the simulation data visually to the user by showing the user probable landing locations on the map of the local area using a GUI. The displayed information should contain different ranges and confidence intervals.

   
4Go no go function <br>
4A   Define acceptable areas<br>
4B   Turning acceptable areas + tolerance + probabilties into yes/no<br>
4C   Prelaunch check - NI<br>
4D   Launch check - NI<br>

5Relaying rocket information to user interface <-- very important (specifically location, velocity, rotation, not so important)<br>
5A   Showing GPS location on a map<br>
5B   Showing diagram of rocket with rotation and velocity - NI<br>
5C   What to do when you can't contact the rocket<br>
5D   Recording it to a file<br>
5E   Playing back that file virtually - NI<br>

####6 Suggesting launch angle 
The application can determine the correct launch angle for the rocket to follow the desired trajectory. This will take into account environmental factors such as wind speed. This is important to reduce the amount of in flight adjustment required, immproving rocket stability.

This data may be calculated from the mission control software itself, or passed on from the Monte Carlo simulation.

####7 Arm ejection charge
Ejection charges are controlled by the avionics team, with their detonation time determined by the the rocket's flight stage through barometric pressure or time. Upon detonation of a charge, this information should be displayed in the mission control software.

There are a number of ways that mission control can determine whether charges have been detonated. Firing the charges results in a large voltage drop, which when corroberated with a predetermined expected detonation time, changes in pressure and expected change in flight path provides an indication that the ejection charge has been deployed successfully.

If an ejection charge malfunction has been detected through a severe deviation from expected flight path or lack of expected voltage drop, mission control should alert the user of this.


This is typically the longest subsection in the document. List up to fifty use cases (in order of priority for development), and for at least top ten focal use cases, write a short goal statement and use case body (up to seven pages).  Identify the use cases that comprise a minimum viable product.

### 3.3 Usability Requirements

User needs to have a clear idea where the rocket is<br>
Units shouldn't be really niche/hard to understand<br>
Correctly interpreting probability information from monte carlo<br>
Entering local weather data needs to be easy<br>
Works around the world<br>
Needs to work without internet on site<br>
Suggested launch angle can't have huge horizontal velocity<br>
Contact loss to the rocket should be clearly shown to the user<br>

See 9.5.12. for most systems this will be around one page.

> **9.5.12 Usability requirements**<br>
> Define usability (quality in use) requirements. Usability requirements and objectives for the software system include measurable effectiveness, efficiency, and satisfaction criteria in specific contexts of use.

### 3.4 Performance requirements

How often the position of the rocket is updated on the map<br>
No 'go' given on failure probabilities of greater than X%

See 9.5.13. for most systems this will be around one page. Hardware projects also see section 9.4.6.

> **9.5.13 Performance requirements** <br>
> Specify both the static and the dynamic numerical requirements placed on the software or on human interaction with the software as a whole. 
> 
> Static numerical requirements may include the following:
> 
> a) The number of terminals to be supported;  
> b) The number of simultaneous users to be supported;  
> c) Amount and type of information to be handled.
> 
> Static numerical requirements are sometimes identified under a separate section entitled Capacity.
> 
> Dynamic numerical requirements may include, for example, the numbers of transactions and tasks and the amount of data to be processed within certain time periods for both normal and peak workload conditions. The performance requirements should be stated in measurable terms.
> 
>  For example, "_95 % of the transactions shall be processed in less than 1 second._" rather than, "An operator shall not have to wait for the transaction to complete."
> 
> NOTE Numerical limits applied to one specific function are normally specified as part of the processing subparagraph description of that function.


### 3.5 Logical database requirements

Data from the rocket. Position, rotation, possibily speed?

Data from NOAA. Windspeed, barometric pressure, temp, ...

To be decided at a meeting at a later time.

See 9.5.14. for most systems, a focus on d) and e) is appropriate, such as an object-oriented domain analysis. You should provide an overview domain model (e.g.  a UML class diagram of approximately ten classes) and write a brief description of the responsibilities of each class in the model (3 pages).

You should use right tools, preferabley PlantUML, to draw your UML diagrams which can be easily embedded into a Mardown file (PlantUML is also supported by GitLab and Foswiki).

### 3.6 Design constraints

Ask customer what standards and regulations we need to conform to.

see 9.5.15 and 9.5.16. for most systems, this will be around one page.

> 9.5.15 Design constraints<br>
> Specify constraints on the system design imposed by external standards, regulatory requirements, or project limitations.
> 
> 9.5.16 Standards compliance<br>
> Specify the requirements derived from existing standards or regulations, including:
> 
> a) Report format;<br>
> b) Data naming;<br>
> c) Accounting procedures;<br>
> d) Audit tracing.
> 
> For example, this could specify the requirement for software to trace processing activity. Such traces are needed for some applications to meet minimum regulatory or financial standards. An audit trace requirement may, for example, state that all changes to a payroll database shall be recorded in a trace file with before and after values.

### 3.7 Nonfunctional system attributes

Present the systemic (aka nonfunctional) requirements of the product (see ISO/IEC 25010).
List up to twenty systemic requirements / attributes.
Write a short natural language description of the top nonfunctional requirements (approx. five pages).


### 3.8 Physical and Environmental Requirements 

Circumstances in which launch is not a good idea.

For systems with hardware components, identify the physical characteristics of that hardware (9.4.10) and environment conditions in which it must operate (9.4.11).  Depending on the project, this section may be from one page up to 5 pages.

### 3.9 Supporting information

see 9.5.19. 

## 4. Verification

Code style enforcement via plugins, IDE settings
Stuff that we can unit/regression tests<br>
Stuff that we need to live test, such as with Monte Carlo<br>

3 pages outlining how you will verify that the product meets the most important specific requirements. The format of this section should parallel section 3 of your document (see 9.5.18). Wherever possible (especially systemic requirements) you should indicate testable acceptance criteria.

## 5. Development schedule.

### 5.1 Schedule

Meeting to discuss this

Identify dates for key project deliverables: 

1. architectural prototype T1 Week 11/19th June
1. minimum viable product T2 Week 5/14th Aug
2. project termination T2 Week 12/16th Oct
1. further releases

(1 page).

### 5.2 Budget

No purchases required. It's a software project.

### 5.3 Risks 

1 Sudden prolonged absence of team member. Likelihood low, effect high.
2 Sudden temporary absence of team member. Likelihood medium-high, effect low
3 Loss of work due to surges, equipment failues. Likelihood medium, effect low

4 Failure to agree on protocol with the Monte Carlo teams. Likelihood high, effect high
5 Failure to agree on protocol with the avionics teams. Likelihood high, effect high
6 Other teams fail to meet deadline. Likelihood low, effect very high

7 Failure to meet deadlines. Likelihood ?, effect depends
8 Major scope creep. Likelihood medium-low, effect high
9 Bad docs. Likelihood medium high, effect medium-low

10 Physical and Mental Health consequences due to overwork. Likelihood medium, effect medium
11 Computer use related injuries. Likelihood low, effect high



Identify the ten most important project risks to achieving project goals: their type, likelihood, impact, and mitigation strategies (3 pages).

If the project will involve any work outside the ECS laboratories, i.e. off-campus activities, these should be included in the following section.

### 5.4 Health and Safety

Eye strain, carpal tunnel, electrocution

Document here project requirements for Health and Safety. All teams must state in this section:

1. How teams will manage computer-related risks such as Occupational Over Use, Cable management, etc.  

2. Whether project work requires work or testing at any external (off-campus) workplaces/sites. If so, state the team's plans for receiving a Health and Safety induction for the external workplaces/sites. If the team has already received such an induction, state the date it was received. 

Also document in this section any additional discussions with the School Safety Officer regarding Health and Safety risks. Give any further information on relevant health and safety regulations, risks, and mitigations, etc.


#### 5.4.1 Safety Plans

Ask someone about this

Safety Plans may be required for some projects, depending on project requirements. Safety Plan templates are available on the course Health & Safety page. Two questions all teams must answer are:


**Do project requirements involve anything that can cause harm or injury?**  
Examples: building/modifying things with voltages <= 60V, small moving machinery, wearable devices.

If so, you will have to write a separate Safety Plan as part of project requirements, and the Safety Plan must be referenced in this section. For health and safety risks involving harm or injury, you should write a draft of the Safety Plan before contacting the School Safety Officer and Course Coordinator to discuss the Safety Plan and project requirements.

If a safety plan is required, list in this section the date the School Safety officer accepted your Health and Safety plan (if accepted by submission date).

_If the project is purely software and requires no contact risks involving physical harm, then state "Project requirements do not involve risk of death, serious harm, harm or injury." in this section._


## 6. Appendices
### 6.1 Assumptions and dependencies 

Communication with rocket will be over serial
Public weather data from NOAA exists and is accurate
The java VM continues to be supported

One page on assumptions and dependencies (9.5.7).

### 6.2 Acronyms and abbreviations

NOAA = National Oceanic and Atmospheric Administration
API = Application Program Interface

One page glossary _as required_.

## 7. Contributions

A one page statement of contributions, including a list of each member of the group and what they contributed to this document.

---

## Formatting Rules 

 * Write your document using [Markdown](https://gitlab.ecs.vuw.ac.nz/help/user/markdown#gitlab-flavored-markdown-gfm) and ensure you commit your work to your team's GitLab repository.
 * Major sections should be separated by a horizontal rule.


## Assessment  

The goal of a requirements document is the problem you are attempting to solve:  not a first attempt at a solution to that problem. The most important factor in the assessmernt of the document is how will it meet that goal. The document will be assessed for both presentation and content. 

The presentation will be based on how easy it is to read, correct spelling, grammar, punctuation, clear diagrams, and so on.

The content will be assessed according to its clarity, consistency, relevance, critical engagement and a demonstrated understanding of the material in the course. We look for evidence these traits are represented and assess the level of performance against these traits. While being comprehensive and easy to understand, this document must be reasonably concise too. You will be affected negatively by writing a report with too many pages (far more than what has been suggested for each section above).

We aim to evaluate ENGR301 documents and projects as if they were real projects rather than academic exercises &mdash; especially as they are real projects with real clients. The best way to get a good mark in a document is to do the right thing for your project, your client, and your team. We encourage you to raise questions with your tutor or course staff, as soon as possible, so you can incorporate their feedback into your work.

---
