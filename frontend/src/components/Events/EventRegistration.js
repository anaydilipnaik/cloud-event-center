import * as React from "react";
import { Grid, Typography } from "@mui/material";

export default function EventRegistration({ eventDetails }) {
  return (
    <div>
      {eventDetails ? (
        <Grid container direction="column">
          <Grid item>
            <Typography
              sx={{
                textDecoration: "underline",
                fontWeight: "bold",
              }}
            >
              Description:
            </Typography>
            <p>{eventDetails.description}</p>
          </Grid>
          <Grid item container direction="row" md={12}>
            <Grid item md={6}>
              <Typography
                sx={{
                  textDecoration: "underline",
                  fontWeight: "bold",
                }}
              >
                Min participants:
              </Typography>
              <p>{eventDetails.minParticipants}</p>
            </Grid>
            <Grid item md={6}>
              <Typography
                sx={{
                  textDecoration: "underline",
                  fontWeight: "bold",
                }}
              >
                Max participants:
              </Typography>
              <p>{eventDetails.maxParticipants}</p>
            </Grid>
          </Grid>
          <Grid item container direction="row" md={12}>
            <Grid item md={6}>
              <Typography
                sx={{
                  textDecoration: "underline",
                  fontWeight: "bold",
                }}
              >
                Fee:
              </Typography>
              <p>${eventDetails.fee}</p>
            </Grid>
            <Grid item md={6}>
              <Typography
                sx={{
                  textDecoration: "underline",
                  fontWeight: "bold",
                }}
              >
                Current no. of sign-ups:
              </Typography>
              <p>{eventDetails.participants.length}</p>
            </Grid>
          </Grid>
          <Grid item>
            <Typography sx={{ fontWeight: "bold", background: "yellow" }}>
              NOTE: The amount will be charged to your default bank account upon
              confirmation. Proceed with the registration?
            </Typography>
          </Grid>
        </Grid>
      ) : null}
    </div>
  );
}
