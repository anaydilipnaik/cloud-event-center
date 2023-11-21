import React, { useState } from "react";
import Button from "@mui/material/Button";
import Dialog from "@mui/material/Dialog";
import DialogActions from "@mui/material/DialogActions";
import DialogContent from "@mui/material/DialogContent";
import DialogTitle from "@mui/material/DialogTitle";
import { Grid, TextField } from "@mui/material";
import { createQuestion } from "../../../controllers/signupForum";

export default function PostNewQuestion({
  open,
  eventId,
  handleClose,
  userId,
}) {
  const [questionText, setQuestionText] = useState(null);
  const [file, setFile] = useState(null);

  const onSubmit = (e) => {
    e.preventDefault();
    let data = {};
    data.text = questionText;
    data.userId = userId;
    if (file) data.file = file;
    createQuestion(eventId, data)
      .then(() => {
        handleClose();
      })
      .catch((err) => console.log(err));
  };

  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <form onSubmit={onSubmit}>
          <DialogTitle>Post new question</DialogTitle>
          <DialogContent>
            <Grid container>
              <Grid item>
                <TextField
                  required
                  label="Ask your question here"
                  fullWidth
                  multiline
                  sx={{ width: "500px", marginTop: "15px" }}
                  rows="3"
                  autoComplete="family-name"
                  variant="outlined"
                  onChange={(e) => setQuestionText(e.target.value)}
                />
              </Grid>
              <Grid item sx={{ marginTop: "15px" }}>
                Upload Image&nbsp;&nbsp;
                <input
                  type="file"
                  onChange={(e) => setFile(e.target.files[0])}
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
