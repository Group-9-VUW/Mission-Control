#Annex F

(informative)

## Architecture Modelinh

### F.1 Introduction

This annex provides information related to the Architecutre Definition and Design Definition Processes in this verion of the standard as they relate to the Architectural Design process in the 2008 version of ISO/IEC/IEEE 15288.

Architecture and design activities in this version are split in two processes to reflect the different practises in the systems engineering community dealing with complex systems. As one example, an architecture might be followed by different designs for different systems in a product line. In this case it is important to perform these two processes in a separate manner. Furthermore, architecture is often done for other reasons than as the basis for design, such as, for example, to drive technology investments, to adjust corportate portifolio of projects, to guide a bid/no-bid decision, etc.

The architecutre of a system can be understood as a set of structured architectural entities and of their relationships, such as functions, function flows, interfaces, resource flow items, information/data elements, physical components, containers, nodes, links, communication resources, etc. These architecutural entities may possess characteristics such as dimensions, environmental resilience, avaliability, robustness, execution efficiency, mission effectiveness, etc. 

### F.2 Viewpoints, views and model kinds used in architecture

The Architectural Definition process uses a variety of models, including the example models listed in the following section. (Traditional system engineering practice classifies some of these models as "logical models" or "physical models", but the taxonmical distinction is unnecessary in the application of this International Standard.) A variety of views are used to represent how the system architecture addresses stakeholder concerns. Views are composed of models. Refer to ISO/IEC/IEEE 42010 for definitions of architecture terms and addtional detail on architecture concepts and models.

### F.3 Logical and Physical Models

### F.3.1 Functional Model

A functional model of the system is a representation of a set of functions that defines the transformations of inputs into outputs performed by the system to achieve its mission or purpose. These functions are determined by how the system is expected to behave when used as intended. Consequently, every system function is associated with an interaction between the system and its environment. Functional, performance, non-function, and constraint requirements are usually analyzed to determine functions and input-output flows. When functions are associated with system elements, the design definition process will need to fetermine if each system element has been sufficiently specified to build or buy it. If the system element must be further resolved in roder to achieve this sufficiency then the functions associated with the system element will also need to further resolvedf and properly associated with the sub elements. Typically there are multiple ways to decompose the functions that contributes to the definition of multiple candidate architectures. 

### F.3.2 Behavioral Model

A behavioral model is an arrangement of functions and interfaces (internal and external) that defines how the system or its elements act under conditions to sustain the operational scenarios, including the execution sequencing and concurrency, the conditions for behavioural change and the performance. A behavioral model can be described with a set of inter-related scenarios. This includes identifying the behavioural elements (modes/states, transitions, trigger events, operational scenarios, etc) through the life cycle.

### F.3.3 Temporal Model

A temporal model of the system is a representation expresses how the time is taken into account in the behaviour the system or its elements that present levels of execution frequency of functions (strategic level, tactical level, operational monitoring level, regulation level, etc) corresponding to levels of decision that enable humans and program logic to monitor and control the system operations. This includes identifying temporal elements (duration, frequency, response time, triggers, timeout, stop conditions, etc) from the operational concept and system requirements.

### F.3.4 Structural Model

A structural model of a system is a representation that shows the arrangement of elements with respect to each other and where necessary shows the interfaces between elements and with external entities. Such a model enables consolidating or identifying the physical interfaces between system elements in a level of the system hierarchy and between levels of the system hierarchy, as well as those with external entities to the concerned system (in its environment/context)

### F.3.5 Mass Model

A mass model of the system is a representation showing the position of physical volumes of system elements or of their parts in the case these are geographically distributed. It could record the expected or actual mass properties to help determine shuch mass properties as centre of gravity and dynamic behaviour of motion. It can also be usde to help allocate total system mass to its elements.

### F.3.6 Layout Model

A layout model of the system is a representation of where the system elements are placed geographically against each other. In model railroading, a layout model is a diorama containing scale track for operating trains. An automobile layout describes where on the vehicle the engine and drive wheels are found. 

### F.3.7 Network Model

A network model defines an arrangement of nodes and links to help understand how resources traverse from one node to another. Resources flowing in a network can be mass, energy, data, people, etc. A network model can be used to determine throughput, latency, congestion points, etc. A network model is sometimes modeled along with a protocol stack to understand how layers in a network interact vertically up and down the stack.

### F.3.8 Other Model Considerations

Stakeholder life cycle concerns such as maintenance, evolution, disposal, potential changes of environment or obsolenscence management, and non-functional requirements, are addressed defining architecture characteristics such as modularity, relative independance, upgradeability, adaptation to serveral environments, level of effectiveness, reliability, robustness, scalability, or resistance to environmental conditions, etc.

Other necessary models could include some of these characteristics or other critical quality characteristics. For example, a reliability model could drive the functional level of the Failure Modes and Effects Analysis (FMEA or FMECA) to help deduce potential architectural mitigations to minimize operational risks (mission loss, safety or security) related to critical concerns and functions.

Determination of while models to use in system definition can be based on examination of stakeholder concerns. The models and their resulting views can be used to express how the system architecture and desgin addresses their concerns and to gain better understanding of their actual needs, wants and expectations.

Furthermore, models may be used in other life cycle processes besides architecture and design definition. Model-Based Systems Engineering (MBSE) is the formalized application of modelling to support system requirements, architecture, design, analysis, verification and validation activities throughout the life cycle.