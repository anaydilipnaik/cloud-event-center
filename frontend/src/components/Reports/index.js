import * as React from "react";
import { Table, TableBody, TableCell, TableRow, Grid } from "@mui/material";
import { useState, useEffect } from "react";
import {
  getSystemReport,
  getParticipantReport,
  getOrganizerReport,
} from "../../controllers/reports";
import { AuthConsumer } from "../contexts/Auth/AuthContext";

const Reports = ({ user }) => {
  const [systemReport, setSystemReport] = useState(null);
  const [participantReport, setParticipantReport] = useState(null);
  const [organizerReport, setOrganizerReport] = useState(null);

  useEffect(() => {
    if (user) {
      getSystemReport()
        .then((res) => {
          setSystemReport(res.data);
          return getParticipantReport(user.id);
        })
        .then((res) => {
          setParticipantReport(res.data);
          return getOrganizerReport(user.id);
        })
        .then((res) => {
          setOrganizerReport(res.data);
        })
        .catch((err) => console.log(err));
    }
  }, [user]);

  return (
    <>
      <h1>System Report</h1>
      {systemReport ? (
        <Grid container direction="row">
          <Grid item sx={{ border: 1, width: 450 }}>
            <Table sx={{ width: 450 }} aria-label="simple table">
              <TableBody>
                {Object.keys(systemReport).map((key) => (
                  <TableRow
                    sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                  >
                    <TableCell component="th" scope="row">
                      {key === "createdEvents"
                        ? "Created Events"
                        : key === "paidEventsPercent"
                        ? "Paid Events (%)"
                        : key === "cancelledEvents"
                        ? "Cancelled Events"
                        : key === "cancelEventPartciReq"
                        ? "Participant Event Cancel Requests"
                        : key === "finishedEvents"
                        ? "Finished Events"
                        : key === "finishedEventsAvgPartici"
                        ? "Finished Events Average Participants"
                        : null}
                    </TableCell>
                    <TableCell align="right">{systemReport[key]}</TableCell>
                  </TableRow>
                ))}
              </TableBody>
            </Table>
          </Grid>
        </Grid>
      ) : null}
      <br />
      <br />
      <h1>Participant Report</h1>
      {participantReport ? (
        <Grid sx={{ border: 1, width: 450 }}>
          <Table sx={{ width: 450 }} aria-label="simple table">
            <TableBody>
              {Object.keys(participantReport).map((key) => (
                <TableRow
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {key === "signedUpeventsCount"
                      ? "Signed Up Events"
                      : key === "rejectsCount"
                      ? "Rejects"
                      : key === "approvalCount"
                      ? "Approvals"
                      : key === "finishedEventsCount"
                      ? "Finished Events"
                      : null}
                  </TableCell>
                  <TableCell align="right">{participantReport[key]}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Grid>
      ) : null}
      <br />
      <br />
      <h1>Organizer Report</h1>
      {organizerReport ? (
        <Grid sx={{ border: 1, width: 450 }}>
          <Table sx={{ width: 450 }} aria-label="simple table">
            <TableBody>
              {Object.keys(organizerReport).map((key) => (
                <TableRow
                  sx={{ "&:last-child td, &:last-child th": { border: 0 } }}
                >
                  <TableCell component="th" scope="row">
                    {key === "createdEvents"
                      ? "Created Events"
                      : key === "paidEvents"
                      ? "Paid Events"
                      : key === "percentagePaidEvents"
                      ? "Paid Events (%)"
                      : key === "cancelledEvents"
                      ? "Cancelled Events"
                      : key === "participationRequestsbyMin"
                      ? "Participation Requests by Minimum"
                      : key === "finishedEvents"
                      ? "Finished Events"
                      : key === "avgParticipantsFshedEvts"
                      ? "Finished Events Average Participants"
                      : key === "finishedPaidEvents"
                      ? "Finished Paid Events"
                      : key === "finishedPaidEventsRevenue"
                      ? "Finished Paid Events Revenue"
                      : null}
                  </TableCell>
                  <TableCell align="right">{organizerReport[key]}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </Grid>
      ) : null}
    </>
  );
};

export default (props) => (
  <AuthConsumer>
    {({ user }) => <Reports user={user} {...props} />}
  </AuthConsumer>
);
