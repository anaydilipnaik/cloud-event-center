import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getSystemReport(
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/report/system");
}

export function getParticipantReport(
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/report/user/" + userId);
}

export function getOrganizerReport(
  organizerId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/report/organizer/" + organizerId);
}
