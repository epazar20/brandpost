import * as constant from "./constants";

export const defaultState: any = {
    isLoggedIn: false,
    username: undefined,
    password: undefined,
    displayname: undefined,
    image: undefined,
    isApiPending:false,
    error:undefined
    
}


export const authReducer = (state: any = {...defaultState}, action: any) => {
    console.log("AuthType: " + action.type);

    if (action.type === constant.LOGOUT_SUCCESS) {
        return defaultState;
    }
    else if (action.type == constant.LOGIN_SUCCESS) {
        return {
            ...state,
            ...action.payload,
            isLoggedIn: true,
            isApiPending: false,
        };
    }else if (action.type == constant.LOGIN_PENDING) {
        return {
            ...state,
            isLoggedIn: false,
            isApiPending: true,
        };
    }
    else if (action.type == constant.LOGIN_ERROR) {
        return {
            ...state,
            isLoggedIn: false,
            isApiPending: false,
            error:action.payload   
        };
    }
    return state;

}