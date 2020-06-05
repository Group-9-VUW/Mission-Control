# Annex F

(informative)

## Software system architecture modelling

In this document, architecture and design activities are described as separate processes. Several iterations of the Architecture Definition, Design Definition, and Implementation processes can be involved when evolving a software element’s architecture e.g., to determine whether an entity or function will be realized through integration of existing software, adaptive reuse, or newly constructed software. In the systems and software engineering communities dealing with complex systems, architecture can be followed by different designs for different systems in different product lines. In this case it is important to perform these two processes in a separate manner. Furthermore, architecture is often done for other reasons than as the immediate basis for design, such as to drive technology investments, to achieve consistency or reduce complexity in an organization’s product line or portfolio of projects, or to guide acquirer‐supplier decisions.

The architecture of a software system can be understood as a set of structured architectural entities and their relationships, chosen to achieve characteristics such as interoperability, scalability, environmental resilience, encapsulation, availability, affordability, robustness, execution efficiency, or mission effectiveness (fitness for use). Software system architecture deals with relationships among a variety of entities, such as scenarios, functions, function flows, interfaces, resource flow items, information or data elements, objects, physical components and environments, containers, nodes, links, communication resources, constraints, equations and parametric models.

This Annex describes some of the models (model kinds) that are used in creating and evaluating the architecture of software systems.

*NOTE ISO/IEC/IEEE 15288:2015, Annex F describes how models and views are applied to the architecture of systems in general, and has additional information relating to views and modelling of the architecture of physical products, such as mass models and layout models.*

### F.2 Views, models and model kinds used in software system architecture

The Architectural Definition process uses a variety of models for software systems, including the example models listed in the following section. Model kinds specify the languages, notations, conventions, modelling techniques, analytical methods or other operations to be used on models of that kind. (Traditional system engineering practice classifies some of these models as “logical models” or “physical models”, but the taxonomical distinction is unnecessary in the application of this document.) A variety of views are used to represent how the system architecture addresses stakeholder concerns. Views are composed of models. For example, a logical view of software can represent business processes in its functions; a process view can represent the events and transformations occurring within different states of the software and can include concurrency and timing concerns; a structural view represents the different system components, which can be associated with physical or virtual system elements, an information view represents the relationships between data elements contained in and transformed by the software.

Refer to ISO/IEC/IEEE 42010 for definitions of architecture terms and additional detail on architecture concepts and models.

### F.2.1 Functional model

A functional model of the system is a representation of a set of functions that defines the transformations of inputs into outputs performed by the system to achieve its mission or purpose. These functions are determined by how the system is expected to behave when used as intended. Consequently, every system function is associated with an interaction between the system and its environment. Functional, performance, non‐functional, and constraint requirements are usually analyzed to determine functions and input‐output flows. When functions are associated with system elements, the design definition process will need to determine if each system/software element has been sufficiently specified to build or buy it. If the system element is further resolved in order to achieve this sufficiency, then the functions associated with the system element are also further resolved and properly associated with the sub‐elements. Typically, there are multiple ways to decompose the functions that contribute to the definition of multiple candidate architectures.

### F.2.2 Static model

A static model describes the structure of a software system. In object‐oriented programming, it is represented through a set of objects (classes) and their relationships (inheritance, association, and dependency), depicted as nodes and links.

### F.2.3 Data model

A data model (semantic or information model) represents the data elements and their relationships and properties (attributes) that the software system will handle. Logical data models use schema to reflect structural relationships between data entities which can be implemented in databases. Data models reflect different types of data (text, graphics, geographic data, images, general objects) and their use in the system functions (frequency of change, data volume, use in searching) as well as the logical relationships among data elements. Data models are applied in the development of interfaces and software services, data analysis, and data reporting. Physical data models reflect the schema for storage and retrieval of data records.

### F.2.4 Behavioral model

A behavioral model (dynamic model) is an arrangement of functions and interfaces (internal and external) that defines how the system or its elements act under conditions to sustain the operational scenarios, including the execution sequencing, synchronization, and concurrency, the conditions for behavioral change and the performance. Behavioral models are applicable to software control systems. A behavioral model can be described with a set of interrelated scenarios. This includes identifying the behavioral elements (e.g., modes/states, transitions, trigger events, and operational scenarios) through the life cycle.

### F.2.5 Temporal model

A temporal model of the system is a representation that expresses how the time is taken into account in the behavior the system or its elements that presents levels of execution frequency of functions (e.g., strategic level, tactical level, operational monitoring level, regulation level) corresponding to levels of decision that enable humans and program logic to monitor and control the system operations. This includes identifying temporal elements (e.g., duration, frequency, response time, triggers, timeout, stop conditions), from the operational concept and system requirements.

### F.2.6 Structural model

A structural model of the system is a representation that shows the arrangement of elements with respect to each other and where necessary shows the interfaces between elements and with external entities. Such a model enables consolidating or identifying physical interfaces between system elements in a level of the system hierarchy and between levels of the system hierarchy, as well as those with external entities to the concerned system (in its environment/context). Structural models can be hierarchical decompositions or object‐oriented.

### F.2.7 Network model

A network model defines an arrangement of nodes and links to help understand how resources (e.g., information and people) traverse from one node to another. A network model can be used to determine constraints such as throughput, latency, and congestion points. A network model is sometimes modelled along with a protocol stack to understand how layers in a network interact vertically up and down the stack.

### F.3 Other model considerations

Stakeholder life cycle concerns, such as maintenance, evolution, disposal, potential changes of environment, obsolescence management, and other non‐functional requirements, are addressed by defining architectural characteristics such as modularity, relative independence, scalability, upgradability, adaptation to several environments, level of effectiveness, reliability, robustness, and resilience. Other necessary models can include some of these characteristics or other critical quality characteristics. For example, a software assurance case, regarded as a model, can help in deducing potential architectural mitigations to minimize operational risks (mission loss due to exploited security vulnerabilities) related to critical concerns and functions.

Determination of which models to use in system definition can be based on examination of stakeholder concerns. The models and the resulting views can be used to express how the system architecture and design addresses their concerns and to gain better understanding of their actual needs, wants and expectations.

Furthermore, models can be used in other life cycle processes besides architecture and design definition. Model‐ Based Systems Engineering (MBSE) is the formalized application of modelling to support system requirements, architecture, design, analysis, and verification and validation activities throughout the life cycle.

*NOTE A verification and validation model defines representations of test information, which can support the verification of the architecture. Verification and validation models can generate test analyzes, data, cases, and other information.*