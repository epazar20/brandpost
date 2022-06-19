import * as constant from "./constants";

export const defaultState: any = {
  isLoggedIn: false,
  username: undefined,
  password: undefined,
  displayname: undefined,
  image: undefined,
  token: undefined,
};

export const authReducer = (state: any = { ...defaultState }, action: any) => {
  console.log("AuthType: " + action.type);

  if (action.type === constant.LOGOUT_SUCCESS) {
    return defaultState;
  } else if (action.type == constant.LOGIN_SUCCESS) {
    return {
      ...state,
      ...action.payload.user,
      isLoggedIn: true,
      token: action.payload.token,
    };
  } else if (action.type == constant.UPDATEUSER) {
    return {
      ...state,
      ...action.payload,
    };
  }
  return state;
};
