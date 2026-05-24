import React, { useState, useEffect } from 'react';
import { enrollmentAPI } from '../../services/api';

const StudentDashboard = () => {
  const [enrollments, setEnrollments] = useState([]);
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState('');

  useEffect(() => {
    fetchEnrollments();
  }, []);

  const fetchEnrollments = async () => {
    try {
      const response = await enrollmentAPI.getMyEnrollments();
      setEnrollments(response.data);
    } catch (error) {
      console.error('Error fetching enrollments:', error);
    } finally {
      setLoading(false);
    }
  };

  const handleProgressUpdate = async (enrollmentId, currentCompleted, total) => {
    const newCompleted = prompt(
      `Enter completed lessons (0-${total}):`,
      currentCompleted
    );
    
    if (newCompleted === null) return;
    
    const completed = parseInt(newCompleted);
    if (isNaN(completed) || completed < 0 || completed > total) {
      setMessage('Invalid number of completed lessons');
      return;
    }

    try {
      const response = await enrollmentAPI.updateProgress(enrollmentId, completed);
      setEnrollments(enrollments.map(e => 
        e.id === enrollmentId ? response.data : e
      ));
      setMessage('Progress updated successfully!');
      setTimeout(() => setMessage(''), 3000);
    } catch (error) {
      setMessage('Failed to update progress');
    }
  };

  if (loading) {
    return <div className="loading">Loading your courses...</div>;
  }

  return (
    <div className="container dashboard">
      <h2>My Enrolled Courses</h2>

      {message && <div className="success-message">{message}</div>}

      <div className="stats-grid">
        <div className="stat-card">
          <h3>Total Courses</h3>
          <p>{enrollments.length}</p>
        </div>
        <div className="stat-card">
          <h3>In Progress</h3>
          <p>{enrollments.filter(e => e.progressPercentage > 0 && e.progressPercentage < 100).length}</p>
        </div>
        <div className="stat-card">
          <h3>Completed</h3>
          <p>{enrollments.filter(e => e.progressPercentage === 100).length}</p>
        </div>
      </div>

      {enrollments.length === 0 ? (
        <div className="empty-state">
          <h3>No enrolled courses yet</h3>
          <p>Browse the course catalog and enroll in courses to get started</p>
        </div>
      ) : (
        <div className="courses-grid">
          {enrollments.map((enrollment) => (
            <div key={enrollment.id} className="course-card">
              <img
                src={enrollment.courseThumbnail || 'https://via.placeholder.com/400x200?text=Course'}
                alt={enrollment.courseTitle}
                className="course-thumbnail"
              />
              <div className="course-content">
                <span className="course-category">{enrollment.courseCategory}</span>
                <h3 className="course-title">{enrollment.courseTitle}</h3>
                <p className="course-description">{enrollment.courseDescription}</p>
                
                <div className="progress-section">
                  <div style={{display: 'flex', justifyContent: 'space-between', marginBottom: '0.5rem'}}>
                    <span>Progress</span>
                    <span>{enrollment.progressPercentage.toFixed(0)}%</span>
                  </div>
                  <div className="progress-bar-container">
                    <div 
                      className="progress-bar" 
                      style={{width: `${enrollment.progressPercentage}%`}}
                    ></div>
                  </div>
                  <div style={{marginTop: '0.5rem', fontSize: '0.875rem', color: '#7f8c8d'}}>
                    {enrollment.completedLessons} / {enrollment.totalLessons} lessons completed
                  </div>
                  <button
                    onClick={() => handleProgressUpdate(
                      enrollment.id,
                      enrollment.completedLessons,
                      enrollment.totalLessons
                    )}
                    className="btn"
                    style={{marginTop: '1rem', fontSize: '0.875rem', padding: '0.5rem'}}
                  >
                    Update Progress
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default StudentDashboard;
