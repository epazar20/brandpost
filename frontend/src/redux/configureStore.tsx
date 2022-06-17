import { applyMiddleware, compose, createStore } from "redux"
import thunk from "redux-thunk";
import { setAuth } from "../api/ApiCalls";
import { authReducer, defaultState } from "./authReducer"


const initialState: any = {
  isLoggedIn: true,
  username: "user",
  password: undefined,
  displayname: undefined,
  image: undefined
}

declare global {
  interface Window {
    __REDUX_DEVTOOLS_EXTENSION_COMPOSE__?: typeof compose;
  }
}

export const configureStore = () => {
  let current = defaultState;
  const local = localStorage.getItem("auth");
  if (local) {
    try {
      current = JSON.parse(local);
      setAuth(current);
    } catch (error) {

    }
  }
  const composeEnhancers = window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose;
  const store = createStore(authReducer, current, composeEnhancers(applyMiddleware(thunk)));
  store.subscribe(() => {
    localStorage.setItem("auth", JSON.stringify(store.getState()));
    setAuth(store.getState());

  })

  return store;
}