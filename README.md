# Event-driven programming
1. Panel and Events: uses JPanel implements ActionListener which triggers timer based events
2. Sim objects: class definitions of objects used in Simulation
3. Simulation: logic of application, connection between model and view

## Map of provided Code to AP CSA requirements
[Computer Science A Course Option description pages 17, 18, 19](https://apcentral.collegeboard.org/pdf/ap-computer-science-a-course-and-exam-description.pdf?course=ap-computer-science-a)

| Concept or Code | MVC idea |
| ------------- | ----------- |
|  Event-driven programing |  Control.java define paintCB method which allows person objects to be managed, change states, and simulation events to be recorded |
|  Writing Classes - Abstract Interfaces |  Community.java completes Panel.java's abstract "paint" interface |
|  Writing Classes - Inheritance | Wall.java and Resident.java extend RectangleCollider as in simulation collision is based on rectangular coordinates |
|  JFrame, UI classes | SettingsUI shows usage of a JFrame to control settings for simulation.  Notice usage of defaults and of external class reference of public settings from Control.java |
|  IntelliJ, GitHub | Using real world development and version control tools |
[Event Programming highlights](https://padlet.com/jmortensen7/csavirussimu)