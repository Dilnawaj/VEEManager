export const isLoggedIn = () => {
    try {
      console.log("bolo")
    let data = localStorage.getItem("data");
    if (data != null) {
      return true;
    } else {
    
      return false;
    }
  } catch (error) {
    return false;
  }
  };
  

  
  export const doLogin = (data, next) => {
    localStorage.setItem("data", JSON.stringify(data));
    next();
  };
  
  export const doLogout = (next) => {
    localStorage.removeItem("data");
    next();
  };