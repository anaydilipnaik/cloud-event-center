import React, { useState, useEffect } from "react";
import { Avatar, Grid, Paper } from "@mui/material";
import { getQuestionsByEvent } from "../../../controllers/signupForum";
import { getEventDetails } from "../../../controllers/events";
import { useLocation } from "react-router-dom";
import { Button } from "@mui/material";
import PostNewQuestion from "./PostNewQuestion";
import { AuthConsumer } from "../../contexts/Auth/AuthContext";

const imgLink =
  "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

const SignupForum = ({ user }) => {
  const [questions, setQuestions] = useState(null);
  const [eventDetails, setEventDetails] = useState(null);
  const [openModal, setOpenModal] = useState(false);

  const search = useLocation().search;

  const handleClose = () => {
    setOpenModal(false);
    getEventDetailsFunc();
  };

  const getEventDetailsFunc = () => {
    const eventId = new URLSearchParams(search).get("eventId");
    getEventDetails(eventId)
      .then((res) => {
        setEventDetails(res.data);
        return getQuestionsByEvent(eventId);
      })
      .then((res) => {
        setQuestions(res.data);
      })
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getEventDetailsFunc();
  }, []);

  return eventDetails && questions ? (
    <div style={{ padding: 14 }} className="App">
      <h1
        style={{
          marginBottom: 50,
        }}
      >
        Sign Up Forum ({eventDetails.title})
        <Button
          variant="contained"
          sx={{ marginLeft: "350px" }}
          onClick={() => setOpenModal(true)}
        >
          Add a question
        </Button>
      </h1>
      {questions.map((item) => (
        <Paper
          style={{
            padding: "40px 20px",
            marginTop: 25,
            cursor: "pointer",
            background:
              item.user.id === eventDetails.organizer.id ? "#E5E4E2" : null,
          }}
          onClick={() => {
            window.location.href =
              "/signup-forum-comments?questionId=" +
              item.id +
              "&text=" +
              item.text +
              "&screenName=" +
              item.user.screenName;
          }}
        >
          <Grid container wrap="nowrap" spacing={2}>
            <Grid item>
              <Avatar alt="Remy Sharp" src={imgLink} />
            </Grid>
            <Grid justifyContent="left" item xs zeroMinWidth>
              <h4
                style={{
                  margin: 0,
                  textAlign: "left",
                }}
              >
                {item.user.screenName}{" "}
                {item.user.id === eventDetails.organizer.id
                  ? "(Organizer)"
                  : ""}
              </h4>
              <p style={{ textAlign: "left" }}>{item.text}</p>
              <p style={{ textAlign: "left" }}>
                <img src={item.imageUrl} />
              </p>
              <p style={{ textAlign: "left", color: "gray" }}>
                posted on {item.createdAt}
              </p>
            </Grid>
          </Grid>
        </Paper>
      ))}
      <PostNewQuestion
        open={openModal}
        handleClose={handleClose}
        eventId={eventDetails.id}
        eventDetails={eventDetails}
        userId={user.id}
      />
    </div>
  ) : null;
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <SignupForum user={user} {...props} />}
  </AuthConsumer>
);
