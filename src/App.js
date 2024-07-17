import { BrowserRouter, Route, Routes } from "react-router-dom";

import Signup from "./pages/Signup";
import Login from "./pages/Login";


import "react-toastify/dist/ReactToastify.css";
import { ToastContainer, toast } from "react-toastify";
import ResetPassword from "./pages/ResetPassword";



import "./App.css";
import Home from "./pages/Home";
import EmployeeList from "./pages/employee/EmployeeList";
import AddEmployee from "./pages/employee/AddEmployee";
import UpdateEmployee from "./pages/employee/UpdateEmployee";
import { RecoilRoot } from 'recoil';
import VendorList from "./pages/vendors/VendorList";
import AddVendor from "./pages/vendors/AddVendor";
import UpdateVendor from "./pages/vendors/UpdateVendor";
import ViewEmail from "./pages/vendors/ViewEmail";


function App() {
  return (
  
      <BrowserRouter>
        <ToastContainer position="top-center" />
        <RecoilRoot>
        <Routes>
        
        <Route path="/resetpassword" element={<ResetPassword />} />
         
          <Route path="/" element={<Login />} />

          <Route path="/user/dashboard" element={<Home />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/employees" element={<EmployeeList />} />
          <Route path="/login" element={<Login />} />
          <Route path="/addemployee" element={<AddEmployee />} />
        <Route path="/updateemployee/:employeeId" element={<UpdateEmployee/>} />
        <Route path="/vendor" element={<VendorList />} />
        <Route path="/addvendor" element={<AddVendor />} />
        <Route path="/updatevendor/:vendorId" element={<UpdateVendor />} />
        <Route path="/viewemail" element={<ViewEmail />} />
        </Routes>
        </RecoilRoot>
      </BrowserRouter>
   
  );
}

export default App;
