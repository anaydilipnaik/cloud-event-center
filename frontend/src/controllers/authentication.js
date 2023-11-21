import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function registerUser(dataJson) {
  return axios.post(semiEndpoint + "/api/auth/signup", dataJson);
}

export function loginUser(dataJson) {
  return axios.post(semiEndpoint + "/api/auth/signin", dataJson);
}

export function logoutUser(dataJson) {
  return axios.post(semiEndpoint + "/api/auth/logout", dataJson);
}
