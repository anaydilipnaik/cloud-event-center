import React, { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { Grid, TextField, Rating, MenuItem, Typography } from "@mui/material";
import { createParticipantReview } from "../../../controllers/reviews";
import { getEventRegistrationsByParticipant } from "../../../controllers/events";

export default function PostReview({
  open,
  participantId,
  handleClose,
  userId,
  getParticipantReviewsFunc,
}) {
  const [reviewText, setReviewText] = useState(null);
  const [rating, setRating] = useState(0);
  const [participantEvents, setParticipantEvents] = useState(null);
  const [eventId, setEventId] = useState(null);
  const [error, setError] = useState(null);

  const onSubmit = (e) => {
    e.preventDefault();
    let data = {};
    data.review = reviewText;
    data.rating = rating;
    createParticipantReview(eventId, userId, data)
      .then((res) => {
        console.log(res);
        handleClose();
        getParticipantReviewsFunc();
      })
      .catch((err) => {
        console.log(err);
        setError(err.response.data);
      });
  };

  useEffect(() => {
    if (participantId) {
      getEventRegistrationsByParticipant(participantId)
        .then((res) => {
          setParticipantEvents(res.data);
        })
        .catch((err) => console.log(err));
    }
  }, [participantId]);

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <form onSubmit={onSubmit}>
          <DialogTitle>Post new review</DialogTitle>
          <DialogContent>
            <Grid
              container
              direction={"column"}
              md={12}
              sx={{ marginTop: "15px" }}
            >
              <Grid item container direction="row">
                <Grid item md={6}>
                  <Rating
                    required
                    value={rating}
                    onChange={(event, newValue) => {
                      setRating(newValue);
                    }}
                    sx={{ marginTop: "10px" }}
                    size="large"
                  />
                </Grid>
                <Grid item md={6}>
                  <TextField
                    select
                    label="Please select an event"
                    fullWidth
                    required
                    onChange={(e) => setEventId(e.target.value)}
                  >
                    {participantEvents &&
                      participantEvents.map((item) => (
                        <MenuItem value={item.event.id}>
                          {item.event.title}
                        </MenuItem>
                      ))}
                  </TextField>
                </Grid>
              </Grid>
              <Grid item>
                <TextField
                  required
                  label="Write something..."
                  fullWidth
                  multiline
                  sx={{ width: "500px", marginTop: "15px" }}
                  rows="3"
                  autoComplete="family-name"
                  variant="outlined"
                  onChange={(e) => setReviewText(e.target.value)}
                />
              </Grid>
              {error ? (
                <Typography sx={{ color: "red" }}>{error}</Typography>
              ) : null}
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
              variant="contained"
              sx={{ textTransform: "none" }}
              type="submit"
            >
              Post
            </Button>
          </DialogActions>
        </form>
      </Dialog>
    </div>
  );
}
