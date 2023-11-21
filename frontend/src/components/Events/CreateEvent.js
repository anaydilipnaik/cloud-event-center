import React, { useState } from "react";
import Button from "@mui/material/Button";
import {
  Grid,
  TextField,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  MenuItem,
} from "@mui/material";
import { AdapterDateFns } from "@mui/x-date-pickers/AdapterDateFns";
import { LocalizationProvider } from "@mui/x-date-pickers/LocalizationProvider";
import { DateTimePicker } from "@mui/x-date-pickers/DateTimePicker";
import { createEvent } from "../../controllers/events";

export default function CreateEvent({ open, handleClose, userId }) {
  const [title, setTitle] = useState(null);
  const [description, setDescription] = useState(null);
  const [startTime, setStartTime] = useState(new Date());
  const [endTime, setEndTime] = useState(new Date());
  const [deadline, setDeadline] = useState(new Date());
  const [address1, setAddress1] = useState(null);
  const [address2, setAddress2] = useState(null);
  const [city, setCity] = useState(null);
  const [state, setState] = useState(null);
  const [zip, setZip] = useState(null);
  const [minParticipants, setMinParticipants] = useState(null);
  const [maxParticipants, setMaxParticipants] = useState(null);
  const [fee, setFee] = useState(null);
  const [admissionPolicy, setAdmissionPolicy] = useState(null);
  const [isSubmitted, setIsSubmitted] = useState(false);

  const onSubmit = (e) => {
    e.preventDefault();
    setIsSubmitted(true);
    let data = {};
    data.title = title;
    data.description = description;
    data.startTime = startTime.toISOString().replace("Z", "");
    data.endTime = endTime.toISOString().replace("Z", "");
    data.deadline = deadline.toISOString().replace("Z", "");
    data.street = address1;
    data.number = address2;
    data.city = city;
    data.state = state;
    data.zip = zip;
    data.minParticipants = minParticipants;
    data.maxParticipants = maxParticipants;
    data.fee = fee;
    data.approvalReq = admissionPolicy;
    data.organizerID = userId;
    console.log(data);
    createEvent(data)
      .then((res) => {
        console.log(res);
        setIsSubmitted(false);
        if ((res.status = 200)) handleClose();
      })
      .catch((err) => {
        console.log(err);
        setIsSubmitted(false);
      });
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <form onSubmit={onSubmit}>
          <DialogTitle>Create Event</DialogTitle>
          <DialogContent>
            <Grid
              container
              direction="column"
              spacing={2}
              sx={{ marginTop: "5px" }}
            >
              <Grid item>
                <TextField
                  required
                  label="Title"
                  fullWidth
                  variant="outlined"
                  autoFocus
                  onChange={(e) => setTitle(e.target.value)}
                />
              </Grid>
              <Grid item>
                <TextField
                  required
                  label="Description"
                  fullWidth
                  variant="outlined"
                  onChange={(e) => setDescription(e.target.value)}
                />
              </Grid>
              <Grid item container direction="row" md={12} spacing={2}>
                <Grid item md={4}>
                  <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DateTimePicker
                      renderInput={(props) => <TextField {...props} />}
                      label="Start time"
                      value={startTime}
                      onChange={(newValue) => {
                        setStartTime(newValue);
                      }}
                    />
                  </LocalizationProvider>
                </Grid>
                <Grid item md={4}>
                  <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DateTimePicker
                      renderInput={(props) => <TextField {...props} />}
                      label="End time"
                      value={endTime}
                      onChange={(newValue) => {
                        setEndTime(newValue);
                      }}
                    />
                  </LocalizationProvider>
                </Grid>
                <Grid item md={4}>
                  <LocalizationProvider dateAdapter={AdapterDateFns}>
                    <DateTimePicker
                      renderInput={(props) => <TextField {...props} />}
                      label="Deadline"
                      value={deadline}
                      onChange={(newValue) => {
                        setDeadline(newValue);
                      }}
                    />
                  </LocalizationProvider>
                </Grid>
              </Grid>
              <Grid item container direction="row" spacing={2}>
                <Grid item md={6}>
                  <TextField
                    required
                    label="Address Line 1"
                    fullWidth
                    variant="outlined"
                    onChange={(e) => setAddress1(e.target.value)}
                  />
                </Grid>
                <Grid item md={6}>
                  <TextField
                    label="Address Line 2"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setAddress2(e.target.value)}
                  />
                </Grid>
              </Grid>
              <Grid item container direction="row" spacing={2}>
                <Grid item md={4}>
                  <TextField
                    required
                    label="City"
                    fullWidth
                    variant="outlined"
                    onChange={(e) => setCity(e.target.value)}
                  />
                </Grid>
                <Grid item md={4}>
                  <TextField
                    required
                    label="State"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setState(e.target.value)}
                  />
                </Grid>
                <Grid item md={4}>
                  <TextField
                    required
                    label="Zip Code"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setZip(e.target.value)}
                  />
                </Grid>
              </Grid>
              <Grid item container direction="row" md={12} spacing={2}>
                <Grid item md={6}>
                  <TextField
                    required
                    label="Min participants"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setMinParticipants(e.target.value)}
                  />
                </Grid>
                <Grid item md={6}>
                  <TextField
                    required
                    label="Max participants"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setMaxParticipants(e.target.value)}
                  />
                </Grid>
              </Grid>
              <Grid item container direction="row" md={12} spacing={2}>
                <Grid item md={6}>
                  <TextField
                    required
                    label="Fee"
                    fullWidth
                    autoComplete="family-name"
                    variant="outlined"
                    onChange={(e) => setFee(e.target.value)}
                  />
                </Grid>
                <Grid item md={6}>
                  <TextField
                    fullWidth
                    select
                    label="Admission policy"
                    onChange={(e) => setAdmissionPolicy(e.target.value)}
                  >
                    <MenuItem value={false}>First come first served</MenuItem>
                    <MenuItem value={true}>Approval required</MenuItem>
                  </TextField>
                </Grid>
              </Grid>
            </Grid>
          </DialogContent>
          <DialogActions>
            <Button
              variant="contained"
              sx={{ textTransform: "none" }}
              onClick={handleClose}
            >
              Close
            </Button>
            <Button
              type="submit"
              variant="contained"
              sx={{ textTransform: "none" }}
              disabled={isSubmitted ? true : false}
            >
              {isSubmitted ? "Please wait.." : "Create"}
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </div>
  );
}
