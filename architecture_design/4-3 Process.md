### 4.3 Process
<br>


The processes of the mission control software can perhaps best be described by the following activity diagram
<br><br>
![Activity Diagram](activity_diagram.png)
<br><br>
As discussed in 4.1 Logical, the program can be broken down into three primary superstates: planning, pre-launch and post-launch. The activity diagram can be broken down in a similar fashion. All activities that happen after the program confirms that the user is not at the launch site can be considered as planning activities. That is, activities that occur when the user is deciding on a launch site before the user is at the launch site. Likewise, all activities that occur after the user is at the launch site can be considered pre-launch activities with activities that occur after the launch signal is sent to the rocket considered as launch or post-launch activities. As can be seen from the activity diagram, the planning processes are completely separate from the pre-launch and post-launch processes. This is quite significant as it will alter the team's strategy when it comes to implementing the design of the mission control software as Java code.
