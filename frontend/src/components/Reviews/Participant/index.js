import React, { useEffect, useState } from "react";
import { Grid, Paper, Typography, Rating, Button } from "@mui/material";
import { getParticipantReviews } from "../../../controllers/reviews";
import { useLocation } from "react-router-dom";
import PostReview from "./PostReview";
import { AuthConsumer } from "../../contexts/Auth/AuthContext";

const ParticipantReviews = ({ user }) => {
  const [participantReviews, setParticipantReviews] = useState([]);
  const [openModal, setOpenModal] = useState(false);

  const search = useLocation().search;

  const handleClose = () => {
    setOpenModal(false);
  };

  const getParticipantReviewsFunc = () => {
    const participantId = new URLSearchParams(search).get("participantId");
    getParticipantReviews(participantId)
      .then((res) => setParticipantReviews(res.data))
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getParticipantReviewsFunc();
  }, []);

  return user ? (
    <>
      <Grid container sx={{ mt: 3 }} alignItems="left">
        <Grid item container direction="row" md={12}>
          <Grid item>
            <Typography
              align="left"
              md={5}
              style={{ fontSize: 35, fontWeight: 650 }}
            >
              Participant Reviews
            </Typography>
          </Grid>
          <Grid item>
            <Button
              variant="contained"
              sx={{ marginLeft: "600px" }}
              onClick={() => setOpenModal(true)}
            >
              Post a Review!
            </Button>
          </Grid>
        </Grid>
        {participantReviews && participantReviews.length === 0 ? (
          <Grid container sx={{ mt: 5 }} alignItems="center">
            <Typography
              md={5}
              style={{ fontSize: 20, fontWeight: 600, marginLeft: 330 }}
            >
              No reviews posted!
            </Typography>
          </Grid>
        ) : (
          participantReviews.map((review) => (
            <Grid container sx={{ mt: 5 }} alignItems="center">
              <Paper
                style={{
                  padding: "40px 20px",
                  marginTop: 25,
                  cursor: "pointer",
                  minWidth: "1000px",
                  marginLeft: "30px",
                }}
              >
                <Grid container wrap="nowrap" spacing={2}>
                  <Grid justifyContent="left" item xs zeroMinWidth>
                    <p>
                      <Rating value={review.rating} readOnly />
                    </p>
                    <p style={{ textAlign: "left" }}>{review.review}</p>
                    <p style={{ textAlign: "left", color: "gray" }}>
                      posted on {review.createdAt}
                    </p>
                  </Grid>
                </Grid>
              </Paper>
            </Grid>
          ))
        )}
      </Grid>
      <PostReview
        open={openModal}
        handleClose={handleClose}
        participantId={new URLSearchParams(search).get("participantId")}
        userId={user.id}
        getParticipantReviewsFunc={getParticipantReviewsFunc}
      />
    </>
  ) : null;
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <ParticipantReviews user={user} {...props} />}
  </AuthConsumer>
);
