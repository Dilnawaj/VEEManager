import React from "react";
import Base from "../Base";
import {
  Card,
  CardBody,
  CardHeader,
  Container,
  FormGroup,
  Input,
  Label,
  Form,
  Button,
} from "reactstrap";
import { useState, useEffect } from "react";
import { toast } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";
import { addEmployee } from "../../services/User-Service";
import { useRecoilState } from "recoil";
import { idState } from "../../services/RecoilState";
import { useParams } from "react-router";
import {employeeDetails,updateEmployee} from "../../services/User-Service";
function UpdateEmployee() {
  const navigate = useNavigate();
  const [id, setId] = useRecoilState(idState);
  const { employeeId } = useParams();
  const [data, setData] = useState({

    name: "",
    designation: "",
    emailAddress: "",
    ctc: "",
  });


  const submitForm = (event) => {
    event.preventDefault();

    updateEmployee(data, id,employeeId)
      .then((data) => {
        // Handle the response data
        console.log("Response from CustomCategory API:", data);
        // Reset the form data

        toast.success("Employee  added successfully");
        // Navigate to the desired location after successful submission
        navigate("/employees");
      })
      .catch((error) => {
        // Handle the error scenario
        toast.error("An error occurred while adding Employee.");
      });
  };
  useEffect(() => {
    employeeDetails(employeeId)
    .then((data) => {
      setData(() => ({
        ...data
      }));
console.log("data now",data)
      
    })
    .catch((error) => {
      console.log("Error in fetching details", error);
    });
  },[employeeDetails,employeeId]);

  const handleChange = (event, field) => {
    const value = event.target.value;
    setData({ ...data, [field]: value });
  };

  return (
    <Base>
      <div
        style={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          backgroundSize: "104% auto",
          backgroundPosition: "left center",
          backgroundRepeat: "no-repeat",
          height: "100vh",
        }}
      >
        <Container>
          <Card
            inverse
            style={{
              backgroundColor: "#454545",
              marginBottom: "90px",
              width: "900px",
              height: "500px",
              display: "flex",
              justifyContent: "center",
              marginLeft: "120px",
            }}
          >
            <CardHeader>
              <h3>Update Employee</h3>
            </CardHeader>
            <CardBody>
              <Form onSubmit={submitForm}>
                <FormGroup>
                  <Label for="categoryTitle">
                    Enter Employee name  (id:{employeeId})
                  </Label>
                  <Input
                    type="text"
                    placeholder="Enter Employee name here"
                    id="name"
                    value={data.name}
                    onChange={(e) => handleChange(e, "name")}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="categoryTitle">Enter Email Address</Label>
                  <Input
                    type="text"
                    placeholder="Enter Email Address here"
                    id="email"
                    value={data.emailAddress}
                    onChange={(e) => handleChange(e, "emailAddress")}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="categoryTitle">Enter Designation</Label>
                  <Input
                    type="text"
                    placeholder="Enter Designation here"
                    id="designation"
                    value={data.designation}
                    onChange={(e) => handleChange(e, "designation")}
                  />
                </FormGroup>

                <FormGroup>
                  <Label for="categoryTitle">Enter CTC</Label>
                  <Input
                    type="text"
                    placeholder="Enter CTC here"
                    id="ctc"
                    value={data.ctc}
                    onChange={(e) => handleChange(e, "ctc")}
                  />
                </FormGroup>

                <Container className="text-center">
                  <div className="d-flex justify-content-center">
                    <Button
                      color="info"
                      size="lg"
                      className="mr-2 mx-2"
                      type="submit"
                    >
                      Update
                    </Button>
                    <Link to="/employees">
                      <Button color="secondary" size="lg" className="mx-2">
                        Cancel
                      </Button>
                    </Link>
                  </div>
                </Container>
              </Form>
            </CardBody>
          </Card>
        </Container>
      </div>
    </Base>
  );
}

export default UpdateEmployee;
