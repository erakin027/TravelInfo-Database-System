# TravelInfo — Bus Travel Information System

A console-based **Java + MySQL** application that displays bus schedules, supports filtering and sorting, and provides admin tools to look up operator contact info and track operator performance.

## Database
The project uses a MySQL database named **travelinfo**, consisting of four main tables:

'operator' - Stores operator details such as name, email, and contact number
'bus'      - Contains bus details mapped to operators
'route'    - Defines start and destination cities
'schedule' - Stores price, timing, and maps buses to routes

The relations use foreign keys to maintain consistency between operators, buses, routes, and schedules.

## Features
### User
- View all buses from a start city to a destination city
- Filter results by operator
- Sort results by price (low -> high) or departure time (early -> late)
- Display vehicle number, AC/Non-AC & seat type, departure time, price, and operator name

### Admin
- Retrieve operator contact info by vehicle number
- Track operator performance (number of buses, number of schedules)
- Deletion of operator buses

## Run Instructions
1. Login to mysql with your mysql password
```bash
mysql -u root -p
```

2. Create and set up the database
```mysql
DROP DATABASE IF EXISTS travelinfo;
CREATE DATABASE travelinfo;
USE travelinfo;
```
Then import the sql files
```mysql
SOURCE sql/travelinfo_create.sql;
SOURCE sql/travelinfo_inserts.sql;
SOURCE sql/travelinfo_alter.sql;
```

3. Open src/Travelinfo.java and ensure:
```java
static final String USER = "root";
static final String PASSWORD = "your_mysql_password";
```

4. From root directory, compile java file into bin/:
```bash
javac -cp ".:lib/mysql-connector-j-8.0.32.jar" -d bin src/Travelinfo.java
```

5. Run the program from the bin folder:
```bash
cd bin
java -cp ".:../lib/mysql-connector-j-8.0.32.jar" Travelinfo
```
