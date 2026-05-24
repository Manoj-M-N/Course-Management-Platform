import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { courseAPI, enrollmentAPI } from '../../services/api';
import { useAuth } from '../../context/AuthContext';

const CourseDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { isStudent, isAuthenticated } = useAuth();
  const [course, setCourse] = useState(null);
  const [loading, setLoading] = useState(true);
  const [enrolling, setEnrolling] = useState(false);
  const [message, setMessage] = useState('');

  useEffect(() => {
    fetchCourse();
  }, [id]);

  const fetchCourse = async () => {
    try {
      const response = await courseAPI.getById(id);
      setCourse(response.data);
    } catch (error) {
      console.error('Error fetching course:', error);
      setMessage('Course not found');
    } finally {
      setLoading(false);
    }
  };

  const handleEnroll = async () => {
    if (!isAuthenticated) {
      navigate('/login');
      return;
    }

    if (!isStudent) {
      setMessage('Only students can enroll in courses');
      return;
    }

    setEnrolling(true);
    try {
      await enrollmentAPI.enroll(id);
      setMessage('Successfully enrolled! Check your dashboard.');
      setTimeout(() => {
        navigate('/student');
      }, 2000);
    } catch (error) {
      setMessage(error.response?.data || 'Enrollment failed');
    } finally {
      setEnrolling(false);
    }
  };

  if (loading) {
    return <div className="loading">Loading course...</div>;
  }

  if (!course) {
    return <div className="container"><p>Course not found</p></div>;
  }

  return (
    <div className="container">
      <div className="course-detail">
        <button onClick={() => navigate('/')} className="btn btn-secondary" style={{marginBottom: '1rem', width: 'auto'}}>
          ← Back to Courses
        </button>
        
        <img
          src={course.thumbnail || 'https://via.placeholder.com/800x400?text=Course'}
          alt={course.title}
          className="course-detail-thumbnail"
        />
        
        <span className="course-category">{course.category}</span>
        <h1>{course.title}</h1>
        <p style={{color: '#7f8c8d', marginBottom: '1rem'}}>
          By {course.createdByName} | {course.totalLessons} lessons
        </p>
        
        <p style={{fontSize: '1.1rem', lineHeight: '1.6', marginBottom: '2rem'}}>
          {course.description}
        </p>

        {message && (
          <div className={message.includes('Success') ? 'success-message' : 'error-message'}>
            {message}
          </div>
        )}

        {isStudent && (
          <button 
            onClick={handleEnroll} 
            className="btn btn-enroll"
            disabled={enrolling}
          >
            {enrolling ? 'Enrolling...' : 'Enroll in this Course'}
          </button>
        )}

        {!isAuthenticated && (
          <button onClick={() => navigate('/login')} className="btn">
            Login to Enroll
          </button>
        )}
      </div>
    </div>
  );
};

export default CourseDetail;
