import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import { courseAPI } from '../../services/api';

const CourseForm = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const isEdit = !!id;

  const [formData, setFormData] = useState({
    title: '',
    description: '',
    category: '',
    thumbnail: '',
    totalLessons: 1,
  });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    if (isEdit) {
      fetchCourse();
    }
  }, [id]);

  const fetchCourse = async () => {
    try {
      const response = await courseAPI.getById(id);
      setFormData({
        title: response.data.title,
        description: response.data.description,
        category: response.data.category,
        thumbnail: response.data.thumbnail || '',
        totalLessons: response.data.totalLessons,
      });
    } catch (error) {
      setError('Failed to load course');
    }
  };

  const handleChange = (e) => {
    const value = e.target.name === 'totalLessons' 
      ? parseInt(e.target.value) 
      : e.target.value;
    
    setFormData({
      ...formData,
      [e.target.name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      if (isEdit) {
        await courseAPI.update(id, formData);
      } else {
        await courseAPI.create(formData);
      }
      navigate('/admin');
    } catch (err) {
      setError(err.response?.data || 'Failed to save course');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <div className="course-form-container">
        <h2>{isEdit ? 'Edit Course' : 'Create New Course'}</h2>
        
        {error && <div className="error-message">{error}</div>}

        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Title *</label>
            <input
              type="text"
              name="title"
              value={formData.title}
              onChange={handleChange}
              required
            />
          </div>

          <div className="form-group">
            <label>Description *</label>
            <textarea
              name="description"
              value={formData.description}
              onChange={handleChange}
              rows="5"
              required
            />
          </div>

          <div className="form-group">
            <label>Category *</label>
            <input
              type="text"
              name="category"
              value={formData.category}
              onChange={handleChange}
              placeholder="e.g., Web Development, Backend, Frontend"
              required
            />
          </div>

          <div className="form-group">
            <label>Thumbnail URL</label>
            <input
              type="url"
              name="thumbnail"
              value={formData.thumbnail}
              onChange={handleChange}
              placeholder="https://example.com/image.jpg"
            />
          </div>

          <div className="form-group">
            <label>Total Lessons *</label>
            <input
              type="number"
              name="totalLessons"
              value={formData.totalLessons}
              onChange={handleChange}
              min="1"
              required
            />
          </div>

          <div className="form-actions">
            <button type="submit" className="btn" disabled={loading}>
              {loading ? 'Saving...' : (isEdit ? 'Update Course' : 'Create Course')}
            </button>
            <button
              type="button"
              onClick={() => navigate('/admin')}
              className="btn btn-secondary"
            >
              Cancel
            </button>
          </div>
        </form>
      </div>
    </div>
  );
};

export default CourseForm;
