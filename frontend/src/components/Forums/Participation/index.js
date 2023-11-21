import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import { Avatar, Grid, Paper } from "@mui/material";
import { getEventDetails } from "../../../controllers/events";
import { getQuestionsByEvent, canPostToForum, closeForum } from "../../../controllers/participationForum";
import { AuthConsumer } from "../../contexts/Auth/AuthContext";
import { Button } from "@mui/material";
import PostNewQuestion from "./PostNewQuestion";
import CancelForum from "./CancelForum";

const imgLink =
  "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

const ParticipationForum = ({ user }) => {

  const [questions, setQuestions] = useState([]);
  const [eventDetails, setEventDetails] = useState(null);
  const [openModal, setOpenModal] = useState(false);
  const [ openCancelForumModal, setOpenCancelForumModal ] = useState(false);

  const search = useLocation().search;

  const handleClose = () => {
    setOpenModal(false);
    getEventDetailsFunc();
  };

  const handleCancelForumClose = () => {
    setOpenCancelForumModal(false);
    getEventDetailsFunc();
  };

  const getEventDetailsFunc = () => {
    const eventId = new URLSearchParams(search).get("eventId");
    getEventDetails(eventId)
      .then((res) => {
        setEventDetails(res.data);
        return getQuestionsByEvent(eventId, user.id);
      })
      .then((res) => {
        setQuestions(res.data);
      })
      .catch((err) => console.log(err));
  };

  // const closeParticipantForum = (userId, eventId, text) => {
  //   closeForum(userId, eventId, text)
  //   .then(() => {
  //     alert("Forum closed");
  //     window.location.reload();
  //   }).catch((err) => {
  //     alert("Failed to close forum. Please try again");
  //     window.location.reload();
  //   })
  // };

  useEffect(() => {
    if (user) 
      getEventDetailsFunc();
  }, [user]);

  return eventDetails && questions ? (
    <div style={{ padding: 14 }} className="App">
      <h1
        style={{
          marginBottom: 50,
        }}
      >
        Participant Forum ({eventDetails.title})
        {
          canPostToForum(eventDetails) || true ?
          <Button
            variant="contained"
            sx={{ marginLeft: "350px" }}
            onClick={() => setOpenModal(true)}
          >
            Add a question
          </Button>
          : 
          null
        }
      </h1>

      <div>
        {
          eventDetails && eventDetails.pForumOpen === false && eventDetails.pForumCancelDesc ?
          (
            <div>
              <p> Forum closed: {eventDetails.pForumCancelDesc}</p>
            </div>
          ) : null
        }
      </div>

      <div>
        {
          true || eventDetails && eventDetails.pForumOpen === true && eventDetails.organizer.id === user.id ?
          (
            <div>
              <Button
              // onClick = {() => closeParticipantForum(eventDetails.organizer.id, eventDetails.id, "Forum manually closed")}
              onClick = {() => setOpenCancelForumModal(true)}
              >
                Close Participant Forum
              </Button>
            </div>
          ) : null
        }
      </div>
      {
      questions.map((item, index) => (
        <Paper
          key = {index}
          style={{
            padding: "40px 20px",
            marginTop: 25,
            cursor: "pointer",
            background:
              item.user.id === eventDetails.organizer.id ? "#E5E4E2" : null,
          }}
          onClick={() => {
            window.location.href =
              "/participation-forum-comments?questionId=" +
              item.id +
              "&text=" +
              item.text +
              "&screenName=" +
              item.user.screenName + 
              "&eventId=" + 
              item.event.id
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
      <CancelForum
      open = {openCancelForumModal}
      handleClose = {handleCancelForumClose}
      eventId = {eventDetails.id}
      eventDetails = {eventDetails}
      userId = {user.id}
      />
    </div>
  ) : null;
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <ParticipationForum user = {user} {...props} />}
  </AuthConsumer>
);
