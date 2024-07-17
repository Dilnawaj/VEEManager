import React from "react";
import { Link } from "react-router-dom";
import { Button, Card, CardBody, CardText } from "reactstrap";

function Vendor({
  vendor = {
    vendorId: 0,
    name: "This is the default employee title",
    emailAddress: "This is the default content",
    upi: "This is the default ctc"
  },
  deleteVendor,
}) {
  return (
    <Card
      className="border-0 shadow-sm mb-3"
      style={{ backgroundColor: "grey", marginBottom: "145px" }}
    >
      <CardBody style={{ display: "flex", justifyContent: "space-between" }}>
        <div className="mb-0">
          <h2>{vendor.name}</h2>
          <h5>{vendor.upi}</h5>
        </div>
        <div style={{ textAlign: "right" }}>
          <h4>{vendor.emailAddress}</h4>
        </div>
      </CardBody>
      <CardText />
      <div className="text-center">
        &nbsp;&nbsp;
        <Button onClick={() => deleteVendor(vendor)} color="danger">
          Delete
        </Button>
        &nbsp;&nbsp;
        <Button
          tag={Link}
          to={`/updatevendor/${vendor.id}`}
          color="warning"
        >
          Update {vendor.vendorId}
        </Button>
      </div>
    </Card>
  );
}

export default Vendor;
