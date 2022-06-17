import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import "./bootstrap-override.scss";
import reportWebVitals from "./reportWebVitals";
import App from "./component/App";
import { Provider } from "react-redux";
import { configureStore } from "./redux/configureStore";
import { I18nextProvider } from "react-i18next";
import i18n from "./i18n/i18n";

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

const store = configureStore();
//React.StrictMode
root.render(
  <div>
    {/* <AuthenticationContext> */}
    <Provider store={store}>
      <I18nextProvider i18n={i18n}>
        <App />
      </I18nextProvider>
    </Provider>
    {/* </AuthenticationContext> */}
  </div>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
