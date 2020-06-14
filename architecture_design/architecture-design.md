# ENGR 301: Architectural Design and Proof-of-Concept

## Proof-of-Concept

The aim of an architectural proof-of-concept (spike or walking skeleton) is to demonstrate the technical feasibility of your chosen architecture, to mitigate technical and project risks, and to plan and validate your technical and team processes (e.g., build systems, story breakdown, Kanban boards, acceptance testing, deployment).

A walking skeleton is an initial technical attempt that will form the architectural foundation of your product. Since a walking skeleton is expected to be carried into your product, it must be completed to the quality standards expected for your final product. A walking skeleton should demonstrate all the technologies your program will rely on "end-to-end" &mdash; from the user interface down to the hardware.

In the context of ENGR 301, a walking skeleton does not need to deliver any business value to your project: the aim is technical validation and risk mitigation.


## Document

The aim of the architectural design document is to describe the architecture and high-level design of the system your group is to build, to identify any critical technical issues with your design, and to explain how you have addressed the highest rated technical and architectural risks. The architecture document should also demonstrate your understanding of architectural techniques and architectural quality, using tools and associated notations as necessary to communicate the architecture precisely, unambiguously and clearly in a written technical document.

Page specifications below are *limits not targets* and refer to the pages in the PDF generated from the markdown. Because the size of your document is necessarily limited, you should ensure that you focus your efforts on those architectural concerns that are most important to completing a successful system: if sections are at their page limit, indicate how many items would be expected in a complete specification.

The ENGR 301 project architecture design document should be based on the standard ISO/IEC/IEEE 42010:2011(E) _Systems and software engineering &mdash; Architecture description_, plus appropriate sections from ISO/IEC/IEEE 29148:2018(E) _Systems and software engineering &mdash; Life cycle processes &mdash; Requirements engineering_; ISO/IEC/IEEE 15289:2017 _Systems and software engineering &mdash; Content of life-cycle information items (documentation)_; ISO/IEC/IEEE 15288:2015 _Systems and software engineering &mdash; System life-cycle processes_; ISO/IEC/IEEE 12207:2017 _Systems and software engineering &mdash; Software life cycle processes_ and ISO 25010 SQuaRE; with notations from ISO/ISE 19501 (UML). In particular, Annex F of ISO/IEC/IEEE 15288 and Annex F of ISO/IEC/IEEE 12207. These standards are available through the Victoria University Library subscription to the [IEEE Xplore Digital Library](https://ieeexplore.ieee.org/) (e.g., by visiting IEEE Xplore from a computer connected to the University network).

The document should contain the sections listed below, and conform to the formatting rules listed at the end of this brief.

All team members are expected to contribute equally to the document and list their contributions in the last section of the document (please make sure that your continued contribution to this document can be traced in GitLab). You should work on your document in your team's GitLab repository in a directory called "M2_Architecture". If more than one team member has contributed to a particular commit, all those team member IDs should be included in the first line of the git commit message. ``git blame``, ``git diff``, file histories, etc. will be tools used to assess individual contributions, so everyone is encouraged to contribute individually (your contribution should be made to many sections of the document, rather than focusing on just a single section), commit early and commit often.

---

# ENGR 301 Project *NN* Architectural Design and Proof-of-Concept

**Authors:** a comma-separated list of the names of each member of the team.

## 1. Introduction

One page overall introduction including sections 1.1 and 1.2 (ISO/IEC/IEEE 42010:2011(E) clause 5.2)

### Client

Identify the client and their contact details.

### 1.1 Purpose

One sentence describing the purpose of the system.

### 1.2 Scope

One paragraph describing the scope of the system.

### 1.3 Changes to requirements

If the requirement have changed significantly since the requirements document, outline the changes here. Changes must be justified and supported by evidences, i.e., they must be substantiated. (max one page, only if required)

## 2. References

References to other documents or standards. Follow the IEEE Citation Reference scheme, available from the [IEEE website](https://ieee-dataport.org/sites/default/files/analysis/27/IEEE%20Citation%20Guidelines.pdf) (PDF; 20 KB). (1 page, longer if required)

## 3. Architecture

Describe your system's architecture according to ISO/IEC/IEEE 42010:2011(E), ISO/IEC/IEEE 12207, ISO/IEC/IEEE 15289 and ISO/IEC/IEEE 15288.

Note in particular the note to clause 5 of 42010:

_"The verb include when used in Clause 5 indicates that either the information is present in the architecture description or reference to that information is provided therein."_

This means that you should refer to information (e.g. risks, requirements, models) in this or other documents rather than repeat information.

### 3.1 Stakeholders

See ISO/IEC/IEEE 42010 clause 5.3 and ISO/IEC/IEEE 12207 clause 6.4.4.3(2).

List stakeholders

- Users: Hobby rocket community<br>
- Developers: Us
- Owners: Andre
- Maintainers: Us and Community



For most systems this will be about 2 pages, including a table mapping concerns to stakeholder.

### 3.2 Architectural Viewpoints
(1 page, 42010 5.4) 

Identify the architectural viewpoints you will use to present your system's architecture. Write one sentence to outline each viewpoint. Show which viewpoint frames which architectural concern.

### 4. Architectural Views

(5 sub-sections of 2 pages each sub-section, per 42010, 5.5, 5.6, with reference to Annex F of both 12207 and 15288) 

Describe your system's architecture in a series of architectural views, each view corresponding to one viewpoint.

You should include views from the following viewpoints (from Kruchten's 4+1 model):

 * Logical
 * Development
 * Process
 * Physical 
 * Scenarios - present scenarios illustrating how two of your most important use cases are supported by your architecture

As appropriate you should include the following viewpoints:

 * Circuit Architecture
 * Hardware Architecture

Each architectural view should include at least one architectural model. If architectural models are shared across views, refer back to the first occurrence of that model in your document, rather than including a separate section for the architectural models.

### 4.1 Logical
...

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

### 4.3 Process
...

### 4.4 Physical 
...

### 4.5 Scenarios
...

## 5. Development Schedule

_For each subsection, make clear what (if anything) has changed from the requirements document._ If unchanged, these sections should be copied over from the requirements document, not simply cross-referenced.

Schedules must be justified and supported by evidences; they must be either direct client requirements or direct consequences of client requirements. If the requirements document did not contain justifications or supporting evidences, then both must be provided here.

### 5.1 Schedule

Identify dates for key project deliverables:

1. prototype(s).
1. first deployment to the client.
1. further improvements required or specified by the client.

(1 page).

### 5.2 Budget and Procurement

#### 5.2.1 Budget

Present a budget for the project (as a table), showing the amount of expenditure the project requires and the date(s) on which it will be incurred. Substantiate each budget item by reference to fulfilment of project goals (one paragraph per item).

(1 page). 

#### 5.2.2 Procurement

| Good/Service  | Procurement Method | Fulfilment of Project Goals |
| ------        | ------ | ------ |
| GitLab        | GitLab is a lifecycle tool and repository manager and was procured from the School of Engineering and Computer Science. | GitLab was provided to our team from the School. Using GitLab hugely increases the simplicity of collaboration and version management. Additionally, GitLab allows for milestones and epics and allows for pipelines to be added for continuous integration and continuous deployment (CI/CD). GitLab allows the team to create issues for different areas of the project and assign team members to each issue. Using GitLab allows for the project files to be well organised and maintained and allows for planning and discussion to occur before code is written or commits are made. This results in generally higher quality, more understandable work in both reports and code. |
| Eclipse       | Eclipse is an integrated development environment (IDE) and was procured from [here.](https://www.eclipse.org/) | Several, but not all, team members have experience using Intellij, while all team member's have experience using Eclipse. We chose, as a group, to have all members program the mission control software using the same IDE. Since all team members have experience with Eclipse, we chose Eclipse as our IDE. All team members using the same IDE allows for consistent project files across all member's machines. Having consistent project files speeds up development and integration and increases the likelihood of the team successfully completing the project. |
| Maven         | Apache Maven is a software project management and comprehension tool and was procured from [here.](https://maven.apache.org/) | Using Maven allows for simple library management where library dependencies are declared in a project object model (POM) file. The libraries are located at a central Maven repository and are downloaded when the project is run on the command line using Maven commands (e.g. mvn clean install). Using a Maven project instead of a standard Java project in Eclipse allows for additional libraries to be added and used simply and will result in all team members having the same libraries available to them. This will reduce library conflicts and speed up development, resulting in more consistent project files and a higher likelihood of successful completion. |
| Google Checks | Google Checks is a checkstyle tool used to enforce a consistent coding standard across a project. Google Checks is procured as an option through Eclipse, which will be activated. | Using Google Checks (or any checkstyle tool for that matter) allows for consistency in code style across code written by all team members. Consistency is key when it comes to programming style as consistency and naming conventions are some of the primary parts of the understandability of code. As such, using Google Checks should allow our team to produce code that is consistent in style and is easily understandable. |


### 5.3 Risks 

*Identify the ten most important project risks: their type, likelihood, impact, and mitigation strategies (3 pages).*

#### Sudden prolonged absence of a team member.
##### Likelihood: Low Effect: High.

The sudden prolonged absense of a team member is a schedule risk that can cause the project to run overtime. It is caused when a team member disappears for a long period without explanation. An example is if a team member is in a crash and is in a coma.

The impact of a sudden prolonged absense of a team member is that there is one less person working on the project. This means that that member's work would have to be split between the other team members and the project will take more time and effort to complete. 

To avoid the risks of a sudden prolonged absence, each team member will strive to:
- Offer an explanation if they are going to be absent.
- Add comments and notes into their work so it is easier for others to pick up.

To avoid the risks of a sudden prolonged absence of a team member, the team will strive to:
- Make sure each member knows about every different part of the project so they can start working on it.
- Have regular meetings with compulsory attendance to quickly notice a team member's absence.

#### Sudden temporary absence of a team member.
##### Likelihood: Medium/High Effect: Low

The sudden temporary absence of a team member is a schedule risk that could cause decisions to be delayed. It is caused when a team member fails to remember a meeting, and is contactable during the meeting. An example is when a team member schedules their work during the meeting, thus is busy and away from their device.

The impact of a sudden temporary absence of a team member is that a decision isn't able to be made. This will halt progress on the project, potentially pushing it back past the deadline. 

To avoid the risks of a sudden temporary absence, each team member will strive to:
- Check the communication channel at least once a day.
- Avoid booking commitments during the lab times.
- Be available on the communcation channel during lab times.

To avoid the risks of a sudden temporary absence of a team member, the team will strive to:
- Have regular meetings at the same time every week.
- Notify team members about meetings outside of normal scheduled meetings at least a day before the meeting.
- Keep important decisions to the normal scheduled meetings.

#### Loss of work due to technological problems.
##### Likelihood: Medium Effect: Low

Loss of work due to technological problems is a scheduling risk that could lead to the project not being finished on time. It is caused when a technological problem causes the file that was being worked on to be closed without saving the work. An example is when a blackout causes the desktop to shutdown before the file was saved. 

The impact of losing work due to technological problems is that work will have to be repeated. This might result in the final code being poorly written as the developer isn't thinking as hard about it. It will result in more work and time taken on the task. If the work lost is a lot, than it could delay the finishing of the project. 

To avoid losing work due to technological problems, each team member will strive to:
- Save their work regularly to keep an almost up-to-date version of their work.
- Work on all their work seriously, even if they are repeating it.
- Commit regularly to GitLab to protect against their device dying.

To avoid losing work due to technological problems, the team will strive to:
- Split tasks into smaller units so work done each 'task' is less.

#### Failure to agree on protocol with the Monte Carlo teams. 
##### Likelihood: High      Effect: High

Failure to agree on protocol with the Monte Carlo teams is a performance risk that would stop the project from integrating with them. It would be caused by a disagreement with all the Monte Carlo teams over how the two projects will communicate. An example is this project not wanting to send a piece of information the Monte Carlo team were expecting.

The impact of failing to agree on protocol with the Monte Carlo teams is being unable to simulate the rocket's launch. This would lead to the system being unable to determine if a launch is safe.

To avoid failing to agree on protocol with the Monte Carlo teams, the team will strive to:
- Be willing to make compromises on agreements. 
- Outline what the system can actually do.
- Don't accept impossible terms. 

#### Failure to agree on protocol with the avionics teams. 
##### Likelihood: High      Effect: High

Failure to agree on protocol with the avionics teams is a performance risk that would stop the project from integrating with them. It would be caused by a disagreement with all the avionics teams over how the two projects will communicate. An example is the avionics team wanting to send information that this project doesn't want to deal with.

The impact of failing to agree on protocol with the avionics teams is being unable to connect to the rocket. This would lead to the user not being able to control the rocket, or see information about the rocket's position. 

To avoid failing to agree on protocol with the avionics teams, the team will strive to:
- Be willing to make compromises on agreements.
- Outline what the system can actually do.
- Don't accept impossible terms.

#### Other teams fail to meet deadline. 
##### Likelihood: Low       Effect: High

The other teams failing to meet deadlines is a schedule risk that would halt the testing or development of the project. This risk would only happen if all of the six teams working on the Monte Carlo/avionics project are all behind schedule. 

The impact of the other teams failing to meet deadlines is that the project deadlines will have to be pushed back to accomondate them. This will cause the project to be completed later than expected, and the team will be waiting around for it to be completed. 

To avoid the other teams failing to meet deadlines, the team will strive to:
- Clearly express when a component from another team is needed.
- Check up regularly on the progress of the component.

#### Failure to meet deadlines. <br>
##### Likelihood: Medium     Effect: Low

Failure to meet deadlines is a schedule risk that can cause the project to run overtime. It is caused when some factor prevented the individual from completing their work. This could be due to having too much work on at a certain time, or a sudden external event like a blackout or injury. An example is when a team member has to fit in the project work while also studying for a test.

The impact of a deadline being missed is that the other parts of the project will be behind schedule. These effects could make the project be completed later than expected or cause extra pressure on the the rest of the team to fix the individual's mistake.

To avoid failing to meet deadlines, each team member will strive to:
- Only take on the amount of work they can physically complete within the time limit.
- Be willing to help others when they are struggling with a task.
- Be willing to ask others for help when they are struggling with a task.
- Be honest about the amount of other work they have to complete.
- Share if they have other circumstances preventing them from working early.

To avoid a team member failing to meet deadlines, the team will strive to:
- Plan work around other obligations or deadlines. 
- Have slack in the schedule so delays don't throw the project off track.

#### Major scope creep.<br>
##### Likelihood: Medium/Low    Effect: High

Major scope creep is a cost risk that can cause the project to run overtime. It is caused when extra tasks are added to the project scope during the project lifetime, or when nominally simple functions are continually over-created. An example is when another requirement is added to the project specification.

The impacts of major scope creep are extensions to the project lifetime or extra work to the team to fit into the same lifetime.

To avoid major scope creep, the team will strive to:
- Carefully define the project specifications so there is no room for confusion. 
- Focus on finishing the project requirements before adding extra functions.
- Review the code regularly to spot wasted code early.
- Communicate about tasks.

#### Bad documentation.<br>
##### Likelihood: Medium/High   Effect: Medium/Low

Bad Docmentation is a performance risk that can cause bugs in the program. It is caused when an individual fails to properly document their methods or classes. An example is when a developer fails to properly add comments and JavaDocs to their method.

The impact of bad documentation is that the method is incorrectly used or that the code is confusing to developers who are editing it. These effects could lead to bugs in the program which will increase the work for developers and testers and increase the time/cost of maintaining the program or adding new features.

To avoid bad documentation, each team member will strive to:
- Write a JavaDoc comment for every method.
- Add important comments to their code so that it can be understood by other team mates.
- Make sure that documentation can be understood by outsiders. 

#### Injuries due to overwork. <br>
##### Likelihood: Medium     Effect: Medium

Overwork is a physical risk that can, in unlikely circumstances, cause death. It is caused when an individual works for an extended amount of time without decent rest or food. An example is when a student pulls an all-nighter for multiple consecutive days to finish an assignment or study for a test. 

The more likely impacts of overwork, other than death, are sleeping disorders, anxiety, and a weakened immune system. These effects will reduce the work quality of the individual and potentially stop them from working on the project.

To avoid becoming overworked, each team member will strive to:
- Only take on the amount of work they can physically complete within the time limit.
- Be willing to help others when they are struggling with a task.
- Be willing to ask others for help when they are struggling with a task.
- Be honest about the other work they have to complete.

To avoid a team member becoming overworked, the team will strive to:
- Share the work between the team so no one's doing all the work.
- Be flexible with deadlines.
- Work around deadlines for other courses.

#### Computer-use related injuries.<br>
##### Likelihood: Low        Effect: High

Computer-use injuries are a physical risk that can lead to the individual not being able to use the computer. They are caused by continued use of a computer while using poor posture or repeating a similar action for a long period of time. Poor posture could be slouching, sitting straight upright, typing with a positive slope, looking down at the monitor or looking up at the monitor. 

The impacts often noticed of poor computer-use are back/neck pain, headaches, and arm pain. These effects could make the individual uncomfortable using the computer, thus reducing their speed or output, or making it too painful to use the computer at all.

To avoid a computer-use injury, each team member will strive to:
- Take regular breaks away from the computer during work to allow the body to stretch and relax.
- Stop working if something starts hurting and allow time for it to return to normal. If it doesn't, they should see a doctor.

To avoid a team member getting a computer-use injury, the team will strive to:
- Reduce length of online meetings.

### 5.4 Health and Safety

*Document here project requirements for Health and Safety.*

1. How the team will manage computer-related risks
    - Some risks were discussed in great detail in the section above (overwork, computer-use injuries, work loss due to technological problems).
    - Cable Management
        - Each team member will strive to keep their work station free of loose wires.
    - Security
        - No sensitive or personal data is stored or used by the program.
        - GitLab.ecs prevents people outside the university from accessing the online work.
    - Other risks
        - Up to team members to identify and mitigate depending on their unique situation.
        - Team members can ask for advice or help from other team members or the School Safety Officer.
        - Team members should share risks they've found with the team.

2. Whether the project requires any work or testing at an external (off-campus) site.
   - The project is a control for a rocket, but can be tested at an internal site using simulations. Thus it won't require off-campus testing.
   - Note: it will require testing under field conditions (no wifi) but doesn't need to be in the field.


#### 5.4.1 Safety Plans

Safety Plans may be required for some projects, depending on project requirements.


## 6. Appendices

### 6.1 Assumptions and dependencies 

One page on assumptions and dependencies (9.5.7) 

### 6.2 Acronyms and abbreviations

One page glossary as required 

## 7. Contributions

An one page statement of contributions, including a list of each member of the group and what they contributed to this document.

---

## Formatting Rules 

 * Write your document using [Markdown](https://gitlab.ecs.vuw.ac.nz/help/user/markdown#gitlab-flavored-markdown-gfm) in your team's GitLab repository.
 * Major sections should be separated by a horizontal rule.


## Assessment 

This document will be weighted at 20% on the architectural proof-of-concept(s), and 80% on the architecture design.

The proof-of-concept will be assessed for coverage (does it demonstrate all the technologies needed to build your project?) and quality (with an emphasis on simplicity, modularity, and modifiability).

The document will be assessed by considering both presentation and content. Group and individual group members will be assessed by identical criteria, the group mark for the finished PDF and the individual mark on the contributions visible through `git blame`, `git diff`, file histories, etc. 

The presentation will be based on how easy it is to read, correct spelling, grammar, punctuation, clear diagrams, and so on.

The content will be assessed according to its clarity, consistency, relevance, critical engagement and a demonstrated understanding of the material in the course. We look for evidence these traits are represented and assess the level of performance against these traits. Inspection of the GitLab Group is the essential form of assessing this document. While being comprehensive and easy to understand, this document must be reasonably concise too. You will be affected negatively by writing a report with too many pages (far more than what has been suggested for each section above).

---
