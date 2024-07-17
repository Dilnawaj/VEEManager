import React, { useState, useEffect } from 'react';
import { emailData } from '../../services/User-Service';
import { toast } from "react-toastify";
import Base from "../Base";
import { Button } from 'reactstrap';
import { Link } from 'react-router-dom';
function ViewEmail() {
    const [emails, setEmails] = useState([]);

    useEffect(() => {
        loadEmails();
    }, []);

    function loadEmails() {
        emailData()
            .then((response) => {
                setEmails(response);
                console.log("Emails data:", response);
            })
            .catch((error) => {
                console.error(error.response);
                toast.error(error.response.data.message);
            });
    }

    return (
        <Base>
        <>   
        
        <Link to="/vendor">
                      <Button color="secondary" size="lg" className="mx-2">
                        Back
                      </Button>
                    </Link></>
          
        <div className="email-list">
            
      
            {emails.map((emailItem) => (
                <div key={emailItem.id} className="email-card">
                    <h3>{emailItem.subject}</h3>
                    <p><strong>Name:</strong> {emailItem.name}</p>
                    <p><strong>UPI:</strong> {emailItem.upi}</p>
                    <p><strong>Email:</strong> {emailItem.email}</p>
                </div>
            ))}
        </div>
        </Base>
    );
}

export default ViewEmail;
