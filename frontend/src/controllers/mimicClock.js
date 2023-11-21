import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getMimicTime(
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/clock/gettime");
}

export function setMimicTime(
  newDate,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.post(semiEndpoint + "/api/clock/settime/" + newDate);
}
