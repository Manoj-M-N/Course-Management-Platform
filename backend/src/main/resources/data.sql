-- This file will seed demo data when the application starts
-- Note: Password for all users is 'password123' (hashed with BCrypt)
-- BCrypt hash for 'password123': $2a$10$xqH7Tq6h4.sDL9cRB3YEUuF0KCMhvmz4m8qVpqQ2DmL6YxKxGXhwK

-- Clear existing data (optional - comment out if you want to preserve data)
-- DELETE FROM enrollments;
-- DELETE FROM courses;
-- DELETE FROM users;

-- Insert demo users (1 admin, 1 student)
INSERT INTO users (email, password, name, role, created_at, updated_at) 
SELECT 'admin@example.com', '$2a$10$xqH7Tq6h4.sDL9cRB3YEUuF0KCMhvmz4m8qVpqQ2DmL6YxKxGXhwK', 'Admin User', 'ADMIN', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'admin@example.com');

INSERT INTO users (email, password, name, role, created_at, updated_at) 
SELECT 'student@example.com', '$2a$10$xqH7Tq6h4.sDL9cRB3YEUuF0KCMhvmz4m8qVpqQ2DmL6YxKxGXhwK', 'Student User', 'STUDENT', NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE email = 'student@example.com');

-- Insert demo courses (3 courses)
INSERT INTO courses (title, description, category, thumbnail, total_lessons, created_by, created_at, updated_at)
SELECT 'Introduction to Web Development', 
       'Learn the fundamentals of web development including HTML, CSS, and JavaScript. This comprehensive course covers everything you need to start building modern websites.',
       'Web Development',
       'https://images.unsplash.com/photo-1498050108023-c5249f4df085?w=400',
       10,
       (SELECT id FROM users WHERE email = 'admin@example.com'),
       NOW(),
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE title = 'Introduction to Web Development');

INSERT INTO courses (title, description, category, thumbnail, total_lessons, created_by, created_at, updated_at)
SELECT 'Advanced React and Redux', 
       'Master React and Redux by building real-world applications. Learn hooks, context API, Redux toolkit, and best practices for scalable React applications.',
       'Frontend',
       'https://images.unsplash.com/photo-1633356122544-f134324a6cee?w=400',
       15,
       (SELECT id FROM users WHERE email = 'admin@example.com'),
       NOW(),
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE title = 'Advanced React and Redux');

INSERT INTO courses (title, description, category, thumbnail, total_lessons, created_by, created_at, updated_at)
SELECT 'Spring Boot Microservices', 
       'Build production-ready microservices with Spring Boot, Spring Cloud, and Docker. Learn about service discovery, API gateway, and distributed systems.',
       'Backend',
       'https://images.unsplash.com/photo-1542831371-29b0f74f9713?w=400',
       12,
       (SELECT id FROM users WHERE email = 'admin@example.com'),
       NOW(),
       NOW()
WHERE NOT EXISTS (SELECT 1 FROM courses WHERE title = 'Spring Boot Microservices');
