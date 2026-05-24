import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { courseAPI } from '../../services/api';

const AdminDashboard = () => {
  const [courses, setCourses] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    fetchCourses();
  }, []);

  const fetchCourses = async () => {
    try {
      const response = await courseAPI.getAll();
      setCourses(response.data);
    } catch (error) {
      console.error('Error fetching courses:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this course?')) {
      return;
    }

    try {
      await courseAPI.delete(id);
      setCourses(courses.filter((c) => c.id !== id));
    } catch (error) {
      alert('Failed to delete course: ' + (error.response?.data || error.message));
    }
  };

  if (loading) {
    return <div className="loading">Loading courses...</div>;
  }

  return (
    <div className="container dashboard">
      <div className="courses-header">
        <h2>Admin Dashboard</h2>
        <button onClick={() => navigate('/admin/courses/new')} className="btn">
          + Create New Course
        </button>
      </div>

      <div className="stats-grid">
        <div className="stat-card">
          <h3>Total Courses</h3>
          <p>{courses.length}</p>
        </div>
      </div>

      {courses.length === 0 ? (
        <div className="empty-state">
          <h3>No courses yet</h3>
          <p>Create your first course to get started</p>
        </div>
      ) : (
        <div className="courses-grid">
          {courses.map((course) => (
            <div key={course.id} className="course-card">
              <img
                src={course.thumbnail || 'https://via.placeholder.com/400x200?text=Course'}
                alt={course.title}
                className="course-thumbnail"
              />
              <div className="course-content">
                <span className="course-category">{course.category}</span>
                <h3 className="course-title">{course.title}</h3>
                <p className="course-description">{course.description}</p>
                <div className="course-footer">
                  <small>{course.totalLessons} lessons</small>
                  <div className="course-actions">
                    <button
                      onClick={() => navigate(`/admin/courses/edit/${course.id}`)}
                      className="btn-edit"
                    >
                      Edit
                    </button>
                    <button
                      onClick={() => handleDelete(course.id)}
                      className="btn-delete"
                    >
                      Delete
                    </button>
                  </div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default AdminDashboard;
