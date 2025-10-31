# Student-Management-System-DSA

**Project Title**
Student Management System: A Desktop Application for Efficient Data Management Using Linked Lists

**Problem Statement**
Managing student data in educational institutions often relies on manual processes or complex database systems, which can be inefficient, prone to errors, and lack intuitive interfaces for real-time interaction. Traditional methods do not provide easy visualization, search, or export options, making it challenging for administrators, teachers, and students to handle records dynamically. This project addresses the need for a lightweight, interactive tool to manage student data using data structures, offering a user-friendly GUI and multi-format export without external databases.

**Objective of the Project**
The main objective of this project is to develop a user-friendly and efficient desktop application that manages student data using a custom linked list and provides clear visualizations through a GUI. Specific Goals:

Implement CRUD operations (Create, Read, Update, Delete) using a custom linked list.
Build an intuitive GUI for data interaction, including search and display functionalities.
Enable multi-format data export for sharing and backup.
Secure access through a login system.
Demonstrate DSA concepts in a real-world application.

**Proposed Solution**
The proposed solution is a standalone Java application that integrates:

A custom singly linked list for data storage and manipulation.
A Java Swing GUI for user interaction, including forms, tables, and buttons.
File handling for exporting data in multiple formats (CSV, Excel, Word, PDF, TXT). The system allows users to add students, search by ID, update details, delete records, and view/export data visually through a responsive table interface.

**Uniqueness of the Project**
Custom Data Structure: Uses a manually implemented linked list instead of built-in collections or databases, emphasizing DSA principles.
Multi-Format Export: Supports exporting to five formats (CSV, Excel, Word, PDF, TXT) with a single button, unlike basic apps.
Solo Development: Entirely built by one developer, showcasing personal coding skills and project management.
GUI Focus: Modern, responsive Swing interface with icons and custom components, making it visually appealing.
Educational Value: Designed for learning, with clear code structure and comments for DSA students

**Technologies Used**
Programming Language: Java
IDE: Visual Studio Code (VS Code)
Data Structure: Custom Singly Linked List
GUI Framework: Java Swing
File Handling: Java IO for export (with external libraries for advanced formats)

**External Libraries:**
Apache POI (for Excel and Word export)
iText (for PDF export)
Operating System: Windows 10

**Features Implemented**
 Secure login with username/password authentication
 CRUD operations: Add, Update, Delete, and Search students
 Dynamic display of all student records in a JTable
 Multi-format export (CSV, Excel, Word, PDF, TXT) via file chooser
 Data validation (e.g., duplicate ID prevention)
 Responsive GUI with icons and modern styling
 Error handling and user feedback via dialogs
 
**Project Setup**

**Step 1: Clone Repository**
https://github.com/zahidali-dev/Student-Management-System-DSA.git

**Step 2: Add External Libraries**
Download the following JAR files:
Apache POI: poi-5.2.4.jar and poi-ooxml-5.2.4.jar from poi.apache.org/download.html
iText: itextpdf-5.5.13.3.jar from itextpdf.com/products/itext-5-legacy
In VS Code, add these JARs to your project classpath (right-click project > "Add Folder to Java Source Path" or configure in .vscode/settings.json).

**Step 3: Compile the Project**
Open the project in VS Code.
Press Ctrl+Shift+P, type "Java: Compile Workspace", and select it to compile all Java files.

**Step 4: Run the Application**
In VS Code's integrated terminal, run:
bash

**java Main**
The login dialog will appear first. Use username: admin, password: 2645.
Step 5: Access Application
The app runs as a desktop window. Interact with the GUI to manage student data.
Example Operations
Operation | Description --- | --- Login | Authenticate with username and password Add Student | Input details and add to linked list Update Student | Modify existing student data by ID Delete Student | Remove student record by ID Search Student | Filter and display by ID Display All | Show all records in table Export Data | Choose format and save file

**Challenges Faced During Development**
Linked List Implementation: Ensuring correct insertion, deletion, and traversal without built-in methods.
Solved by thorough testing and debugging each method.
GUI Layout Issues: Aligning components in Swing without overlapping.
Solved by using GridBagLayout and testing on different screen sizes.
Export Library Integration: Configuring Apache POI and iText for multi-format export.
Solved by adding JARs to classpath and handling exceptions.
Main Method Errors: "Main method not found" due to incomplete code.
Solved by ensuring the main method is properly defined in Main.java.
PowerShell Console Issues: PSReadLine errors when running from external terminals.
Solved by using VS Code's integrated terminal instead.
**Expected Outcomes**
A fully functional Student Management System for efficient data handling.
Improved understanding of DSA concepts through practical implementation.
A portfolio-ready project demonstrating Java and GUI skills.

**Future Improvements**
Integrate a database (e.g., MySQL) for persistent storage.
Add user roles (e.g., admin vs. student view).
Implement sorting and advanced search filters.
Deploy as a JAR file or web app using JavaFX.

**Developer Information**
Developed by: Zahid Ali
Roll No: K24SW012
Submitted to: ENGR: Asmatullah Zubair Soomro
Course: Data Structure and Algorithm (DSA)
Email: azmeeralimahar@gmail.com
Contact:+923083253683

**License**
This project is licensed under the MIT License.
You are free to use, modify, and distribute with proper credit.

**Conclusion**
The Student Management System (DSA Project) shows how a simple linked list can be turned into a real working application.
It combines DSA logic, Java Swing GUI, and file handling to make data management simple and visual.
This project reflects both technical learning and creativity — a perfect example of how theory can become a working software tool.
