# 📝 Todo Application

### 🔐 Spring Boot • JWT • MySQL • Flyway

A secure and production-ready RESTful Todo API built using Spring Boot,
Spring Security (JWT Authentication), Hibernate (JPA), and MySQL, with
database versioning powered by Flyway.

------------------------------------------------------------------------

## 🚀 Key Features

### 🔑 Authentication & Security

-   User Registration
-   Secure Login with JWT Access Token
-   Refresh Token stored in HttpOnly Cookie
-   User Verification Endpoint
-   Protected APIs with Spring Security
-   Centralized Exception Handling
-   DTO-based Validation using `@Valid`

### 📌 Task Management

-   Create Task
-   Fetch All Tasks
-   Fetch Task by ID
-   Update Task (PUT)
-   Partial Update (PATCH)
-   Delete Task

### 🗄 Database & Migrations

-   MySQL Integration
-   Hibernate (JPA)
-   Flyway Version-Controlled Migrations
-   Schema validation (`ddl-auto=validate`)

------------------------------------------------------------------------

## 🛠 Tech Stack

  Category     Technology
  ------------ -----------------------
  Language     Java 17+
  Framework    Spring Boot
  Security     Spring Security + JWT
  ORM          Hibernate (JPA)
  Database     MySQL
  Migration    Flyway
  Build Tool   Maven
  Validation   Jakarta Validation

------------------------------------------------------------------------

## 📂 Project Architecture

    org.crud.todo
    │
    ├── config
    ├── controller
    │   ├── auth
    │   └── tasks
    ├── dto
    │   ├── auth
    │   ├── tasks
    │   └── common
    ├── exception
    ├── helper
    ├── model
    ├── repository
    ├── security
    ├── service
    │   ├── auth
    │   └── tasks
    └── TodoApplication.java

------------------------------------------------------------------------

# ⚙️ Getting Started

## 1️⃣ Clone the Repository

``` bash
git clone https://github.com/The-Indian-Programmer/Spring-Boot-TodoApplication
cd Spring-Boot-TodoApplication
```

## 2️⃣ Create MySQL Database

``` sql
CREATE DATABASE todo_db;
```

## 3️⃣ Configure `application.properties`

``` properties
spring.application.name=demo
server.port=3500

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/todo_db
spring.datasource.username=root
spring.datasource.password=12345678

# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

# Flyway Migration
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migrations

# JWT Configuration
jwt.secret=your_secure_secret_here
jwt.access-expiration=900000
jwt.refresh-expiration=604800000
```

## 4️⃣ Build & Run Application

``` bash
mvn clean install
mvn spring-boot:run
```

Application runs at:

    http://localhost:3500

------------------------------------------------------------------------

# 🔐 Authentication APIs

### Register

**POST** `/api/auth/register`

``` json
{
  "username": "sumit",
  "password": "password123"
}
```

### Login

**POST** `/api/auth/login`

Returns: - Access Token (response body) - Refresh Token (HttpOnly
cookie)

### Verify User

**GET** `/api/auth/verify`

Header: Authorization: Bearer `<accessToken>`{=html}

------------------------------------------------------------------------

# 📌 Task APIs

All endpoints require:

Authorization: Bearer `<accessToken>`{=html}

  Method   Endpoint          Description
  -------- ----------------- -----------------
  POST     /api/tasks        Create new task
  GET      /api/tasks        Get all tasks
  GET      /api/tasks/{id}   Get task by ID
  PUT      /api/tasks/{id}   Update task
  PATCH    /api/tasks/{id}   Partial update
  DELETE   /api/tasks/{id}   Delete task

------------------------------------------------------------------------

# 🧾 Standard API Response Format

``` json
{
  "success": true,
  "message": "Operation successful",
  "statusCode": 200,
  "data": {}
}
```

------------------------------------------------------------------------

# 🛡 Security Highlights

-   JWT-based authentication
-   HttpOnly Refresh Token Cookie
-   Protected Endpoints
-   Centralized Error Handling
-   Input Validation

------------------------------------------------------------------------

# 📊 HTTP Status Codes

  Code   Description
  ------ -----------------------
  200    OK
  201    Created
  400    Validation Error
  401    Unauthorized
  403    Forbidden
  404    Not Found
  500    Internal Server Error

------------------------------------------------------------------------

# 📦 Future Enhancements

-   Refresh Token Endpoint\
-   Role-Based Authorization\
-   Swagger / OpenAPI Documentation\
-   Docker Support\
-   CI/CD Integration\
-   Unit & Integration Testing


# 👨‍💻 Author : Sumit Kosta (sumitkosta07@gmail.com)


Built with Spring Boot, MySQL & Flyway