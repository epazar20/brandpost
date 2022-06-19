import { login, signUp } from "../api/ApiCalls";
import * as constant from "./constants";

export const logoutAction = () => {
  return {
    type: constant.LOGOUT_SUCCESS,
  };
};

export const loginSuccessAction = (payload: any) => {
  return {
    type: constant.LOGIN_SUCCESS,
    payload,
  };
};

export const loginHandleAction = (cred: any) => {
  return async (dispatch: any) => {
    try {
      const res: any = await login(cred);
      const authState = {
        ...res.data,
        password: cred.password,
      };
      dispatch(loginSuccessAction(authState));
      return res;
    } catch (error) {
      throw error;
    }
  };
};

export const signUpHandlerAction = (data: any) => {
  return async (dispatch: any) => {
    try {
      const response = await signUp(data);
      const authState = {
        ...data,
      };
      dispatch(await loginHandleAction(authState));
      return response;
    } catch (error) {
      throw error;
    }
  };
};

export const updateSuccess = (data: any) => {
  return {
    type: constant.UPDATEUSER,
    payload: data,
  };
};
