# Hiring Project

This project is a full-stack web application designed for managing a hiring process. It includes a user-friendly frontend for interacting with a robust backend that handles business logic and data persistence.

## Prerequisites

Before you begin, ensure you have the following installed on your system:

*   **Java Development Kit (JDK):** Version 11 or higher
*   **Apache Maven:** For building and managing the backend dependencies
*   **Node.js and npm:** For managing the frontend dependencies
*   **MySQL:** As the database for the application

## Technology Stack

### Frontend

*   **HTML/CSS:** For creating the structure and styling of the web pages.
*   **JavaScript:** For adding interactivity and dynamic features to the user interface.
*   **Thymeleaf:** A modern server-side Java template engine for both web and standalone environments. It allows for processing and creating HTML, XML, JavaScript, CSS, and plain text.

### Backend

*   **Spring Boot:** For simplifying the creation of stand-alone, production-grade Spring-based Applications.
*   **Spring Security:** For providing authentication and authorization to the application.
*   **RESTful Services:** For exposing the application's functionality through a set of REST APIs.
*   **Spring MVC:** For building the web application and handling HTTP requests.
*   **Microservices:** The application is designed with a microservices architecture, with `hiring-api` and `hiring-portal` as two separate services.

### Data

*   **Spring Data JPA:** For simplifying data access and interaction with the database.
*   **MySQL:** A reliable and widely-used open-source relational database.

## Installation

To get the application up and running, you'll need to install the dependencies for both the backend and frontend.

### Backend

1.  Navigate to the `hiring-api` directory:
    ```bash
    cd hiring-api
    ```
2.  Run the Maven install command to download the dependencies:
    ```bash
    mvn clean install
    ```

### Frontend

1.  Navigate to the `hiring-portal` directory:
    ```bash
    cd hiring-portal
    ```
2.  Run npm install to download the dependencies:
    ```bash
    npm install
    ```

## Database Setup

The application uses MySQL as its database. Follow these steps to set it up:

1.  **Install MySQL:**

    On a Debian-based Linux distribution, you can install MySQL with the following commands:
    ```bash
    sudo apt-get update
    sudo apt-get install mysql-server
    ```

2.  **Start the MySQL Service:**
    ```bash
    sudo service mysql start
    ```

3.  **Log in to MySQL:**
    ```bash
    sudo mysql -u root -p
    ```

4.  **Create the Database and User:**

    Execute the following SQL commands to create the database and a user with the necessary privileges:
    ```sql
    CREATE DATABASE hiring_db;
    CREATE USER 'hiring_user'@'localhost' IDENTIFIED BY 'password';
    GRANT ALL PRIVILEGES ON hiring_db.* TO 'hiring_user'@'localhost';
    FLUSH PRIVILEGES;
    ```

    You will also need to update the `application.properties` file in the `hiring-api` with the database credentials.

## Folder Structure

The project is organized into two main directories:

*   **`hiring-api`:** This directory contains the backend of the application, which is built with Spring Boot. It handles the business logic, RESTful API endpoints, and communication with the database.
*   **`hiring-portal`:** This directory contains the frontend of the application. It includes the HTML, CSS, and JavaScript files that make up the user interface.

## Running the Application

To run the application, you will need to start both the backend and frontend services.

### Backend

1.  Navigate to the `hiring-api` directory:
    ```bash
    cd hiring-api
    ```
2.  Run the application using the Spring Boot Maven plugin:
    ```bash
    mvn spring-boot:run
    ```
    The backend will start on `http://localhost:8080`.

### Frontend

1.  Navigate to the `hiring-portal` directory:
    ```bash
    cd hiring-portal
    ```
2.  Start the frontend development server:
    ```bash
    npm start
    ```
    The frontend will be accessible at `http://localhost:3000`.



Running the Application
Backend
cd hiring-project/hiring-api
mvn spring-boot:run
Backend runs on http://localhost:8081

Frontend
cd hiring-project/hiring-portal
mvn spring-boot:run
Frontend runs on its configured port (check application logs).

Run Application – Sample API Calls
Once the backend is running, you can create job entries using the following curl commands:

curl -X POST http://localhost:8081/api/jobs \
  -H "Content-Type: application/json" \
  -d '{"title": "Java Developer", "description": "Spring Boot Expert", "location": "Remote", "status": "OPEN"}'
curl -X POST http://localhost:8081/api/jobs \
  -H "Content-Type: application/json" \
  -d '{"title": "Frontend Engineer", "description": "React and CSS Wizard", "location": "New York", "status": "OPEN"}'
These API calls will create job postings in the system, which can then be viewed and managed through the Hiring Portal UI.













## Architecture Flow

The application follows a client-server architecture, with the `hiring-portal` acting as the client and the `hiring-api` as the server.

1.  **Client-Side (Hiring Portal):** The user interacts with the web interface, which is built with HTML, CSS, and JavaScript. The frontend sends HTTP requests to the backend to fetch or modify data.

2.  **Server-Side (Hiring API):** The backend, built with Spring Boot, receives the HTTP requests from the client.
    *   **Spring MVC:** The Spring MVC module routes the incoming requests to the appropriate controllers.
    *   **Controllers:** The controllers handle the requests, process the data, and invoke the necessary business logic.
    *   **Spring Data JPA:** The business logic interacts with the MySQL database through Spring Data JPA, which simplifies data access and management.
    *   **Spring Security:** Spring Security is used to secure the application. It authenticates and authorizes users, ensuring that only authorized users can access certain endpoints.

3.  **Database:** The MySQL database stores all the application data, such as user information, job postings, and candidate details.

The backend returns a response to the client, which is then displayed to the user. The frontend and backend are decoupled, allowing them to be developed, deployed, and scaled independently.
