// Home.js
import React from 'react';
import { useNavigate } from "react-router-dom";
import Base from './Base';

const Home = () => {
  const navigate = useNavigate();

  const navigateTo = (path) => {
    navigate(path);
  };

  return (
    <Base>
      <div className="home-container">
        <div className="home-icon employee-icon" onClick={() => navigateTo('/employees')}>
          Employees
        </div>
        <div className="home-icon vendor-icon" onClick={() => navigateTo('/vendor')}>
          Vendors
        </div>
      </div>
    </Base>
  );
};

export default Home;
