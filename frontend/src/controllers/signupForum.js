import { semiEndpoint } from "../utils/ApiEndpoint";
import axios from "axios";

export function getQuestionsByEvent(
  eventId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint + "/api/forums/sign_up/" + eventId + "/questions"
  );
}

export function createQuestion(
  eventId,
  dataJson,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  
  const formData = new FormData();
  if (dataJson.file)
    formData.append("file", dataJson.file);

  return axios.post(
    semiEndpoint +
      "/api/forums/sign_up/" +
      eventId +
      "/questions?text=" +
      dataJson.text +
      "&userId=" +
      dataJson.userId,
    formData,
    
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
      "/api/forums/sign_up/questions/" +
      questionId +
      "/answers?text=" +
      dataJson.text +
      "&userId=" +
      dataJson.userId
  );
}

export function getQuestionAnswers(
  questionId,
  token = JSON.parse(localStorage.getItem("user")).token
) {
  axios.defaults.headers.common["authorization"] = "Bearer " + token;
  return axios.get(
    semiEndpoint + "/api/forums/sign_up/questions/" + questionId + "/answers"
  );
}
