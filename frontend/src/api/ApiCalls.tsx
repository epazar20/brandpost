import axios, { HeadersDefaults } from "axios";

export const signUp = async (data: any) => {
  return axios.post("users", data);
};

interface CommonHeaderProperties extends HeadersDefaults {
  Authorization: string;
  "Accept-Language": string;
}

export const changeBackendLanguage = (lang: string) => {
  axios.defaults.headers = {
    "Accept-Language": lang,
  } as CommonHeaderProperties;
};

export const login = (cred: any) => {
  return axios.post("auth", cred );
};

// export const login = (cred: any) => {
//   return axios.post("auth", {}, { auth: cred });
// };

export const getUsers = (type = -1, page = 0, size = 5) => {
  return axios.get(`users?page=${page}&size=${size}&type=${type}`);
};

export const setAuth = ({ username, password, isLoggedIn, token }: any) => {
  let auth = undefined;
  if (isLoggedIn) {
    auth = `Bearer ${token}`;
  }
  axios.defaults.headers = {
    Authorization: auth,
  } as CommonHeaderProperties;
};

// export const setAuth = ({ username, password, isLoggedIn }: any) => {
//   let auth = undefined;
//   if (isLoggedIn) {
//     auth = `Basic ${btoa(username + ":" + password)}`;
//   }
//   axios.defaults.headers = {
//     Authorization: auth,
//   } as CommonHeaderProperties;
// };

export const getUser = (username: string) => {
  return axios.get(`users/${username}`);
};

export const updateUser = (username: string, userDto: any) => {
  return axios.put(`users/${username}`, userDto);
};

export const sendFeedApi = (feed: any) => {
  return axios.post("feeds", feed);
};

export const getFeeds = (
  username: string,
  page: number = 0,
  size: number = 0,
  id: number = 0,
  isbefore: number = 1
) => {
  const pathId = id > 0 ? `&id=${id}&isbefore=${isbefore}` : ``;
  const path = username
    ? `feeds?username=${username}&page=${page}&size=${size}`
    : `feeds?page=${page}&size=${size}`;
  return axios.get(path + pathId);
};

export const getNewFeedsCount = (username: string, id: number = 0) => {
  const path = username
    ? `new/feeds/count?username=${username}&id=${id}`
    : `new/feeds/count?id=${id}`;
  return axios.get(path);
};

export const uploadFile = (attach: any) => {
  return axios.post("attachments", attach);
};

export const deleteFeed = (id:number) => {
  return axios.delete(`feeds/${id}`);
}

export const deleteUser = (username:string) => {
  return axios.delete(`users/${username}`);
}