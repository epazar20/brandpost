import { useTranslation } from "react-i18next";
import Input from "../component/Input";
import { useEffect, useState } from "react";
import ButtonWithLoading from "../component/ButtonWithLoading";
import { useApiProgress } from "../common/ApiProgress";
import { useDispatch } from "react-redux";
import { loginHandleAction } from "../redux/authAction";

const LoginPage = (props: any) => {
  //static contextType: React.Context<any> = Authentication;
  const [username, setUsername] = useState();
  const [password, setPassword] = useState();
  const [errorApi, setErrorApi] = useState("");
  const pendingApiCall = useApiProgress("get", "auth");
  const { t } = useTranslation();
  const dispatch = useDispatch();

  useEffect(() => {
    setErrorApi("");
  }, [username, password]);

  const onClick = async (event: any) => {
    event.preventDefault();
    setErrorApi("");
    const { history } = props as any;
    const cred = {
      username,
      password,
    };
    try {
      const res = await dispatch(loginHandleAction(cred) as any);
      history.push("/");
    } catch (err: any) {
      console.log(err);
      setErrorApi(err.response.data.message);
      console.log(err.response.data.message);

    }
  };

  const disableLogin = !(username && password) || pendingApiCall;

  return (
    <div className="container">
      <h1 className="text-center">{t("Login")}</h1>
      <form>
        <Input
          name={"username"}
          label={t("Username")}
          onChange={(event: any) => setUsername(event.target.value)}
        />
        <Input
          name={"password"}
          label={t("Password")}
          onChange={(event: any) => setPassword(event.target.value)}
          type={"password"}
        />
        <ButtonWithLoading
          onClick={onClick}
          disabled={disableLogin}
          text={t("Login")}
          pendingApiCall={pendingApiCall}
          error={errorApi}
        />
      </form>
    </div>
  );
};

export default LoginPage;
