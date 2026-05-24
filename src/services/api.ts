import axios from "axios";

/* =========================
   API INSTANCE
========================= */

export const api = axios.create({

  baseURL:
    "http://localhost:8080",

  timeout: 10000,

  headers: {
    "Content-Type":
      "application/json",
  },

});

/* =========================
   REQUEST INTERCEPTOR
========================= */

api.interceptors.request.use(

  (config) => {

    /*
      Future auth token support:

      const token =
        localStorage.getItem("token");

      if (token) {
        config.headers.Authorization =
          `Bearer ${token}`;
      }
    */

    return config;

  },

  (error) => {
    return Promise.reject(error);
  }

);

/* =========================
   RESPONSE INTERCEPTOR
========================= */

api.interceptors.response.use(

  (response) => response,

  (error) => {

    /* NETWORK ERROR */
    if (!error.response) {

      console.error(
        "Network error:",
        error
      );

      return Promise.reject({
        message:
          "Cannot connect to server",
      });

    }

    /* SERVER ERROR */
    if (
      error.response.status >= 500
    ) {

      console.error(
        "Server error:",
        error.response
      );

    }

    /* UNAUTHORIZED */
    if (
      error.response.status === 401
    ) {

      console.warn(
        "Unauthorized access"
      );

    }

    return Promise.reject(error);

  }

);