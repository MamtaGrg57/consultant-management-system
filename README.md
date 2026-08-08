# Consultant Management System

A Java Spring Boot web application for managing consultant information. The application allows users to add, view, search, edit, delete, and manage the active/inactive status of consultants.

## Features

* Add new consultants
* Server-side validation
* View all consultants
* Search consultants by name or technology
* Edit consultant information
* Delete consultants
* Activate and deactivate consultants
* Dashboard with consultant statistics
* Total consultant count
* New consultants added this month
* Active consultant count
* Inactive consultant count
* Responsive design using Bootstrap
* Custom error page
* MySQL database integration

## Technologies Used

* Java 17
* Spring Boot
* Spring MVC
* Spring Data JPA
* Hibernate
* MySQL
* Thymeleaf
* Bootstrap 5
* Maven
* IntelliJ IDEA

## Project Architecture

The application follows a layered architecture:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
Entity
    ↓
MySQL Database
```

### Controller

Handles HTTP requests and application navigation.

### Service

Contains the business logic of the application.

### Repository

Uses Spring Data JPA to communicate with the database.

### Entity

Represents consultant information stored in the MySQL database.

## Consultant Fields

Each consultant contains:

* Name
* Email
* Phone
* Technology
* Experience
* Active/Inactive status
* Created date

## Database Setup

1. Install MySQL.
2. Create a MySQL database.
3. Update the database configuration in:

```text
src/main/resources/application.properties
```

Example:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/consultant_management_system
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace `YOUR_PASSWORD` with your MySQL password.

The project also includes an SQL script:

```text
consultant_management_system.sql
```

This script can be used to create the consultant database table and sample data.

## How to Run the Application

1. Open the project in IntelliJ IDEA.
2. Make sure MySQL is running.
3. Verify the MySQL configuration in `application.properties`.
4. Run:

```text
ConsultantManagementSystemApplication
```

5. Open a browser and go to:

```text
http://localhost:8080
```

## Application Pages

### Dashboard

Displays:

* Total consultants
* New consultants this month
* Active consultants
* Inactive consultants
* Search functionality
* Consultant table

### Add Consultant

Allows users to enter:

* Name
* Email
* Phone
* Technology
* Experience

Server-side validation is applied to submitted information.

### Consultant Management

Users can:

* Edit consultants
* Delete consultants
* Activate consultants
* Deactivate consultants
* Search by name or technology

## Validation

The application validates consultant information on the server side.

Examples:

* Name cannot be blank
* Email must be valid
* Phone is required
* Technology is required
* Experience cannot be negative

## Responsive Design

The application uses Bootstrap 5 to provide a responsive user interface that works across desktop, tablet, and mobile screen sizes.

## Database

The application uses MySQL with Spring Data JPA for persistence.

Consultant information is stored in the `consultant` table.

## Author

Developed as a Java Spring Boot Consultant Management System project.
