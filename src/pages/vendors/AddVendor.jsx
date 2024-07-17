import React from "react";
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
import { useState } from "react";
import { toast } from "react-toastify";
import { Link, useNavigate } from "react-router-dom";
import { addVendor } from "../../services/User-Service";
import Base from "../Base";
import { useRecoilState } from "recoil";
import { idState } from "../../services/RecoilState";
function AddVendor() {
  const navigate = useNavigate();
  const [id, setId] = useRecoilState(idState);

  const [data, setData] = useState({
    name: "",
    upi: "",
    emailAddress: ""
  });
  const submitForm = (event) => {
    event.preventDefault();
console.log("My id",id);
addVendor(data,id)
      .then((data) => {
        // Handle the response data
        console.log("Response from CustomCategory API:", data);
        // Reset the form data

        toast.success("Vendor  added successfully");
        // Navigate to the desired location after successful submission
        navigate("/vendor");
      })
      .catch((error) => {
        toast.error(error.response.data.message);
        
      });
  };

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
              <h3>Add Vendor</h3>
            </CardHeader>
            <CardBody>
              <Form onSubmit={submitForm}>
                <FormGroup>
                  <Label for="categoryTitle">Enter Vendor name</Label>
                  <Input
                    type="text"
                    placeholder="Enter Vendor name here"
                    id="name"
                    onChange={(e) => handleChange(e, "name")}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="categoryTitle">Enter Email Address</Label>
                  <Input
                    type="email"
                    placeholder="Enter Email Address here"
                    id="email"
                    onChange={(e) => handleChange(e, "emailAddress")}
                  />
                </FormGroup>
                <FormGroup>
                  <Label for="categoryTitle">Enter upi</Label>
                  <Input
                    type="text"
                    placeholder="Enter upi here"
                    id="upi"
                    onChange={(e) => handleChange(e, "upi")}
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
                      Add
                    </Button>
                    <Link to="/vendor">
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

export default AddVendor;
