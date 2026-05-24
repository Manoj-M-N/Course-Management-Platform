# Course Management Platform - Frontend

React frontend for the GISUL Course Management Platform.

## Tech Stack

- **React 18**
- **React Router v6**
- **Axios**
- **Context API for state management**

## Prerequisites

- Node.js 16+ and npm

## Setup Instructions

### 1. Install Dependencies

```bash
cd frontend
npm install
```

### 2. Configure Environment

Create a `.env` file in the frontend directory:

```bash
cp .env.example .env
```

Update the API URL if needed:
```
REACT_APP_API_URL=http://localhost:8080/api
```

### 3. Run the Application

```bash
npm start
```

The frontend will start on `http://localhost:3000`

## Available Scripts

- `npm start` - Run development server
- `npm build` - Build for production
- `npm test` - Run tests

## Features

### For All Users
- Browse course catalog
- Search and filter courses by category and title
- View course details

### For Students
- Register and login
- Enroll in courses
- View enrolled courses dashboard
- Track progress with progress bars
- Mark lessons as complete

### For Admins
- Register and login as admin
- Create, edit, and delete courses
- Manage all courses from admin dashboard

## Project Structure

```
frontend/
├── public/
│   └── index.html
├── src/
│   ├── components/
│   │   ├── Admin/
│   │   │   ├── AdminDashboard.jsx    # Admin course management
│   │   │   └── CourseForm.jsx         # Create/Edit courses
│   │   ├── Auth/
│   │   │   ├── Login.jsx              # Login page
│   │   │   └── Register.jsx           # Registration page
│   │   ├── Student/
│   │   │   ├── CourseCatalog.jsx      # Browse all courses
│   │   │   ├── CourseDetail.jsx       # View course details
│   │   │   └── StudentDashboard.jsx   # Enrolled courses
│   │   └── Common/
│   │       ├── Navbar.jsx             # Navigation bar
│   │       └── PrivateRoute.jsx       # Route protection
│   ├── context/
│   │   └── AuthContext.jsx            # Authentication state
│   ├── services/
│   │   └── api.js                     # API calls
│   ├── App.jsx                        # Main app component
│   ├── App.css                        # Styles
│   ├── index.js                       # Entry point
│   └── index.css                      # Global styles
├── package.json
└── README.md
```

## Demo Credentials

### Admin Account
- Email: `admin@example.com`
- Password: `password123`

### Student Account
- Email: `student@example.com`
- Password: `password123`

## Authentication

The app uses JWT tokens for authentication:
- Token is stored in localStorage
- Automatically added to API requests
- Protected routes redirect to login if not authenticated
- Role-based access control (Admin/Student)

## Building for Production

```bash
npm run build
```

The optimized build will be in the `build/` directory.

## Deployment

### Vercel (Recommended)

1. Install Vercel CLI:
```bash
npm install -g vercel
```

2. Deploy:
```bash
cd frontend
vercel
```

3. Set environment variables in Vercel dashboard:
```
REACT_APP_API_URL=https://your-backend-api.com/api
```

### Netlify

1. Build the app:
```bash
npm run build
```

2. Deploy the `build/` directory to Netlify

3. Set environment variable:
```
REACT_APP_API_URL=https://your-backend-api.com/api
```

## Troubleshooting

### CORS Issues

Make sure the backend CORS configuration includes your frontend URL:
```properties
cors.allowed.origins=http://localhost:3000,https://your-frontend-domain.com
```

### API Connection Failed

- Verify backend is running on port 8080
- Check REACT_APP_API_URL in .env file
- Check browser console for errors
