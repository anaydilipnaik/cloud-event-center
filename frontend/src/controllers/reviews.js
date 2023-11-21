import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function createOrganizerReview(
  eventId,
  reviewerId,
  dataJson,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.post(
    semiEndpoint + "/api/reviews/" + eventId + "/organizer/" + reviewerId,
    dataJson
  );
}

export function createParticipantReview(
  eventId,
  participantId,
  dataJson,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.post(
    semiEndpoint + "/api/reviews/" + eventId + "/participant/" + participantId,
    dataJson
  );
}

export function getAverageParticipantRatings(
  participantId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint + "/api/reviews/participant/ratings/" + participantId
  );
}

export function getAverageOrganizerRatings(
  organizerId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint + "/api/reviews/organizer/ratings/" + organizerId
  );
}

export function getParticipantReviews(
  participantId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint + "/api/reviews/participant/reviews/" + participantId
  );
}

export function getOrganizerReviews(
  organizerId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint + "/api/reviews/organizer/reviews/" + organizerId
  );
}
