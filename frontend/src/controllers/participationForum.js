import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getQuestionsByEvent(
  eventId,
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint +
      "/api/forums/participant/" +
      eventId +
      "/questions" +
      "?userId=" +
      userId
  );
}

export function createQuestion(
  eventId,
  userId,
  dataJson,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;

  const formData = new FormData();
  if (dataJson.file)
    formData.append("file", dataJson.file);

  return axios.post(
    semiEndpoint +
      "/api/forums/participant/" +
      eventId +
      "/questions?text=" +
      dataJson.text +
      "&userId=" +
      userId,
    formData
  );
}

export function createAnswer(
  questionId,
  dataJson,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.post(
    semiEndpoint +
      "/api/forums/participant/questions/" +
      questionId +
      "/answers?text=" +
      dataJson.text +
      "&userId=" +
      dataJson.userId
  );
}

export function getQuestionAnswers(
  questionId,
  userId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint +
      "/api/forums/participant/questions/" +
      questionId +
      "/answers" +
      "?userId=" +
      userId
  );
}

export function closeForum(
  userId,
  eventId,
  text,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.post(
    `${semiEndpoint}/api/forums/participant/${eventId}/close?text=${text}&userId=${userId}`
  );
}

export function canPostToForum(
  event,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  if (!event) return false;
  const { pForumOpen, endTime } = event;
  if (pForumOpen === false) return false;
  let eventEndTime = new Date(endTime);
  eventEndTime.setTime(eventEndTime.getTime() + 1000 * 60 * 60 * 24 * 3);
  if (new Date().getTime() > eventEndTime) return false;
  return true;
}
