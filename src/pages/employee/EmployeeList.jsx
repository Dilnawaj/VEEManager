import React, { useState, useEffect } from "react";

import { Col, Row, Container, Button } from "reactstrap";
import { toast } from "react-toastify";
import { FaArrowLeft, FaSearch, FaUndo } from "react-icons/fa";
import { Link } from "react-router-dom";
import { useSpring, animated } from "react-spring";
import UserPost from "./EmployeeData";

import { employee } from "../../services/User-Service";
import { useRecoilState } from "recoil";
import { idState } from  "../../services/RecoilState";
import { deleteEmploy } from "../../services/User-Service";
import Base from "../Base";
function EmployeeList() {
    const [id, setId] = useRecoilState(idState);
  const [employeeContent, setEmployeeContent] = useState({
    totalElements: 0,
    employee: [],
    totalPages: "",
    name: "",
    emailAddress: "",
    about: "",
  });

  useEffect(() => {
    const email = localStorage.getItem("userEmail");
    const userId = localStorage.getItem("userId");
    console.log("My email is", email);
    console.log("My Password is", userId);
    loadEmployees(userId);
  }, []);

  function loadEmployees(userId) {
    console.log("User value is", id);
    employee(userId)
      .then((response) => {
        console.log("yaha kya dikkat ho rhi hai", response);

        setEmployeeContent((prevEmployeeContent) => ({
          ...prevEmployeeContent,
          ...response,
        }));
        console.log("Employee m dekjhte hai kya hai", employeeContent);
      })
      .catch((error) => {
        console.error(error.response);
        toast.error(error.response.data.message);
      });
  }

  function deleteEmployee(employee) {
    console.log("Employee is:", employee);
    deleteEmploy(employee.id)
      .then((res) => {
        console.log(res);
        toast.success("Employee deleted successfully.");
        console.log("1");
        let newEmployeeContent = employeeContent.employee.filter(
          (p) => p.id !== employee.id
        );
        console.log("2", newEmployeeContent);
        setEmployeeContent({
          ...employeeContent,
          employee: newEmployeeContent,
          totalElements: employeeContent.employee.totalElements - 1,
        });
        console.log("3", employeeContent);
        console.log("3", employeeContent.employee);
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error in deleting post");
      });
  }

  return (
    <Base>
    <div className="container-fluid">
      <Row className="align-items-center">
      <Link to="/user/dashboard">
                      <Button color="info" size="lg" className="mr-2 mx-2">
                        Back
                      </Button>
                    </Link>
        <Col md="auto">
          <h1 className="blogs-count-heading">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Employee
            Count:
          </h1>
        </Col>
        <Col>
          <animated.h1 className="animated-count">
            {employeeContent?.employee.length}
          </animated.h1>
        </Col>
        <Button color="primary" size="lg">
            <Link
              to="/addemployee"
              style={{ color: "inherit", textDecoration: "inherit" }}
            >
              Add Employee
            </Link>
          </Button>
          &nbsp;&nbsp;
      </Row>
      <Row className="align-items-center">
        <Col></Col>
        <Col md="auto"></Col>
        <Col></Col>
      </Row>

      <Row>
        <Col md="auto"></Col>
        <Col></Col>
        <Col md="auto"></Col>

        <Col md={{ size: 12 }}>
          {employeeContent?.employee.map((employee) => (
            <UserPost
              deleteEmployee={deleteEmployee}
              employee={employee}
              key={employee.id}
            />
          ))}
        </Col>
      </Row>
    </div>
    </Base>
  );
}

export default EmployeeList;
