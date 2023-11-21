import React, { useState, useEffect } from "react";
import { Grid, TextField, Typography, Button } from "@mui/material";
import {
  getQuestionAnswers,
  createAnswer,
  canPostToForum,
} from "../../../controllers/participationForum";
import { useLocation } from "react-router-dom";
import { AuthConsumer } from "../../contexts/Auth/AuthContext";
import { getEventDetails } from "../../../controllers/events";

const ParticipationForumComments = ({ user }) => {
  const [answers, setAnswers] = useState(null);
  const [comment, setComment] = useState(null);
  const [eventDetails, setEventDetails] = useState(null);

  const search = useLocation().search;

  const handleSubmit = (e) => {
    e.preventDefault();
    let data = {};
    data.text = comment;
    data.userId = user.id;
    createAnswer(new URLSearchParams(search).get("questionId"), data)
      .then(() => {
        document.getElementById("commentForm").reset();
        getAnswersFunc();
      })
      .catch((err) => console.log(err));
  };

  const getAnswersFunc = () => {
    const questionId = new URLSearchParams(search).get("questionId");
    const eventId = new URLSearchParams(search).get("eventId");
    getQuestionAnswers(questionId, user.id)
      .then((res) => {
        setAnswers(res.data);
        return getEventDetails(eventId)
      }).then((res) => {
        setEventDetails(res.data);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    if (user)
      getAnswersFunc();
  }, [ user ]);

  return eventDetails ? (
    <Grid container direction="column">
      <form onSubmit={handleSubmit} id="commentForm">
        <Grid item>
          <Typography sx={{ fontSize: "24px", fontWeight: "bold" }}>
            <i>{new URLSearchParams(search).get("text")}</i>
            <p
              style={{
                textAlign: "left",
                color: "gray",
                margin: 0,
                fontSize: "18px",
              }}
            >
              asked by {new URLSearchParams(search).get("screenName")}
            </p>
          </Typography>
        </Grid>
        {
          canPostToForum(eventDetails) || true ? 
          <div>
            <Grid item sx={{ marginTop: "25px" }}>
              <TextField
                required
                variant="filled"
                multiline
                fullWidth
                rows="3"
                placeholder="Add a comment..."
                onChange={(e) => setComment(e.target.value)}
              />
            </Grid>
            <Grid
              item
              container
              direction="row"
              justifyContent={"right"}
              sx={{ marginTop: "5px" }}
              spacing={2}
            >
              <Grid item>
                <Button
                  variant="outlined"
                  sx={{ textTransform: "none" }}
                  size="large"
                  type="submit"
                >
                  Comment
                </Button>
              </Grid>
            </Grid>
          </div>
          :
          null
        }

        <Grid item>
          <Typography>
            <h1>All Comments</h1>
          </Typography>
        </Grid>
        {answers &&
          answers.map((item, index) => (
            <Grid
              key = {index}
              item
              container
              direction="column"
              sx={{ marginBottom: "25px", marginLeft: "10px" }}
            >
              <Grid item justifyContent="left" xs zeroMinWidth>
                <h4
                  style={{
                    margin: 0,
                    textAlign: "left",
                    fontWeight: "bold",
                    fontSize: "18px",
                    marginBottom: "10px",
                  }}
                >
                  {item.user.screenName}
                </h4>
              </Grid>
              <Grid item style={{ textAlign: "left", marginBottom: "5px" }}>
                {item.text}
              </Grid>
              <Grid item style={{ textAlign: "left", color: "gray" }}>
                commented on {item.createdAt}
              </Grid>
            </Grid>
          ))}
      </form>
    </Grid>
  ) : null;
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <ParticipationForumComments user={user} {...props} />}
  </AuthConsumer>
);

