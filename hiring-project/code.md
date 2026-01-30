# Hiring Project Code Documentation

This document provides a detailed explanation of the files in the `hiring-project`, including their purpose, code, and connections to other files.

## `hiring-api`

The `hiring-api` is a Spring Boot application that provides a RESTful API for managing jobs and applications.

### `pom.xml`

This file defines the project's dependencies and build configuration. It includes dependencies for Spring Boot Web, Spring Data JPA, and the H2 database.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>hiring-api</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>hiring-api</name>
    <description>Hiring API</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### `src/main/java/com/example/hiring/api/Application.java`

This file defines the `Application` entity, which represents a job application.

```java
package com.example.hiring.api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String candidateName;
    private String jobTitle;
    private String status;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String linkedinId;

    public Application() {
    }

    public Application(String candidateName, String jobTitle, String status, String phoneNumber, LocalDate dateOfBirth, String linkedinId) {
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.linkedinId = linkedinId;
    }

    // Getters and Setters
}
```

### `src/main/java/com/example/hiring/api/ApplicationRepository.java`

This file defines the `ApplicationRepository`, which is a JPA repository for the `Application` entity.

```java
package com.example.hiring.api;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
```

### `src/main/java/com/example/hiring/api/HiringApiApplication.java`

This is the main class for the `hiring-api` application.

```java
package com.example.hiring.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HiringApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiringApiApplication.class, args);
    }

}
```

### `src/main/java/com/example/hiring/api/Job.java`

This file defines the `Job` entity, which represents a job posting.

```java
package com.example.hiring.api;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String location;

    public Job() {
    }

    public Job(String title, String description, String location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    // Getters and Setters
}
```

### `src/main/java/com/example/hiring/api/JobController.java`

This file defines the `JobController`, which is a REST controller that exposes endpoints for jobs and applications.

```java
package com.example.hiring.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ApplicationRepository applicationRepository;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobRepository.save(job);
    }

    @PostMapping("/apply")
    public Application apply(@RequestBody Application application) {
        return applicationRepository.save(application);
    }

    @GetMapping("/applications")
    public List<Application> getApplications() {
        return applicationRepository.findAll();
    }
}
```

### `src/main/java/com/example/hiring/api/JobRepository.java`

This file defines the `JobRepository`, which is a JPA repository for the `Job` entity.

```java
package com.example.hiring.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
```

### `src/main/resources/application.properties`

This file contains the configuration for the `hiring-api` application, including the server port and database connection settings.

```properties
server.port=8081
spring.datasource.url=jdbc:h2:mem:hiring_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

## `hiring-portal`

The `hiring-portal` is a Spring Boot application that provides a web interface for the hiring process. It communicates with the `hiring-api` to fetch and submit data.

### `pom.xml`

This file defines the project's dependencies and build configuration. It includes dependencies for Spring Boot Web, Spring Security, and Thymeleaf.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.1.0</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>com.example</groupId>
    <artifactId>hiring-portal</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <name>hiring-portal</name>
    <description>Hiring Portal</description>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-security</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.security</groupId>
            <artifactId>spring-security-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>
```

### `src/main/java/com/example/hiring/portal/Application.java`

This file defines the `Application` class, which is a plain Java object that represents a job application. This class is used to send and receive data from the `hiring-api`.

```java
package com.example.hiring.portal;

import java.time.LocalDate;

public class Application {

    private Long id;
    private String candidateName;
    private String jobTitle;
    private String status;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private String linkedinId;

    public Application() {
    }

    public Application(String candidateName, String jobTitle, String status, String phoneNumber, LocalDate dateOfBirth, String linkedinId) {
        this.candidateName = candidateName;
        this.jobTitle = jobTitle;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
        this.linkedinId = linkedinId;
    }

    // Getters and Setters
}
```

### `src/main/java/com/example/hiring/portal/HiringPortalApplication.java`

This is the main class for the `hiring-portal` application.

```java
package com.example.hiring.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HiringPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HiringPortalApplication.class, args);
    }

}
```

### `src/main/java/com/example/hiring/portal/Job.java`

This file defines the `Job` class, which is a plain Java object that represents a job posting. This class is used to receive data from the `hiring-api`.

```java
package com.example.hiring.portal;

public class Job {

    private Long id;
    private String title;
    private String description;
    private String location;

    public Job() {
    }

    public Job(String title, String description, String location) {
        this.title = title;
        this.description = description;
        this.location = location;
    }

    // Getters and Setters
}
```

### `src/main/java/com/example/hiring/portal/JobService.java`

This file defines the `JobService`, which is responsible for communicating with the `hiring-api` to get jobs and applications.

```java
package com.example.hiring.portal;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class JobService {

    private final RestTemplate restTemplate = new RestTemplate();

    public List<Job> getJobs() {
        String url = "http://localhost:8081/api/jobs";
        Job[] jobs = restTemplate.getForObject(url, Job[].class);
        if (jobs == null) {
            return List.of();
        }
        return Arrays.asList(jobs);
    }

    public void apply(Application application) {
        String url = "http://localhost:8081/api/jobs/apply";
        restTemplate.postForObject(url, application, Application.class);
    }

    public List<Application> fetchApplications() {
        String url = "http://localhost:8081/api/jobs/applications";
        Application[] applications = restTemplate.getForObject(url, Application[].class);
        if (applications == null) {
            return List.of();
        }
        return Arrays.asList(applications);
    }
}
```

### `src/main/java/com/example/hiring/portal/PortalController.java`

This file defines the `PortalController`, which handles the web requests for the hiring portal.

```java
package com.example.hiring.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PortalController {

    @Autowired
    private JobService jobService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("jobs", jobService.getJobs());
        return "index";
    }

    @GetMapping("/apply/{title}")
    public String applyForm(@PathVariable String title, Model model) {
        Application application = new Application();
        application.setJobTitle(title);
        model.addAttribute("application", application);
        model.addAttribute("jobTitle", title);
        return "apply";
    }

    @PostMapping("/apply")
    public String apply(@ModelAttribute Application application) {
        application.setStatus("APPLIED");
        jobService.apply(application);
        return "redirect:/?success";
    }

    @GetMapping("/hr/dashboard")
    public String hrDashboard(Model model) {
        model.addAttribute("applications", jobService.fetchApplications());
        return "hr-dashboard";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
```

### `src/main/java/com/example/hiring/portal/SecurityConfig.java`

This file configures Spring Security for the `hiring-portal` application. It defines the security filter chain and an in-memory user details service with two users: `admin` (with role `HR`) and `candidate` (with role `CANDIDATE`).

```java
package com.example.hiring.portal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/hr/dashboard").hasRole("HR")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("HR")
                .build();
        UserDetails candidate = User.withDefaultPasswordEncoder()
                .username("candidate")
                .password("pass")
                .roles("CANDIDATE")
                .build();
        return new InMemoryUserDetailsManager(admin, candidate);
    }
}
```

### `src/main/resources/application.properties`

This file contains the configuration for the `hiring-portal` application, including the server port.

```properties
server.port=8080
```

### `src/main/resources/templates/apply.html`

This is a Thymeleaf template for the job application page.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <title>Apply for Job</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" th:href="@{/}">Hiring Portal</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" th:href="@{/}">Home</a></li>
                <li class="nav-item" sec:authorize="hasRole('ROLE_HR')"><a class="nav-link" th:href="@{/hr/dashboard}">HR Dashboard</a></li>
            </ul>
            <form th:action="@{/logout}" method="post" class="d-flex">
                <button class="btn btn-outline-danger" type="submit">Logout</button>
            </form>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h1 class="mb-4">Apply for <span th:text="${jobTitle}"></span></h1>
    <form th:action="@{/apply}" th:object="${application}" method="post">
        <input type="hidden" th:field="*{jobTitle}" />
        <div class="mb-3">
            <label for="candidateName" class="form-label">Name:</label>
            <input type="text" id="candidateName" class="form-control" th:field="*{candidateName}" />
        </div>
        <div class="mb-3">
            <label for="phoneNumber" class="form-label">Phone Number:</label>
            <input type="text" id="phoneNumber" class="form-control" th:field="*{phoneNumber}" />
        </div>
        <div class="mb-3">
            <label for="dateOfBirth" class="form-label">Date of Birth:</label>
            <input type="date" id="dateOfBirth" class="form-control" th:field="*{dateOfBirth}" />
        </div>
        <div class="mb-3">
            <label for="linkedinId" class="form-label">LinkedIn ID:</label>
            <input type="text" id="linkedinId" class="form-control" th:field="*{linkedinId}" />
        </div>
        <div>
            <button type="submit" class="btn btn-primary">Submit Application</button>
        </div>
    </form>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

### `src/main/resources/templates/hr-dashboard.html`

This is a Thymeleaf template for the HR dashboard, which displays a list of job applications.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <title>HR Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" th:href="@{/}">Hiring Portal</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" th:href="@{/}">Home</a></li>
                <li class="nav-item" sec:authorize="hasRole('ROLE_HR')"><a class="nav-link" th:href="@{/hr/dashboard}">HR Dashboard</a></li>
            </ul>
            <form th:action="@{/logout}" method="post" class="d-flex">
                <button class="btn btn-outline-danger" type="submit">Logout</button>
            </form>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h1 class="mb-4">Applications</h1>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>Candidate Name</th>
            <th>Job Title</th>
            <th>Status</th>
            <th>Phone Number</th>
            <th>Date of Birth</th>
            <th>LinkedIn ID</th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="app : ${applications}">
            <td th:text="${app.candidateName}"></td>
            <td th:text="${app.jobTitle}"></td>
            <td th:text="${app.status}"></td>
            <td th:text="${app.phoneNumber}"></td>
            <td th:text="${app.dateOfBirth}"></td>
            <td th:text="${app.linkedinId}"></td>
        </tr>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

### `src/main/resources/templates/index.html`

This is a Thymeleaf template for the home page, which displays a list of available jobs.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org" xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <title>Hiring Portal</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" th:href="@{/}">Hiring Portal</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link" th:href="@{/}">Home</a></li>
                <li class="nav-item" sec:authorize="hasRole('ROLE_HR')"><a class="nav-link" th:href="@{/hr/dashboard}">HR Dashboard</a></li>
            </ul>
            <form th:action="@{/logout}" method="post" class="d-flex">
                <button class="btn btn-outline-danger" type="submit">Logout</button>
            </form>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <h1 class="mb-4">Available Jobs</h1>

    <div th:if="${param.success}" class="alert alert-success">
        Application successful!
    </div>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>Title</th>
            <th>Description</th>
            <th>Location</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <tr th:each="job : ${jobs}">
            <td th:text="${job.title}"></td>
            <td th:text="${job.description}"></td>
            <td th:text="${job.location}"></td>
            <td>
                <a th:href="@{/apply/{title}(title=${job.title})}" class="btn btn-primary btn-sm" sec:authorize="hasRole('ROLE_CANDIDATE')">Apply</a>
                <a href="#" class="btn btn-secondary btn-sm" sec:authorize="hasRole('ROLE_HR')">Manage</a>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

### `src/main/resources/templates/login.html`

This is a Thymeleaf template for the login page.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h1 class="text-center mt-5">Hiring Portal Login</h1>
            <div th:if="${param.error}" class="alert alert-danger mt-3">
                Invalid username and password.
            </div>
            <div th:if="${param.logout}" class="alert alert-success mt-3">
                You have been logged out.
            </div>
            <form th:action="@{/login}" method="post" class="mt-3">
                <div class="mb-3">
                    <label for="username" class="form-label">Username</label>
                    <input type="text" id="username" name="username" class="form-control" required autofocus>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password</label>
                    <input type="password" id="password" name="password" class="form-control" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Log in</button>
            </form>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

## `README.md`

This file provides an overview of the project, including prerequisites, technology stack, installation instructions, and architecture flow.

```markdown
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
    `bash
    cd hiring-api
    `
2.  Run the Maven install command to download the dependencies:
    `bash
    mvn clean install
    `

### Frontend

1.  Navigate to the `hiring-portal` directory:
    `bash
    cd hiring-portal
    `
2.  Run npm install to download the dependencies:
    `bash
    npm install
    `

## Database Setup

The application uses MySQL as its database. Follow these steps to set it up:

1.  **Install MySQL:**

    On a Debian-based Linux distribution, you can install MySQL with the following commands:
    `bash
    sudo apt-get update
    sudo apt-get install mysql-server
    `

2.  **Start the MySQL Service:**
    `bash
    sudo service mysql start
    `

3.  **Log in to MySQL:**
    `bash
    sudo mysql -u root -p
    `

4.  **Create the Database and User:**

    Execute the following SQL commands to create the database and a user with the necessary privileges:
    `sql
    CREATE DATABASE hiring_db;
    CREATE USER 'hiring_user'@'localhost' IDENTIFIED BY 'password';
    GRANT ALL PRIVILEGES ON hiring_db.* TO 'hiring_user'@'localhost';
    FLUSH PRIVILEGES;
    `

    You will also need to update the `application.properties` file in the `hiring-api` with the database credentials.

## Folder Structure

The project is organized into two main directories:

*   **`hiring-api`:** This directory contains the backend of the application, which is built with Spring Boot. It handles the business logic, RESTful API endpoints, and communication with the database.
*   **`hiring-portal`:** This directory contains the frontend of the application. It includes the HTML, CSS, and JavaScript files that make up the user interface.

## Running the Application

To run the application, you will need to start both the backend and frontend services.

### Backend

1.  Navigate to the `hiring-api` directory:
    `bash
    cd hiring-api
    `
2.  Run the application using the Spring Boot Maven plugin:
    `bash
    mvn spring-boot:run
    `
    The backend will start on `http://localhost:8080`.

### Frontend

1.  Navigate to the `hiring-portal` directory:
    `bash
    cd hiring-portal
    `
2.  Start the frontend development server:
    `bash
    npm start
    `
    The frontend will be accessible at `http://localhost:3000`.

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
```
