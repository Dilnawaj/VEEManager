import React, { useEffect } from "react";
import { useState } from "react";
import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardText } from "reactstrap";
import { toast } from "react-toastify";
import { useHistory } from "react-router-dom";
function EmployeeData({
  employee = {
    employeeId: 0,
    name: "This is the default employee title",
    emailAddress: "This is the default content",
    ctc:"This is the default ctc",
    designation: "This is the default"
  },
  deleteEmployee,
}) {


  return (
    <Card
    className="border-0 shadow-sm mb-3 "
    style={{ backgroundColor: "grey", marginBottom: "145px" }}
  >
    <CardBody style={{ display: "flex", justifyContent: "space-between" }}>
      <div className="mb-0">
        <h2>{employee.name}</h2>
        <h5>{employee.ctc}</h5>
      </div>
      <div style={{ textAlign: "right" }}>
        <h4>{employee.emailAddress}</h4>
        <h5>{employee.designation}</h5>
        <h5>{employee.employeeId}</h5>
      </div>
    </CardBody>
        <CardText />
        <div className="text-center">
    
         
      
          
            <>
              &nbsp;&nbsp;
              <Button onClick={() => deleteEmployee(employee)} color="danger">
                Delete
              </Button>
              &nbsp;&nbsp;
                 <Button
                tag={Link}
                to={`/updateemployee/${employee.id}`}
                color="warning"
              >
                Update{employee.employeeId}
              </Button>
             
            </>
          
        </div>
     
    </Card>
  );
}

export default EmployeeData;
