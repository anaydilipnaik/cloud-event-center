import { Redirect, Route } from "react-router-dom";

const PrivateRoute = ({ redirectPath = "/login", children }) => {
  const isLoggedIn = localStorage.getItem("user") != null;

  const errorMessage = !isLoggedIn ? "You need to be logged in" : null;

  const shouldRedirect = isLoggedIn;

  return (
    <Route
      render={({ location }) =>
        shouldRedirect ? (
          children
        ) : (
          <Redirect
            to={{
              pathname: redirectPath,
              state: { from: location, errorMessage: errorMessage },
            }}
          />
        )
      }
    />
  );
};

export default PrivateRoute;
