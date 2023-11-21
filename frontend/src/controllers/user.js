import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getUserDetails(
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/auth/user/" + userId);
}

export function updateUserDetails(
  userId,
  dataJson,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.put(semiEndpoint + "/api/auth/user/" + userId, dataJson);
}
