# Event Master README

## Project Introduction
Event Master is a Java-based JDBC event management system designed to organize and manage events effectively. This system facilitates user interaction through a Swing GUI, allowing for the management of events, users, organizers, and tasks.

## Features
- CRUD operations for events, users, organizers, tasks, and more.
- Dynamic user interface built with Java Swing.
- Robust database integration for persistent data management.
- User role management including guests and organizers.

## Installation Instructions
1. Ensure you have MySQL Workbench installed to create and insert the database information.
2. Follow the provided SQL queries to set up the database structure and insert initial data.

### To Configure Your Database Connection
Please follow these steps:
1. Open the `DatabaseConfig.java` file located in the directory `EventMaster/src/main/java/config/DatabaseConfig.java`.
2. In this file, locate the variables `USERNAME` and `PASSWORD`.
3. Replace the current values of `USERNAME` and `PASSWORD` with your database username and password, respectively.
4. Find the variable `DATABASE_URL` and update it with your database URL.

## Database Setup
Execute the SQL queries in the provided sequence to create the EventMaster database, tables, and insert sample data.

### SQL Queries to Create Tables
```sql
-- Create EventMaster Database
CREATE DATABASE IF NOT EXISTS EventMaster;
USE EventMaster;

-- Create User table
CREATE TABLE IF NOT EXISTS User (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL
);

-- Create Organizer table
CREATE TABLE IF NOT EXISTS Organizer (
    UserID INT PRIMARY KEY,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Event table
CREATE TABLE IF NOT EXISTS Event (
    EventID INT AUTO_INCREMENT PRIMARY KEY,
    EventName VARCHAR(255) NOT NULL,
    OrganizerID INT,
    Date DATE,
    Location VARCHAR(255),
    FOREIGN KEY (OrganizerID) REFERENCES Organizer(UserID)
);

-- Create Guest table
CREATE TABLE IF NOT EXISTS Guest (
    GuestID INT AUTO_INCREMENT PRIMARY KEY,
    UserID INT,
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Guest_rsvp table
CREATE TABLE IF NOT EXISTS Guest_rsvp (
    EventID INT,
    UserID INT,
    RSVPStatus ENUM ('pending', 'confirmed', 'declined'),
    PRIMARY KEY (EventID, UserID),
    FOREIGN KEY (EventID) REFERENCES Event(EventID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);

-- Create Task table
CREATE TABLE IF NOT EXISTS Task (
    TaskID INT AUTO_INCREMENT PRIMARY KEY,
    EventID INT,
    Description TEXT,
    Status ENUM ('not_started', 'in_progress', 'completed'),
    Coordinator INT,
    FOREIGN KEY (EventID) REFERENCES Event(EventID),
    FOREIGN KEY (Coordinator) REFERENCES User(UserID)
);

-- Create Budget table
CREATE TABLE IF NOT EXISTS Budget (
    BudgetID INT AUTO_INCREMENT PRIMARY KEY,
    EventID INT,
    TotalAmount DECIMAL(10,2),
    FOREIGN KEY (EventID) REFERENCES Event(EventID)
);

-- Create Expense table
CREATE TABLE IF NOT EXISTS Expense (
    ExpenseID INT AUTO_INCREMENT PRIMARY KEY,
    BudgetID INT,
    Amount DECIMAL(10, 2),
    Description TEXT,
    Date DATE,
    FOREIGN KEY (BudgetID) REFERENCES Budget(BudgetID)
);

-- Create user_events table
CREATE TABLE IF NOT EXISTS user_events (
    EventID INT,
    UserID INT,
    PRIMARY KEY (EventID, UserID),
    FOREIGN KEY (EventID) REFERENCES Event(EventID),
    FOREIGN KEY (UserID) REFERENCES User(UserID)
);
```

### SQL Queries to Insert Data
```sql
INSERT INTO User (UserID, Username, Email) VALUES 
  (1, 'JohnDoe', 'johndoe@example.com'),
  (2, 'JaneDoe', 'janedoe@example.com'),
  (3, 'MikeSmith', 'mikesmith@example.com'),
  (4, 'SarahJones', 'sarahjones@example.com'),
  (5, 'AliceJohnson', 'alicejohnson@example.com'),
  (6, 'BobBrown', 'bobbrown@example.com'),
  (7, 'CharlieDavis', 'charliedavis@example.com'),
  (8, 'DianaMiller', 'dianamiller@example.com'),
  (9, 'EvanWilson', 'evanwilson@example.com'),
  (10, 'FionaClark', 'fionaclark@example.com');

INSERT INTO Organizer (UserID) VALUES 
  (1),
  (2),
  (3),
  (4);

INSERT INTO Event (EventID, EventName, OrganizerID, Date, Location) VALUES 
  (101, 'Tech Conference', 2, '2024-04-17', 'Convention Center, New York'),
  (102, 'Music Festival', 2, '2024-11-05', 'Central Park, New York'), 
  (103, 'Art Exhibition', 1, '2024-07-06', 'Museum of Modern Art, New York'),
  (104, 'Food Fair', 2, '2024-03-27', 'Brooklyn Food Market, New York'),
  (105, 'Book Reading', 4, '2024-11-28', 'Public Library, Boston'),
  (106, 'Charity Run', 3, '2024-04-26', 'Marathon Finish Line, Chicago'),
  (107, 'Film Screening', 1, '2024-05-19', 'Indie Cinema, Los Angeles'),
  (108, 'Networking Event', 3, '2025-01-11', 'Tech Hub, San Francisco'),
  (109, 'Startup Pitch', 2, '2024-04-19', 'Innovation Center, Seattle'),
  (110, 'Science Fair', 1, '2024-07-28', 'Science Museum, Houston');

INSERT INTO Guest (UserID) VALUES 
  (5),
  (6),
  (7),
  (8);

INSERT INTO Guest_rsvp (EventID, UserID, RSVPStatus) VALUES 
  (101, 8, 'confirmed'),
  (108, 7, 'declined'),
  (104, 6, 'confirmed'),
  (110, 6, 'confirmed'),
  (106, 5, 'confirmed'),
  (105, 7, 'confirmed'),
  (101, 5, 'declined'),
  (103, 5, 'confirmed'),
  (105, 6, 'declined'),
  (103, 6, 'confirmed');

INSERT INTO Task (EventID, Description, Status, Coordinator) VALUES 
  (101, 'Prepare event schedule', 'completed', 6),
  (108, 'Coordinate with vendors', 'not_started', 7),
  (108, 'Set up online registration', 'not_started', 2),
  (102, 'Design event brochures', 'not_started', 8),
  (102, 'Arrange catering', 'completed', 7),
  (105, 'Organize transportation', 'completed', 6),
  (110, 'Hire security personnel', 'completed', 3),
  (102, 'Set up AV equipment', 'completed', 3),
  (102, 'Plan VIP activities', 'not_started', 6),
  (106, 'Conduct post-event survey', 'in_progress', 3);

INSERT INTO Budget (BudgetID, EventID, TotalAmount) VALUES 
  (201, 101, 27592.42),
  (202, 102, 41722.75),
  (203, 103, 37734.19),
  (204, 104, 31074.38),
  (205, 105, 47609.5),
  (206, 106, 48308.18),
  (207, 107, 19887.58),
  (208, 108, 13094.4),
  (209, 109, 33675.31),
  (210, 110, 45704.18);

INSERT INTO Expense (ExpenseID, BudgetID, Amount, Description, Date) VALUES 
  (301, 210, 2799.08, 'Venue rental fee', '2024-10-06'),
  (302, 210, 342.33, 'Catering services', '2024-05-31'),
  (303, 203, 4013.95, 'Audio equipment rental', '2024-02-12'),
  (304, 210, 2374.05, 'Security services', '2024-06-21'),
  (305, 202, 2451.27, 'Printing of brochures', '2024-01-21'),
  (306, 203, 4067.5, 'Promotional materials', '2024-08-17'),
  (307, 205, 996.12, 'Photography services', '2024-03-22'),
  (308, 207, 4155.62, 'Transportation for guests', '2024-04-29'),
  (309, 208, 1854.14, 'Speaker honorarium', '2025-01-22'),
  (310, 201, 221.42, 'Event insurance', '2024-10-26'),
  (311, 210, 591.84, 'Decorations', '2024-03-24'),
  (312, 207, 2470.62, 'Website development', '2025-01-12'),
  (313, 208, 4648.44, 'Online advertising', '2025-01-06'),
  (314, 201, 4886.4, 'Cleaning services', '2024-03-10'),
  (315, 203, 2375.16, 'Staff overtime pay', '2024-06-08'),
  (316, 204, 835.32, 'Conference materials', '2025-01-08'),
  (317, 209, 2199.2, 'Signage', '2024-03-19'),
  (318, 207, 292.5, 'First aid services', '2024-09-05'),
  (319, 207, 2327.44, 'Wi-Fi service', '2024-06-22'),
  (320, 202, 3203.49, 'License and permits', '2024-09-05'),
  (321, 202, 4911.69, 'Gifts for speakers', '2024-07-08'),
  (322, 208, 4831.21, 'Attendee badges', '2024-08-08'),
  (323, 204, 2243.32, 'Mobile app development', '2024-02-23'),
  (324, 207, 944.5, 'Live streaming services', '2024-01-28'),
  (325, 208, 4622.54, 'Post-event survey tools', '2024-02-03');

INSERT INTO user_events (EventID, UserID) VALUES 
  (103, 8),
  (103, 2),
  (108, 3),
  (105, 2),
  (110, 5),
  (107, 6),
  (102, 3),
  (110, 2),
  (110, 10),
  (105, 6);
```

## Running the Application in IntelliJ IDEA Community Edition 2023.3.2
1. Unzip the project file.
2. Open IntelliJ IDEA and press the "Open" button.
3. Navigate to the EventMaster folder and press "OK".
4. IntelliJ IDEA will build the project.
5. Press the "Project" folder button at the top left.
6. Navigate to `Main.java` located at `EventMaster/src/main/java/Main.java`.
7. Press the green triangular "Run" button at the top right.

## Running the Application from the Command Line (Windows)
1. Start from the root folder called EventMaster.
2. Compile the code using the command: `javac -cp ".\mysql.jar;.\src\main\java" .\src\main\java\Main.java`
3. Run the program using the command: `java -cp ".\mysql.jar;.\src\main\java" Main`

## To Modify or Remove Entries from Tables
1. Select the row you wish to edit or remove within the table.
2. Click on either "Update" or "Delete" as per your requirement.
3. A confirmation dialog will appear to verify your action. Confirm your decision to proceed with the update or deletion.
