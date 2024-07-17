import React, { useState, useEffect } from "react";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";
import { useRecoilState } from "recoil";
import { idState } from "../../services/RecoilState";
import { vendor, deleteVendor, shareVendors } from "../../services/User-Service";
import Base from "../Base";
import Vendor from "./Vendor";
import {
  Button,
  Card,
  CardBody,
  CardText,
  Col,
  Container,
  Input,
  Row,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter,
} from "reactstrap";

function VendorList() {
  const [id, setId] = useRecoilState(idState);
  const [isEmailModalOpen, setIsEmailModalOpen] = useState(false);
  const [emailInput, setEmailInput] = useState("");
  const [upiInput, setUpiInput] = useState("");
  const [emailList, setEmailList] = useState([]);
  const [vendorContent, setVendorContent] = useState({
    totalElements: 0,
    vendor: [],
    totalPages: "",
    name: "",
    emailAddress: "",
    about: "",
  });

  useEffect(() => {
    const email = localStorage.getItem("userEmail");
    const userId = localStorage.getItem("userId");
    console.log("My email is", email);
    console.log("My User ID is", userId);
    loadVendors(userId);
  }, []);

  const addEmailAndUpi = () => {
    if (emailInput.trim() !== "" && upiInput.trim() !== "") {
      setEmailList([...emailList, { email: emailInput.trim(), upi: upiInput.trim() }]);
      setEmailInput("");
      setUpiInput("");
    }
  };

  const removeEmailAndUpi = (index) => {
    const updatedList = [...emailList];
    updatedList.splice(index, 1);
    setEmailList(updatedList);
  };

  const shareEmailtoVendors = () => {
 
      const shareEmailRequest = {
      emails: emailList,
      id:id
    };

    shareVendors(shareEmailRequest)
      .then((data) => {
        console.log("bebo", data);
        toast.success("Email successfully sent to vendors");
        setIsEmailModalOpen(false); // Close the modal here
      })
      .catch((error) => {
        console.log(error);
        toast.error("Error in sending email ");
      });
  };

  function loadVendors(userId) {
    console.log("User value is", id);
    vendor(userId)
      .then((response) => {
        console.log("Response from vendor API", response);
        setVendorContent((prevVendorContent) => ({
          ...prevVendorContent,
          ...response,
        }));
      })
      .catch((error) => {
        console.error(error.response);
        toast.error(error.response.data.message);
      });
  }

  function deleteVendorData(vendor) {
    console.log("Vendor to delete is:", vendor);
    deleteVendor(vendor.id)
      .then((res) => {
        console.log("Delete response", res);
        toast.success("Vendor deleted successfully.");
        let newVendorContent = vendorContent.vendor.filter(
          (p) => p.id !== vendor.id
        );
        setVendorContent((prevVendorContent) => ({
          ...prevVendorContent,
          vendor: newVendorContent,
          totalElements: prevVendorContent.totalElements - 1,
        }));
      })
      .catch((error) => {
        console.error("Error deleting vendor", error);
        toast.error("Error in deleting vendor");
      });
  }

  const toggleEmailModal = () => {
    setIsEmailModalOpen(!isEmailModalOpen);
  };

  return (
    <Base>
      <div className="container-fluid">
        <Row className="align-items-center">
          <Link to="/user/dashboard">
            <Button color="info" size="lg" className="mr-2 mx-2">
              Back
            </Button>
      
          </Link>
          <Button color="primary" size="lg" onClick={toggleEmailModal}>
          {"  "}
          Share
        </Button>
          <Col md="auto">
            <h1 className="blogs-count-heading">
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Vendor
              Count:
            </h1>
          </Col>
          <Col>
            <h1 className="animated-count">{vendorContent?.vendor.length}</h1>
            
          </Col>
          
          <Button color="primary" size="lg">
            <Link
              to="/addvendor"
              style={{ color: "inherit", textDecoration: "inherit" }}
            >
              Add Vendor
            </Link>
            
          </Button>
          <Link to="/viewemail">
                      <Button color="info" size="lg" className="mr-2 mx-2">
                        ViewEmail
                      </Button>
                    </Link>
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
            {vendorContent?.vendor.map((vendor) => (
              <Vendor
                deleteVendor={deleteVendorData}
                vendor={vendor}
                key={vendor.id}
              />
            ))}
            <Modal isOpen={isEmailModalOpen} toggle={toggleEmailModal}>
              <ModalHeader toggle={toggleEmailModal}>Enter Email and UPI</ModalHeader>
              <ModalBody>
                <Input
                  type="text"
                  placeholder="Enter email"
                  value={emailInput}
                  onChange={(e) => setEmailInput(e.target.value)}
                />
                <Input
                  type="text"
                  placeholder="Enter UPI"
                  value={upiInput}
                  onChange={(e) => setUpiInput(e.target.value)}
                />
                <Button color="primary" onClick={addEmailAndUpi}>
                  Add
                </Button>
                <ul>
                  {emailList?.map((item, index) => (
                    <li key={index}>
                      {item.email} - {item.upi}{" "}
                      <span
                        style={{ cursor: "pointer" }}
                        onClick={() => removeEmailAndUpi(index)}
                      >
                        ❌
                      </span>
                    </li>
                  ))}
                </ul>
              </ModalBody>
              <ModalFooter>
                <Button color="primary" onClick={shareEmailtoVendors}>
                  Share
                </Button>{" "}
                <Button color="secondary" onClick={toggleEmailModal}>
                  Cancel
                </Button>
              </ModalFooter>
            </Modal>
          </Col>
        </Row>
      </div>
    </Base>
  );
}

export default VendorList;
