# Learning Management System (LMS) - Backend

## Overview

The **Learning Management System (LMS)** backend is built using **Java Spring Boot** and designed to manage user roles, authentication, and role-based access to various system features. This backend facilitates the smooth operation of a Learning Management System, including user management, course enrollment, and role-specific operations.

---

## Features

### User Roles

- **Student**: Can view courses, enroll, and access learning materials.
- **Instructor**: Can create and manage courses.
- **Admin**: Manages user roles and overall system configurations.
- **User**: Basic system user with limited access.

### Authentication & Authorization

- Login functionality.
- Token-based authentication using JWT.
- Role-based URL access control.

### Core Operations

- Create, update, delete, and view courses.
- User registration and management.
- Role-specific access control for functions.

---

## Technologies Used

| **Component**         | **Technology**                   |
| --------------------- | -------------------------------- |
| **Backend Framework** | Java Spring Boot (version 3.4.1) |
| **Database**          | PostgreSQL                       |
| **Authentication**    | JWT                              |
| **Testing Tools**     | Postman                          |

---

## Project Structure

```plaintext
src/
|-- main/
|   |-- java/com/lms/
|   |   |-- controllers/    # REST controllers
|   |   |-- services/       # Business logic
|   |   |-- repositories/   # Database repositories
|   |   |-- models/         # Data models
|   |   |-- config/         # Security and app configuration
|   |-- resources/
|       |-- application.yml  # Application configuration
|-- test/
    |-- java/com/lms/        # Test cases
```

---

## Installation and Setup

### Prerequisites

1. **Java**: Ensure JDK 17 or above is installed.
2. **PostgreSQL**: Install PostgreSQL and create a database for the project.
3. **Postman**: For testing API endpoints.

### Steps

1. Clone the repository:

   ```bash
   git clone https://github.com/Yousef-karem/Learning-Management-System.git
   cd Learning-Management-System/lms-backend
   ```

2. Configure the database:

   - Update `src/main/resources/application.yml` with your PostgreSQL credentials.

   ```yaml
   spring:
     datasource:
       url: jdbc:postgresql://localhost:5432/lms_db
       username: your_username
       password: your_password
     jpa:
       hibernate:
         ddl-auto: update
   jwt:
     secret: your_jwt_secret
   ```

3. Build and run the project:

   ```bash
   ./mvnw spring-boot:run
   ```

4. Access the API:

   - API base URL: `http://localhost:8080`

---

## API Endpoints

### Authentication

| **Method** | **Endpoint**   | **Description**                         |
| ---------- | -------------- | --------------------------------------- |
| POST       | /auth/login    | Authenticate user and return JWT token. |
| POST       | /auth/register | Register a new user.                    |

### User Management

| **Method** | **Endpoint**     | **Description**                |
| ---------- | ---------------- | ------------------------------ |
| GET        | /users           | List all users (Admin only).   |
| PUT        | /users/{id}/role | Update user role (Admin only). |

### Course Management

| **Method** | **Endpoint**  | **Description**                              |
| ---------- | ------------- | -------------------------------------------- |
| GET        | /courses      | View all courses (Student/Instructor/Admin). |
| POST       | /courses      | Create a new course (Instructor only).       |
| PUT        | /courses/{id} | Update course details (Instructor only).     |
| DELETE     | /courses/{id} | Delete a course (Instructor/Admin).          |

---

## Testing

1. Open Postman.
2. Import the Postman collection provided in the repository (if available) or manually create requests based on the endpoints listed above.
3. Test the endpoints using appropriate roles and JWT tokens.

---

## Contribution

Contributions are welcome! Follow these steps to contribute:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Commit your changes and push to your fork.
4. Open a pull request to the main repository.

---

## License

This project is licensed under the [MIT License](LICENSE).

---

## Contact

For further inquiries or collaboration, please contact:

- **Yousef Karem**\
  [GitHub Profile](https://github.com/Yousef-karem)
- **Juliana George**\
[GitHub Profile](https://github.com/julianageorge)
- **Ephraim Youssef**\
[GitHub Profile](https://github.com/EphraimYoussef)
- **Ramez Ragaay**\
[GitHub Profile](https://github.com/RamezRagaay)
- **Arsany Nageh**\
[GitHub Profile](https://github.com/Arsany11)

---

This backend system is a crucial component of the Learning Management System and ensures secure and efficient management of users, roles, and courses.

