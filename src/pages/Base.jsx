// Base.js
import React from 'react';
import CustomNavbar from "./CustomNavbar";

const Base = ({ title = "Welcome to our website", children }) => {
  return (
    <div className="container-fluid" style={{
      backgroundImage: 'url("http://localhost:5000/user/image/background.png")',
      backgroundSize: "cover", // Use cover for background images to ensure it fills the container
      minHeight: "100vh", // Ensure the container takes full height
      position: "relative" // Ensure relative positioning
    }}>
      <CustomNavbar />
      {children}
    </div>
  );
};

export default Base;
