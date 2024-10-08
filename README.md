# Spring Auth Service

---

A boilerplate project for implementing Spring Security, providing essential authentication and authorization features.

### Features

---

- **Password Authentication**: Traditional sign-in using email and password.
- **Social Sign-On**: Integrate with social media accounts for authentication (e.g., Google, Github).
- **Email Verification**: Verify user email addresses during registration.
- **Role-based Dashboard**: Separate user and admin dashboard.
- **Role Management**: Manage user roles and permissions easily.

### Prerequisites

---

- Java 22 or higher
- Spring Boot 3.3.3 or higher
- PostgreSQL

## Installation Guide

---

### Clone the Repository

---

```bash
git clone https://github.com/yourusername/spring-auth-service.git
cd spring-auth-service
```

### Application Configuration

---

- Copy the `application.properties` file and paste in the directory with name `application-dev.properties`.
- Remove the `spring.profiles.active=dev` from `application-dev.properties`.
- Add necessary value for each property (if you need something, add or remove any property).

### Run the application

---

- Open a terminal in that directory and execute following command:
    ```
    ./mvnw spring-boot:run
    ```
- Open the browser and go to `http://127.0.0.1:8080`

## How to Contribute ?

---

Contributions are welcome! Here's how you can help:

1. Fork the repository.
2. Create a new feature branch:
    ```
    git checkout -b feature/your-feature-name
    ```
3. Commit your changes:
    ```
    git commit -m "feat: add some feature"
    ```
4. Push to the branch:
    ```
    git push origin feature/your-feature-name
    ```
5. Open a pull request.

Please ensure your code follows proper coding conventions and is well-tested.

## License

---

This project is licensed under the MIT License.

## Acknowledgements

---

- **Spring Boot** - The framework used.
- **Spring Security** - For authentication and authorization.
- **PostgreSQL** - For database.
- **Bootstrap** - For frontend.