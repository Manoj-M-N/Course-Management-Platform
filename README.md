# Course Management Platform

A full-stack course management platform built for the GISUL Fullstack Intern Assessment.

## 🚀 Live Demo

- **Frontend**: [Deploy URL here]
- **Backend API**: [Deploy URL here]

## 📋 Project Overview

This is a comprehensive course management platform where:
- **Admins** can create, edit, and delete courses
- **Students** can browse courses, enroll, and track their progress

Built with modern technologies and best practices, featuring JWT authentication, RESTful API, and a responsive UI.

## 🛠️ Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Security** with JWT authentication
- **Spring Data JPA**
- **MySQL 8.0**
- **Maven**

### Frontend
- **React 18**
- **React Router v6**
- **Axios** for API calls
- **Context API** for state management

## ✨ Features

### Core Features (Completed)

#### 1. Authentication ✅
- Register and login for Admin and Student roles
- JWT-based authentication
- Protected routes with role-based access control
- Automatic token management

#### 2. Course Management (Admin) ✅
- Create new courses with title, description, category, and thumbnail
- Edit existing courses
- Delete courses
- View all courses in admin dashboard

#### 3. Course Catalog (Student) ✅
- Browse all available courses
- View detailed course information
- Responsive course cards with thumbnails
- Clean and intuitive UI

#### 4. Enrollment ✅
- Students can enroll in courses
- View all enrolled courses in student dashboard
- Prevent duplicate enrollments

### Bonus Features (Completed)

#### 5. Progress Tracking ✅
- Mark lessons as complete
- Visual progress bars showing completion percentage
- Track completed lessons vs total lessons
- Update progress directly from dashboard

#### 6. Search & Filter ✅
- Search courses by title (case-insensitive)
- Filter courses by category
- Combined search and filter functionality
- Real-time results

## 📦 Installation & Setup

### Prerequisites

- **Java 17** or higher
- **Node.js 16+** and npm
- **MySQL 8.0+**
- **Maven 3.6+**

### Backend Setup

1. **Clone the repository**
```bash
git clone <repository-url>
cd course-management-platform
```

2. **Setup MySQL Database**
```bash
mysql -u root -p
CREATE DATABASE course_management_db;
exit;
```

3. **Configure Database**

Edit `backend/src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=your_password
```

4. **Build and Run Backend**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

Backend will run on `http://localhost:8080`

### Frontend Setup

1. **Install Dependencies**
```bash
cd frontend
npm install
```

2. **Configure Environment**
```bash
cp .env.example .env
```

Update `.env`:
```
REACT_APP_API_URL=http://localhost:8080/api
```

3. **Run Frontend**
```bash
npm start
```

Frontend will run on `http://localhost:3000`

## 🔐 Demo Credentials

### Admin Account
- **Email**: `admin@example.com`
- **Password**: `password123`

### Student Account
- **Email**: `student@example.com`
- **Password**: `password123`

## 📡 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user

### Courses (Public)
- `GET /api/courses` - Get all courses
- `GET /api/courses/{id}` - Get course by ID
- `GET /api/courses/search?category=&search=` - Search/filter courses

### Admin Endpoints (Requires ADMIN role)
- `POST /api/admin/courses` - Create course
- `PUT /api/admin/courses/{id}` - Update course
- `DELETE /api/admin/courses/{id}` - Delete course

### Student Endpoints (Requires STUDENT role)
- `POST /api/student/enroll/{courseId}` - Enroll in course
- `GET /api/student/enrollments` - Get enrolled courses
- `PUT /api/student/enrollments/{id}/progress` - Update progress

## 🗄️ Database Schema

### Users Table
- `id` (Primary Key)
- `email` (Unique)
- `password` (BCrypt hashed)
- `name`
- `role` (ADMIN/STUDENT)
- `created_at`, `updated_at`

### Courses Table
- `id` (Primary Key)
- `title`
- `description`
- `category`
- `thumbnail`
- `total_lessons`
- `created_by` (Foreign Key → Users)
- `created_at`, `updated_at`

### Enrollments Table
- `id` (Primary Key)
- `student_id` (Foreign Key → Users)
- `course_id` (Foreign Key → Courses)
- `completed_lessons`
- `progress_percentage`
- `enrolled_at`

## 🎨 UI/UX Features

- **Responsive Design** - Works on desktop, tablet, and mobile
- **Clean Interface** - Modern, intuitive design
- **Visual Feedback** - Loading states, error messages, success notifications
- **Progress Visualization** - Color-coded progress bars
- **Role-Based Navigation** - Different menus for admin and students
- **Search & Filter** - Real-time course filtering

## 🚀 Deployment

### Backend Deployment (Railway/Render)

1. Create account on Railway or Render
2. Create new MySQL database
3. Deploy Spring Boot application
4. Set environment variables:
   - `DATABASE_URL`
   - `JWT_SECRET`

### Frontend Deployment (Vercel/Netlify)

1. Build the frontend:
```bash
cd frontend
npm run build
```

2. Deploy to Vercel:
```bash
vercel
```

Or upload `build/` folder to Netlify

3. Set environment variable:
```
REACT_APP_API_URL=https://your-backend-api.com/api
```

## 📁 Project Structure

```
course-management-platform/
├── backend/                    # Spring Boot backend
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/gisul/courseplatform/
│   │   │   │   ├── config/           # Security & CORS
│   │   │   │   ├── controller/       # REST controllers
│   │   │   │   ├── dto/              # Data Transfer Objects
│   │   │   │   ├── model/            # JPA entities
│   │   │   │   ├── repository/       # Database repositories
│   │   │   │   ├── security/         # JWT utilities
│   │   │   │   └── service/          # Business logic
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       └── data.sql          # Demo data
│   │   └── test/
│   ├── pom.xml
│   └── README.md
├── frontend/                   # React frontend
│   ├── public/
│   ├── src/
│   │   ├── components/
│   │   │   ├── Admin/         # Admin components
│   │   │   ├── Auth/          # Login/Register
│   │   │   ├── Student/       # Student components
│   │   │   └── Common/        # Shared components
│   │   ├── context/           # Auth context
│   │   ├── services/          # API services
│   │   ├── App.jsx
│   │   └── index.js
│   ├── package.json
│   └── README.md
└── README.md                   # This file
```

## ⏱️ Development Timeline

Following the suggested 6-hour timeline:

- **Hour 1**: Project setup, database schema, authentication ✅
- **Hour 2**: Backend APIs - courses CRUD, enrollment ✅
- **Hour 3**: Admin UI - course management ✅
- **Hour 4**: Student UI - catalog, detail, enroll ✅
- **Hour 5**: Student dashboard, progress tracking ✅
- **Hour 6**: Search/filter, final touches, documentation ✅

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm test
```

## ⚠️ Known Limitations

1. **No Email Verification** - Users can register without email confirmation
2. **No Password Reset** - Password recovery not implemented
3. **Basic File Upload** - Thumbnails are URLs, not file uploads
4. **No Course Content** - Courses don't have actual lesson content/videos
5. **Simple Progress Tracking** - Progress is manually updated, not automatic
6. **No Pagination** - All courses/enrollments loaded at once

## 🔮 Future Enhancements

- [ ] Email verification and password reset
- [ ] File upload for course thumbnails
- [ ] Actual lesson content with videos
- [ ] Automatic progress tracking
- [ ] Pagination for courses and enrollments
- [ ] Course ratings and reviews
- [ ] Discussion forums
- [ ] Certificate generation
- [ ] Payment integration
- [ ] Advanced analytics

## 📝 License

This project is created for the GISUL Fullstack Intern Assessment.

## 👤 Author

[Your Name]

## 🙏 Acknowledgments

- GISUL for the assessment opportunity
- Spring Boot and React communities for excellent documentation
