import React, { useState } from "react";
import { NavLink as ReactLink, useNavigate } from "react-router-dom";
import {
  Navbar,
  NavbarBrand,
  Nav,
  NavItem,
  NavLink
} from "reactstrap";
import { doLogout, isLoggedIn } from "./Index";
import { toast } from "react-toastify";

function CustomNavbar() {
  const navigate = useNavigate();
  const [isLogoutHovered, setIsLogoutHovered] = useState(false);

  const handleLogoutMouseEnter = () => {
    setIsLogoutHovered(true);
  };

  const handleLogoutMouseLeave = () => {
    setIsLogoutHovered(false);
  };

  const logout = () => {
    console.log("Session", );
    setTimeout(() => {
     
        toast.error("Session expired, Please do login again to continue using Credmarg.", {
          style: {
            width: "580px",
          },
          autoClose: 12000,
        });
      
      doLogout(() => {
        console.log("LOGOUT");
        navigate("/");
      });
    });
  };

  return (
    <div>
      <Navbar style={{ backgroundColor: "rgba(0, 0, 0, 0.6)" }} light expand="md">
        <NavbarBrand
          style={{
            color: "#e6e6e6",
            fontWeight: "bold",
            fontFamily: "Montserrat, sans-serif",
            flex: 1,
            textAlign: 'center'
          }}
        >
          CREDMARG
        </NavbarBrand>
        <Nav className="ml-auto" navbar>
          {isLoggedIn() && (
            <NavItem>
              <NavLink
                onClick={logout}
                onMouseEnter={handleLogoutMouseEnter}
                onMouseLeave={handleLogoutMouseLeave}
                style={{
                  color: "#e6e6e6",
                  fontWeight: "bold",
                  fontFamily: "Montserrat, sans-serif",
                  border: "1px solid #e6e6e6",
                  borderRadius: "5px",
                  padding: "8px 12px",
                  transition: "all 0.2s ease-in-out",
                  backgroundColor: isLogoutHovered ? "#e6e6e6" : "transparent",
                  color: isLogoutHovered ? "#333" : "#e6e6e6",
                  marginRight: "10px",
                }}
              >
                Logout
              </NavLink>
            </NavItem>
          )}
        </Nav>
      </Navbar>
    </div>
  );
}

export default CustomNavbar;
