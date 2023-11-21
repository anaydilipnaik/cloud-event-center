import React, { useState, useEffect } from "react";
import { TextField, Typography, Button, Grid, MenuItem } from "@mui/material";
import { AuthConsumer } from "../contexts/Auth/AuthContext";
import { getUserDetails, updateUserDetails } from "../../controllers/user";
import Snackbar from "@mui/material/Snackbar";
import MuiAlert from "@mui/material/Alert";

/*
1) Email: a valid email address. Must be unique and cannot be changed. 
Beside identification within the service, it is used for verification, notification, and communication purposes too. 

2) Account Type: Person or Organization. 

3) Full Name: Required for all users. For simplicity, we can use one string to capture first, middle, and last names. 

4) Screen Name: also string type, and must be unique among all screen names. An org’s screen name must equal to 
its full name, or start with the full name with an optional suffix to achieve uniqueness. 

5) Gender: optional, and applies to persons only.

6) Description: optional text to describe the user.

7) Address: Street and number (optional), City, State, Zip Code. We are assuming US addresses only. 
You can provide default values for the latter three to simplify the registration process.
*/

const Alert = React.forwardRef(function Alert(props, ref) {
  return <MuiAlert elevation={6} ref={ref} variant="filled" {...props} />;
});

const UpdateUser = ({ user }) => {
  const [fullName, setFullName] = useState(null);
  const [screenName, setScreenName] = useState(null);
  const [gender, setGender] = useState(null);
  const [description, setDescription] = useState(null);
  const [address1, setAddress1] = useState(null);
  const [address2, setAddress2] = useState(null);
  const [city, setCity] = useState(null);
  const [state, setState] = useState(null);
  const [zipcode, setZipcode] = useState(null);
  const [userDetails, setUserDetails] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  const [isSubmitted, setIsSubmitted] = useState(false);
  // Hook for the MUI snackbar alert
  const [open, setOpen] = useState(false);

  const handleClose = (e) => {
    e.preventDefault();
    setOpen(false);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setIsSubmitted(true);
    let data = {};
    if (fullName) data.fullName = fullName;
    else data.fullName = userDetails.fullName;
    if (screenName) data.screenName = screenName;
    else data.screenName = userDetails.screenName;
    if (gender) data.gender = gender;
    else data.gender = userDetails.gender;
    if (description) data.description = description;
    else data.description = userDetails.description;
    if (address1) data.street = address1;
    else data.street = userDetails.street;
    if (address2) data.number = address2;
    else data.number = userDetails.number;
    if (city) data.city = city;
    else data.city = userDetails.city;
    if (state) data.state = state;
    else data.state = userDetails.state;
    if (zipcode) data.zip = zipcode;
    else data.zip = userDetails.zip;
    updateUserDetails(user.id, data)
      .then((res) => {
        console.log(res);
        setIsSubmitted(false);
        if ((res.status = 200))
          setSuccessMessage("Successfully updated user details.");
      })
      .catch((err) => {
        console.log(err);
        setIsSubmitted(false);
        setErrorMessage("Something went wrong");
        setOpen(true);
      });
  };

  useEffect(() => {
    if (user) {
      getUserDetails(user.id)
        .then((res) => setUserDetails(res.data))
        .catch((err) => console.log(err));
    }
  }, [user]);

  return (
    <Grid
      container
      direction="row"
      style={{
        width: "100%",
        height: "100%",
        background: "#ffffff",
      }}
    >
      <Grid
        item
        container
        direction="column"
        xs={12}
        sm={12}
        md={12}
        lg={12}
        xl={12}
      >
        <Grid item>
          <Typography
            style={{
              height: "45px",
              fontFamily: "Work Sans",
              fontStyle: "normal",
              fontWeight: "bold",
              fontSize: "30px",
              color: "#000000",
            }}
          >
            Update your user information.
          </Typography>
        </Grid>
        <Snackbar
          open={errorMessage}
          autoHideDuration={6000}
          onClose={handleClose}
        >
          <Alert severity="error" sx={{ width: "100%" }}>
            {errorMessage}
          </Alert>
        </Snackbar>
        {userDetails ? (
          <form onSubmit={handleSubmit}>
            <Grid
              container
              direction="column"
              spacing={2}
              style={{ marginTop: "25px" }}
            >
              <Grid item container direction="row" spacing={2}>
                <Grid item md={4}>
                  <TextField
                    required
                    label="Full Name"
                    fullWidth
                    autoComplete="given-name"
                    variant="outlined"
                    autoFocus
                    onChange={(e) => setFullName(e.target.value)}
                    defaultValue={userDetails.fullName}
                  />
                </Grid>
                <Grid item md={4}>
                  <TextField
                    label="Email"
                    fullWidth
                    autoComplete="given-name"
                    variant="outlined"
                    disabled
                    defaultValue={userDetails.email}
                  />
                </Grid>
                <Grid item md={4}>
                  <TextField
                    fullWidth
                    select
                    label="Account type"
                    disabled
                    defaultValue={
                      userDetails.roles[0].name === "ROLE_PERSON"
                        ? "PERSON"
                        : "ORGANIZER"
                    }
                  >
                    <MenuItem value="PERSON">Person</MenuItem>
                    <MenuItem value="ORGANIZER">Organizer</MenuItem>
                  </TextField>
                </Grid>
              </Grid>
              <Grid item container direction="row" spacing={2}>
                <Grid item md={6}>
                  <TextField
                    required
                    label="Screen Name"
                    fullWidth
                    autoComplete="given-name"
                    variant="outlined"
                    onChange={(e) => setScreenName(e.target.value)}
                    defaultValue={userDetails.screenName}
                  />
                </Grid>
                <Grid item md={6}>
                  <TextField
                    fullWidth
                    select
                    label="Gender"
                    onChange={(e) => setGender(e.target.value)}
                    defaultValue={userDetails.gender}
                  >
                    <MenuItem value={"male"}>Male</MenuItem>
                    <MenuItem value={"female"}>Female</MenuItem>
                  </TextField>
                </Grid>
              </Grid>
              <Grid item>
                <TextField
                  label="Description"
                  fullWidth
                  multiline
                  rows="3"
                  autoComplete="family-name"
                  variant="outlined"
                  onChange={(e) => setDescription(e.target.value)}
                  defaultValue={userDetails.description}
                />
              </Grid>
              <Grid item container direction="row" spacing={2}>
                <Grid item md={6}>
                  <TextField
                    label="Street"
                    fullWidth
                    autoComplete="given-name"
                    variant="outlined"
                    onChange={(e) => setAddress1(e.target.value)}
                    defaultValue={
                      userDetails.address && userDetails.address.street
                        ? userDetails.address.street
                        : null
                    }
                  />
                </Grid>
                <Grid item md={6}>
                  <TextField
                    label="Apt no"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setAddress2(e.target.value)}
                    defaultValue={
                      userDetails.address && userDetails.address.number
                        ? userDetails.address.number
                        : null
                    }
                  />
                </Grid>
              </Grid>
              <Grid item container direction="row" spacing={2}>
                <Grid item md={4}>
                  <TextField
                    label="City"
                    fullWidth
                    autoComplete="given-name"
                    variant="outlined"
                    onChange={(e) => setCity(e.target.value)}
                    defaultValue={
                      userDetails.address && userDetails.address.city
                        ? userDetails.address.city
                        : null
                    }
                  />
                </Grid>
                <Grid item md={4}>
                  <TextField
                    label="State"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setState(e.target.value)}
                    defaultValue={
                      userDetails.address && userDetails.address.state
                        ? userDetails.address.state
                        : null
                    }
                  />
                </Grid>
                <Grid item md={4}>
                  <TextField
                    label="Zip Code"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setZipcode(e.target.value)}
                    defaultValue={
                      userDetails.address && userDetails.address.state
                        ? userDetails.address.state
                        : null
                    }
                  />
                </Grid>
              </Grid>
              <Grid item>
                <Button
                  type="submit"
                  fullWidth
                  style={{
                    width: "100%",
                    height: "55px",
                    cursor: "pointer",
                    background: "#1F51FF",
                    borderRadius: "5px",
                    color: "#ffffff",
                    textTransform: "none",
                    fontFamily: "Work Sans",
                    fontStyle: "normal",
                    fontWeight: "bold",
                    fontSize: "18px",
                  }}
                  disabled={isSubmitted ? true : false}
                >
                  {isSubmitted ? "Please wait.." : "Save"}
                </Button>
              </Grid>
            </Grid>
            <Snackbar
              open={successMessage}
              autoHideDuration={6000}
              onClose={handleClose}
            >
              <Alert severity="success" sx={{ width: "100%" }}>
                {successMessage}
              </Alert>
            </Snackbar>
          </form>
        ) : null}
      </Grid>
    </Grid>
  );
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <UpdateUser user={user} {...props} />}
  </AuthConsumer>
);
