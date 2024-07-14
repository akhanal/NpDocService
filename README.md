
# NpDoc Service
NpDoc Service is a Spring Boot application designed to manage users and doctors. It provides REST APIs for user registration, login, and retrieval of user and doctor data.

### Project Structure
```plaintext
src/main/java/co/yasok/npdoc/
├── config/
│   ├── DataInitializer.java
│   └── OpenApiConfig.java
├── controller/
│   └── UserController.java
├── dto/
│   ├── DoctorDTO.java
│   └── UserDTO.java
├── entity/
│   ├── Doctor.java
│   ├── User.java
│   └── UserType.java
├── repo/
│   ├── DoctorRepository.java
│   └── UserRepository.java
├── service/
│   └── UserService.java
└── NpDocServiceMain.java
```

# Code Organization
## Main Application
### NpDocServiceMain.java: 
The entry point of the Spring Boot application.
## Configuration
### DataInitializer.java: 
This class initializes the database with some sample data at startup.
### OpenApiConfig.java: 
This class configures OpenAPI for API documentation.

## Controllers
### UserController.java: 
Defines REST endpoints for user-related operations such as registration, login, and fetching users and doctors.

## DTOs (Data Transfer Objects)
### DoctorDTO.java: 
Data transfer object for Doctor.
### UserDTO.java: 
Data transfer object for User.

## Entities
### Doctor.java: 
Entity representing a doctor with additional attributes like NMC number, specialty, and bio.
### User.java: 
Base entity representing a user with attributes like full name, email, password, and user type.
### UserType.java: 
Enumeration for user types (PATIENT, DOCTOR).

## Repositories
### DoctorRepository.java: 
Repository interface for Doctor entity.
### UserRepository.java: 
Repository interface for User entity.

## Services
### UserService.java: 
Provides business logic for user-related operations such as registration, login, and fetching users and doctors.

## APIs
### User Registration
```plaintext
Endpoint: /api/register
Method: POST
Parameters:
fullName: Full name of the user.
email: Email of the user.
password: Password for the user.
Description: Registers a new user with the provided details.
```

### User Login
```plaintext
Endpoint: /api/login
Method: POST
Parameters:
email: Email of the user.
password: Password for the user.
Description: Authenticates a user and returns user details if successful.
```

### Get All Users
```plaintext
Endpoint: /api/users
Method: GET
Description: Retrieves a list of all users who are patients.
```

### Get All Doctors
```plaintext
Endpoint: /api/doctors
Method: GET
Description: Retrieves a list of all doctors with their details.
```

# Getting Started
## Prerequisites
    Java 17 or higher
    Maven 3.6.3 or higher

## Running the Application
Clone the repository:
```shell
git clone https://github.com/yourusername/npdoc-service.git
```

Navigate to the project directory:
```sh
cd npdoc-service
```

Build the project:
```sh
mvn clean install
```

Run the application:
```sh
mvn spring-boot:run
```

Access the application at http://localhost:8080.

### API Documentation
The API documentation is available at http://localhost:8080/swagger-ui.html once the application is running.

# License
This project is licensed under the MIT License - see the LICENSE file for details.

# Acknowledgments
    Spring Boot
    Swagger/OpenAPI
