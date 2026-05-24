import React from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

const Navbar = () => {
  const { user, logout, isAuthenticated, isAdmin, isStudent } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-content">
        <h1 onClick={() => navigate('/')}>Course Platform</h1>
        <div className="navbar-links">
          {isAuthenticated ? (
            <>
              <Link to="/">Courses</Link>
              {isAdmin && <Link to="/admin">Admin Dashboard</Link>}
              {isStudent && <Link to="/student">My Courses</Link>}
              <span className="user-info">
                {user?.name} ({user?.role})
              </span>
              <button onClick={handleLogout}>Logout</button>
            </>
          ) : (
            <>
              <Link to="/login">Login</Link>
              <Link to="/register">Register</Link>
            </>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
