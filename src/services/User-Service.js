import axios from 'axios';
export const BASE_URL =process.env.REACT_APP_API_KEY;

// Create an Axios instance with the base URL
export const myAxios = axios.create({
  baseURL: BASE_URL,
});
export const signUp = (user) => {
  return myAxios.post("/user/signup", user).then((response) => response.data);
};



export const googleSignUp = (code) => {
  console.log("Hahha code is", code);
  console.log("Request  is", `/user/googlesignupprocess/${code}`);
  return myAxios
    .get(`/user/account/googlesignupprocess/${code}`)
    .then((response) => response.data);
};

export const googleLogin = (code) => {
  console.log("Hahha code is", `/account/google/login/${code}`);
  return myAxios
    .get(`/user/google/login/${code}`)
    .then((response) => response.data);
};
export const getUser = (userId) => {
  return myAxios.get(`/user/account/${userId}`).then((resp) => resp.data);
};
export const login = (user) => {
  return myAxios.post("/user/login", user).then((response) => response.data);
};

export const resetPassword = (user) => {
  return myAxios.post("/user/reset", user).then((response) => response.data);
};

export const forgotPassword = (user) => {
  return myAxios
    .post("/account/forgot", user)
    .then((response) => response.data);
};

export const updatePassword = (user) => {
  console.log("Your user is:", user);
  return myAxios.put(`/user/update`, user).then((resp) => resp.data);
};

export const cancelUpdatePassword = (userId) => {
  console.log("Your user is:", userId);
  return myAxios
    .get(`/account/cancelpassword/${userId}`)
    .then((resp) => resp.data);
};

export const updateUser = (user) => {
  return myAxios.put(`/user`, user).then((resp) => resp.data);
};

export const getBackgroundImage = () => {
  return myAxios.get(`/post/image/background.png`).then((resp) => resp.data);
};

export const employee = (userId) => {
  console.log("Hahha code is", `/user/${userId}`);
  return myAxios
    .get(BASE_URL + `user/${userId}`)
    .then((response) => response.data);
};
export const vendor = (userId) => {
  console.log("Hahha code is", `/user/${userId}`);
  return myAxios
    .get(BASE_URL + `user/${userId}`)
    .then((response) => response.data);
};

export const addEmployee = (data,userId) => {
  return myAxios
    .post(BASE_URL + `employee/add/${userId}`, data)
    .then((response) => response.data);
};
export const updateEmployee = (data,userId,employeeId) => {
  return myAxios
    .put(BASE_URL + `employee/update?userId=${userId}&employeeId=${employeeId}`, data)
    .then((response) => response.data);
};
export const deleteEmploy = (userId) => {
  return myAxios
    .delete(BASE_URL + `employee/${userId}`)
    .then((response) => response.data);
};
export const employeeDetails = (employeeId) => {
  console.log("Hahha code is", `/employee/${employeeId}`);
  return myAxios
    .get(BASE_URL + `employee/${employeeId}`)
    .then((response) => response.data);
};
export const vendorDetails = (vendorId) => {
  console.log("Hahha code is", `/vendor/${vendorId}`);
  return myAxios
    .get(BASE_URL + `vendor/${vendorId}`)
    .then((response) => response.data);
};
export const addVendor = (data,userId) => {
  return myAxios
    .post(BASE_URL + `vendor/add/${userId}`, data)
    .then((response) => response.data);
};

export const deleteVendor = (userId) => {
  return myAxios
    .delete(BASE_URL + `vendor/${userId}`)
    .then((response) => response.data);
};

export const updateVendor = (data,userId,vendorId) => {
  return myAxios
    .put(BASE_URL + `vendor/update?userId=${userId}&vendorId=${vendorId}`, data)
    .then((response) => response.data);
};

export const shareVendors = (data) => {
  console.log("Your data is::",data);
  return myAxios
    .post(BASE_URL + `vendor/share`, data)
    .then((response) => response.data);
};

export const emailData = (userId) => {
  return myAxios
    .get(BASE_URL + `vendor/email?userId=${userId}`)
    .then((response) => response.data);
};