# Course Management Platform - Backend

Spring Boot backend for the GISUL Course Management Platform.

## Tech Stack

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security with JWT**
- **Spring Data JPA**
- **MySQL 8.0+**
- **Maven**

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+

## Setup Instructions

### 1. Install MySQL

Make sure MySQL is installed and running on your system.

### 2. Create Database

```bash
mysql -u root -p
CREATE DATABASE course_management_db;
exit;
```

### 3. Configure Database

Update `src/main/resources/application.properties` with your MySQL credentials:

```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

### 4. Install Dependencies

```bash
cd backend
mvn clean install
```

### 5. Run the Application

```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Courses (Public)

- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/search?category=&search=` - Search courses

### Admin Endpoints (Requires ADMIN role)

- `POST /api/admin/courses` - Create course
- `PUT /api/admin/courses/{id}` - Update course
- `DELETE /api/admin/courses/{id}` - Delete course

### Student Endpoints (Requires STUDENT role)

- `POST /api/student/enroll/{courseId}` - Enroll in course
- `GET /api/student/enrollments` - Get enrolled courses
- `PUT /api/student/enrollments/{id}/progress` - Update progress

## Demo Credentials

### Admin Account
- Email: `admin@example.com`
- Password: `password123`

### Student Account
- Email: `student@example.com`
- Password: `password123`

## Database Schema

The application automatically creates tables on startup:

- **users** - Stores user accounts (admin/student)
- **courses** - Stores course information
- **enrollments** - Tracks student enrollments and progress

## JWT Authentication

All protected endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

Token is returned after successful login/registration.

## Running Tests

```bash
mvn test
```

## Building for Production

```bash
mvn clean package
java -jar target/course-platform-1.0.0.jar
```

## Troubleshooting

### Database Connection Error

- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure database `course_management_db` exists

### Port Already in Use

Change the port in `application.properties`:
```properties
server.port=8081
```

## Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/gisul/courseplatform/
│   │   │   ├── config/          # Security & CORS configuration
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── model/           # JPA entities
│   │   │   ├── repository/      # Database repositories
│   │   │   ├── security/        # JWT utilities
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql         # Demo data seeding
│   └── test/                    # Unit tests
├── pom.xml
└── README.md
```
