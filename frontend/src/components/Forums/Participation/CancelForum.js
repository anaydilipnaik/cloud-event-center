import React, { useState } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { Grid, TextField } from "@mui/material";
import { closeForum } from "../../../controllers/participationForum";

export default function CancelForum({
  open,
  eventId,
  handleClose,
  userId,
}) {
  const [questionText, setQuestionText] = useState(null);

  const onSubmit = (e) => {
    e.preventDefault();
    let data = {};
    data.text = questionText;

    closeForum(userId, eventId, questionText)
      .then(() => {
        handleClose();
      })
      .catch((err) => {
        console.log("CancelForumError", err);
      });
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <form onSubmit={onSubmit}>
          <DialogTitle>Add a reason for cancellation</DialogTitle>
          <DialogContent>
            <Grid container>
              <Grid item>
                <TextField
                  required
                  label="Participation Forum Cancel description"
                  fullWidth
                  multiline
                  sx={{ width: "500px", marginTop: "15px" }}
                  rows="3"
                  autoComplete="family-name"
                  variant="outlined"
                  onChange={(e) => setQuestionText(e.target.value)}
                />
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
