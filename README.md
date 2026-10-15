# WatchSpace — Collaborative Watch Party Backend

A RESTful backend application built using Java and Spring Boot that allows users to create and join virtual watch spaces using unique invite codes. Users can select titles, join existing watch spaces, communicate through chat messages, and manage watch-space sessions.

## Table of Contents

- [Project Overview](#project-overview)
- [Objectives](#objectives)
- [Technology Stack](#technology-stack)
- [Features](#features)
- [User Roles](#user-roles)
- [Application Architecture](#application-architecture)
- [Database Design](#database-design)
- [Application Workflow](#application-workflow)
- [REST API Endpoints](#rest-api-endpoints)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation and Setup](#installation-and-setup)
- [Running the Application](#running-the-application)
- [API Documentation — Swagger UI / OpenAPI](#api-documentation--swagger-ui--openapi)
- [Testing APIs](#testing-apis)
- [Error Handling and Validation](#error-handling-and-validation)


## Project Overview

WatchSpace is a backend application designed to support virtual watch parties. It provides REST APIs for user authentication, title management, watch-space creation, joining sessions through invite codes, and chat messaging.

A host can create a watch space for a selected title. The application generates a unique invite code that other users can use to join the session. Each watch space maintains information about its host, selected title, participants, messages, and current status.

The application follows a layered architecture to separate HTTP request handling, business logic, and database operations.

## Objectives

- Build a RESTful backend using Spring Boot.
- Implement authentication and role-based access.
- Manage users, titles, watch spaces, participants, and chat messages.
- Generate unique invite codes for joining watch spaces.
- Implement database relationships using Spring Data JPA and Hibernate.
- Apply separation of concerns through controller, service, and repository layers.
- Provide a foundation for future real-time collaborative viewing features.

## Technology Stack

| Technology      | Purpose 

| Java            | Core programming language 
| Spring Boot     | Application framework 
| Spring Security | Authentication and authorization 
| Spring Data JPA | Database access and persistence 
| Hibernate       | ORM implementation 
| MySQL           | Relational database for persistent data 
| Maven           | Dependency and build management 
| REST APIs       | Communication between clients and backend 
| Swagger         | API documentation and testing 
| IntelliJ IDEA   | Development environment 
| Git and GitHub  | Version control and source-code hosting 


## Features

### 1. User Management
- Register users with the PLAYER role.
- Register hosts with the HOST role.
- Authenticate registered users.
- Retrieve user information for authenticated operations.

### 2. Authentication and Authorization
- Integrate Spring Security.
- Authenticate users through the application's login flow.
- Use the authenticated user's identity when performing protected operations.
- Distinguish between players and hosts.
- Apply role-based restrictions to operations where configured.

### 3. Title Management
- Add titles to the application.
- Retrieve the available titles.
- Retrieve a specific title using its ID.
- Associate a watch space with a selected title.

### 4. Watch-Space Management
- Create a watch space for a selected title.
- Assign the authenticated host as the watch-space owner.
- Generate a unique invite code.
- Retrieve watch-space details.
- Track the status of a watch space.
- End an active watch space.

### 5. Join Using an Invite Code
- Allow users to join a watch space using its invite code.
- Find the corresponding watch space using the code.
- Record participant information and joining time.
- Reject requests when the invite code does not correspond to an existing watch space.

### 6. Chat Messaging
- Post messages to a watch space.
- Retrieve messages for a particular watch space.
- Order messages chronologically.
- Associate messages with their sender and watch space.

### 7. Watch-Space Lifecycle
Watch spaces use status values such as `ACTIVE` and `ENDED`.

- `ACTIVE`: The watch space is available for participation.
- `ENDED`: The watch space has been ended by an authorized operation.

The backend can validate the current status before allowing operations that require an active session.

## User Roles

### HOST
A host is responsible for creating and managing a watch space.

- Register and log in as a host.
- Select a title when creating a watch space.
- Create a watch space and obtain an invite code.
- Manage the session according to the implemented authorization rules.

### PLAYER
A player participates in a watch space.

- Register and log in as a player.
- Browse available titles.
- Join a watch space using an invite code.
- Participate in chat when permitted.

**Authorization principle:** A host should only be able to end or manage their own watch spaces, unless an explicitly authorized administrator role is introduced. Role checks alone do not establish ownership; the authenticated user's ID should also be compared with the watch space's host ID.

## Application Architecture

The application follows a layered architecture.

```text
    Client 
       |
       v
Spring Security
       |
       v
Controller Layer
       |
       v
Service Layer
       |
       v
Repository Layer
       |
       v
MySQL Database
```

### Controller Layer
Responsible for receiving HTTP requests, reading path variables and request bodies, invoking service methods, and returning HTTP responses.

### Service Layer
Contains the business logic, including authentication-related operations, invite-code joining, watch-space creation, status management, and message retrieval.

### Repository Layer
Uses Spring Data JPA repositories to query and persist application data.

### Entity Layer
Defines the application's persistent data models and their relationships.

### DTO Layer
Data Transfer Objects can be used to control request and response formats without exposing complete JPA entities.

## Database Design

The application uses JPA entities to represent its main domain objects.

### Users
Represents registered users and hosts.

Typical fields:
- `userId`
- `username`
- `email`
- `password`
- `role`

Relationships connect users with hosted watch spaces, participants, and messages.

### Title
Represents a movie or other title available for a watch party.

Typical fields:
- `titleId`
- `name`
- `description`
- `genre`
- `duration`

A title can be associated with multiple watch spaces.

### WatchSpace
Represents a collaborative viewing session.

Typical fields:
- `watchspaceId`
- `inviteCode`
- `status`

Relationships:
- Many-to-one relationship with `Title`.
- Many-to-one relationship with `Users`, representing the host.
- One-to-many relationships with participant and chat-message records, according to the entity mappings.

### WatchSpaceParticipant
Represents a user's participation in a watch space.

Typical information includes:
- Participant identifier and associated user.
- Associated watch space.
- Joining timestamp.

### ChatMessage
Represents a message sent within a watch space.

Typical information includes:
- Message identifier.
- Message content.
- Sender.
- Associated watch space.
- Message timestamp.

## Application Workflow

### Creating a Watch Space

1. A host registers and authenticates.
2. The host selects a title using its `titleId`.
3. The client sends a request to create a watch space.
4. The backend retrieves the authenticated host and selected title.
5. The backend creates the watch-space record with an initial status of `ACTIVE`.
6. A unique invite code is generated.
7. The backend saves the watch space and returns its invite code.

### Joining a Watch Space

1. A player authenticates.
2. The player submits an invite code.
3. The backend searches for the corresponding watch space.
4. The backend validates that the session is eligible for joining.
5. The backend records the participant and joining timestamp.
6. The backend returns the appropriate response.

### Sending a Chat Message

1. An authenticated participant submits a message.
2. The backend identifies the sender from the security context.
3. The backend retrieves the target watch space.
4. The backend validates the relevant participation and session rules.
5. The message is saved with its sender, watch space, and timestamp.

### Ending a Watch Space

1. An authorized host requests to end a watch space.
2. The backend checks whether the watch space exists.
3. The backend checks its current status and, where implemented, verifies host ownership.
4. The status changes from `ACTIVE` to `ENDED`.
5. Further operations can be restricted according to the session rules.

## REST API Endpoints

The following endpoint list reflects the intended API structure. Verify each HTTP method and URL against your current controller mappings before publishing this section.

### User and Authentication APIs

| Method    | Endpoint        | Description

| POST      | `/register`     | Register a player
| POST      | `/login`        | Authenticate a player
| POST      | `/hostRegister` | Register a host
| POST      | `/hostLogin`    | Authenticate a host

### Title APIs

| Method    | Endpoint            | Description

| POST      | `/admin/postTitles` | Add a title
| GET       | `/getTitles`        | Retrieve available titles
| GET       | `/getTitles/{id}`   | Retrieve a title by ID

### Watch-Space APIs

| Method    | Endpoint                         | Description

| POST      | `/watchSpaces`                   | Create a watch space
| GET       | `/getWatchSpace`                 | Retrieve watch spaces
| GET       | `/getWatchSpace/{id}`            | Retrieve a watch space by ID
| POST      | `/watchSpaces/join/{inviteCode}` | Join using an Invite code
| POST      | `/endWatchSpace`                 | End a watch space

### Chat APIs

| Method    | Endpoint                                 | Description

| GET       | `/watchSpaces/{watchSpaceId}/messages`   | Retrieve messages for a watch space
| POST      | `/watchSpace/postMessage`                | Post a message to a watch space


## Project Structure

The package and class names may vary slightly as the project evolves.

```text
watchSpace/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bharath/
│   │   │           └── watchSpace/
│   │   │               ├── controller/
│   │   │               │   ├── UserController.java
│   │   │               │   ├── TitleController.java
│   │   │               │   └── WatchSpaceController.java
│   │   │               ├── service/
│   │   │               ├── repository/
│   │   │               ├── entity/
│   │   │               ├── dto/
│   │   │               ├── security/
│   │   │               └── WatchSpaceApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
├── pom.xml
├── README.md
└── .gitignore
```


## Prerequisites

Install the following before running the application:

- JDK compatible with the Java version configured in `pom.xml`.
- IntelliJ IDEA or another Java IDE.
- Maven, or the Maven Wrapper included with the project.
- MySQL Server 8.x or a compatible MySQL version.
- Postman or another REST API client.

## Installation and Setup

### 1. Clone the Repository

Replace the placeholder URL with your actual GitHub repository URL.

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
cd YOUR_REPOSITORY
```

Alternatively, open the project directly from IntelliJ IDEA.

### 2. Configure the Application

Open:

`src/main/resources/application.properties`

The application uses MySQL for persistent data storage.

First, start your MySQL server and create the project database:

```sql
CREATE DATABASE watchspaceprojectdatabase;
```

Then configure `src/main/resources/application.properties` with your local MySQL credentials:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/watchspaceprojectdatabase
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Replace `YOUR_MYSQL_PASSWORD` with your actual MySQL password. Do not commit real credentials to GitHub. For a public repository, use environment variables or a local configuration file excluded through `.gitignore`.

Keep the MySQL Connector/J runtime dependency in `pom.xml` and remove the H2 dependency if you are no longer using H2. After updating the configuration, reload Maven and restart the application.

### 3. Resolve Dependencies

Using the IntelliJ Maven tool window, reload the Maven project.

Alternatively, run:

```bash
mvn clean install
```

If the project includes the Maven Wrapper, use `mvnw.cmd` on Windows or `./mvnw` on Linux and macOS.

### 4. Run the Application

Locate the main Spring Boot application class and run its `main()` method from IntelliJ IDEA.

Check the Run console for startup errors before sending API requests.

## Running the Application

Once the application has started, send requests through Postman using the port configured by Spring Boot.

For the default port, the base URL is:

```text
http://localhost:8080
```

For example, if the application uses the default port, the title retrieval endpoint is:

```text
GET http://localhost:8080/getTitles
```

Verify that the configured MySQL database is available and that the application starts without datasource or authentication errors. You can inspect the database and tables using MySQL Workbench or the MySQL command-line client.

## API Documentation — Swagger UI / OpenAPI

WatchSpace can use Swagger UI and OpenAPI to provide interactive documentation for its REST APIs.

Swagger UI allows developers to explore available endpoints, inspect request and response formats, and execute API requests directly from a browser without manually creating every request in Postman.

### Features

Interactive documentation for REST API endpoints.

Visibility into HTTP methods, request parameters, and response schemas.

Ability to execute API requests from the browser.

OpenAPI-based API descriptions.

Support for testing authenticated endpoints when the Spring Security configuration permits access and the required authentication mechanism is configured in Swagger UI.

Access Swagger UI

Start the Spring Boot application and open:

http://localhost:8080/swagger-ui/index.html

The OpenAPI specification is typically available at:

http://localhost:8080/v3/api-docs

These are the default Springdoc URLs. They may differ if the application uses custom configuration.

## Testing APIs

Use Swagger to test the application in the following order:

1. Register a player.
2. Register a host.
3. Authenticate the host and obtain whatever authentication credential the login API returns.
4. Retrieve available titles and note a valid `titleId`.
5. Create a watch space using that `titleId`.
6. Save the returned invite code.
7. Authenticate a player and join the watch space using the invite code.
8. Retrieve the watch-space details.
9. Test posting and retrieving chat messages.
10. End the watch space and verify its updated status.
11. Test invalid invite codes, nonexistent IDs, repeated join attempts, unauthorized operations, and operations on ended sessions.

## Error Handling and Validation

The application should handle invalid requests and common failure cases consistently.

Examples include:

- User not found.
- Title not found.
- Watch space not found.
- Invalid or unknown invite code.
- Attempt to join a session that has ended.
- Attempt to end an already-ended watch space.
- Missing or invalid request fields.
- Unauthenticated requests.
