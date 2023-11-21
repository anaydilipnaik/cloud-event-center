import React, { useState, useEffect } from "react";

const AuthContext = React.createContext();

const AuthProvider = (props) => {
  const [user, setUser] = useState(null);

  const processLogin = (loginResponse) => {
    return new Promise((resolve, reject) => {
      if (loginResponse) {
        setUser(loginResponse);
        localStorage.setItem("user", JSON.stringify(loginResponse));
        resolve("update-user");
      }
    });
  };

  const processLogout = () => {
    setUser(null);
    localStorage.removeItem("user");
    window.location.href = "/login";
  };

  useEffect(() => {
    setUser(
      localStorage.getItem("user")
        ? JSON.parse(localStorage.getItem("user"))
        : null
    );
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user: user,
        processLogin: processLogin,
        processLogout: processLogout,
        history: window.history,
      }}
    >
      {props.children}
    </AuthContext.Provider>
  );
};

const AuthConsumer = AuthContext.Consumer;

export { AuthProvider, AuthConsumer };
