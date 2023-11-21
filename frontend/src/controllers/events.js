import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getEvents(
  page = 1,
  city,
  status,
  keyword,
  startTime,
  endTime,
  organizer,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint +
      "/api/event/" +
      page +
      "/search?city=" +
      city +
      "&status=" +
      status +
      "&keyword=" +
      keyword +
      "&startTime=" +
      startTime +
      "&endTime=" +
      endTime +
      "&organizer=" +
      organizer
  );
}

export function getEventDetails(
  eventId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/event/" + eventId);
}

export function createEvent(
  dataJson,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.post(semiEndpoint + "/api/event", dataJson);
}

export function registerEvent(
  eventId,
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.put(
    semiEndpoint + "/api/registration/" + eventId + "/" + userId
  );
}

export function getEventRegistrationsByParticipant(
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/auth/event/" + userId);
}

export function getEventRegistrationsByOrganizer(
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(semiEndpoint + "/api/event/organizer/" + userId);
}

export function approveParticipant(
  eventId,
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.put(
    semiEndpoint + "/api/registration/approve/" + eventId + "/" + userId
  );
}

export function rejectParticipant(
  eventId,
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.put(
    semiEndpoint + "/api/registration/reject/" + eventId + "/" + userId
  );
}
