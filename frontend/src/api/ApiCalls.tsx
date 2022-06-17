import axios, { HeadersDefaults } from "axios"


export const signUp = async (data: any) => {
  return axios.post("/api/1.0/users", data);
}

interface CommonHeaderProperties extends HeadersDefaults {
  Authorization: string;
  "Accept-Language": string;
}

export const changeBackendLanguage = (lang: string) => {
  axios.defaults.headers = {
    "Accept-Language": lang
  } as CommonHeaderProperties;
}


export const login = (cred: any) => {
  return axios.post("/api/1.0/auth", {}, { auth: cred });
}

export const getUsers = (page = 0, size = 3) => {
  return axios.get(`/api/1.0/users?page=${page}&size=${size}`);
}

export const setAuth = ({ username, password, isLoggedIn }: any) => {

  let auth = undefined;
  if (isLoggedIn) {
    auth = `Basic ${btoa(username + ":" + password)}`

  }
  axios.defaults.headers = {
    "Authorization": auth
  } as CommonHeaderProperties;

}

export const getUser = (username: string) => {
  return axios.get(`/api/1.0/users/${username}`);
}


export const updateUser = (username: string, userDto: any) => {
  return axios.put(`/api/1.0/users/${username}`, userDto);
}


export const sendFeedApi = (feed: any) => {

  return axios.post("/api/1.0/feeds", feed);

}

export const getFeeds = (page:number=0,size:number=0) => 
{
  return axios.get(`/api/1.0/feeds?page=${page}&size=${size}`);
}