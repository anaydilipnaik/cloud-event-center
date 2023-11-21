import React, { useEffect, useState } from "react";
import { Grid, Paper, Typography, Rating, Button } from "@mui/material";
import { getOrganizerReviews } from "../../../controllers/reviews";
import { useLocation } from "react-router-dom";
import PostReview from "./PostReview";
import { AuthConsumer } from "../../contexts/Auth/AuthContext";

const OrganizerReviews = ({ user }) => {
  const [organizerReviews, setOrganizerReviews] = useState([]);
  const [openModal, setOpenModal] = useState(false);

  const search = useLocation().search;

  const handleClose = () => {
    setOpenModal(false);
  };

  const getOrganizerReviewsFunc = () => {
    const organizerId = new URLSearchParams(search).get("organizerId");
    getOrganizerReviews(organizerId)
      .then((res) => setOrganizerReviews(res.data))
      .catch((err) => console.log(err));
  };

  useEffect(() => {
    getOrganizerReviewsFunc();
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
              Organizer Reviews
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
        {organizerReviews && organizerReviews.length === 0 ? (
          <Grid container sx={{ mt: 5 }} alignItems="center">
            <Typography
              md={5}
              style={{ fontSize: 20, fontWeight: 600, marginLeft: 330 }}
            >
              No reviews posted!
            </Typography>
          </Grid>
        ) : (
          organizerReviews.map((review) => (
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
        organizerId={new URLSearchParams(search).get("organizerId")}
        userId={user.id}
        getOrganizerReviewsFunc={getOrganizerReviewsFunc}
      />
    </>
  ) : null;
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <OrganizerReviews user={user} {...props} />}
  </AuthConsumer>
);
