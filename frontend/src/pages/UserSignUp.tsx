import { useState } from "react";
import Input from '../component/Input';
import { useTranslation } from "react-i18next";
import ButtonWithLoading from "../component/ButtonWithLoading";
import { useApiProgress } from "../common/ApiProgress";
import { useDispatch } from "react-redux";
import { signUpHandlerAction } from "../redux/authAction";

const UserSignUpPage = (props: any) => {

    const [userData, setUserData] = useState({
        username: null,
        displayname: null,
        password: null,
        passwordrepeat: null
    });

    const [errors, setErrors] = useState({});
    const pendingApiCall  = useApiProgress("post","/api/1.0/users")
    const {t} = useTranslation();
    const dispatch = useDispatch();

    const onChange = (event: any) => {
        const { name, value } = event.target;
        const errorsCopy: any = { ...errors, [name]: undefined }
        setErrors(errorsCopy);
        setUserData((previous:any)=>{
            return{
                ...previous,
                [name]:value
            }
        })
    }

    const onClick = async (event: any) => {
        event.preventDefault();
        const { username, displayname, password } = userData;
        const { history } = props;
        const data = {
            username,
            displayname,
            password
        }

        try {
            const response = await dispatch(signUpHandlerAction(data) as any);
            history.push("/");
        } catch (error: any) {
            console.log(error.response.data);
            if (error.response.data.validationerrors) { setErrors(error.response.data.validationerrors); }
        }

    }
    
    let paswordMismatch = undefined;
    if (userData.password !== userData.passwordrepeat) { paswordMismatch = t("Password mismatch") }
    const { username, displayname, password } = errors as any;
    return (
        <div className="container">
            <h1 className="text-center">{t("Sign Up")}</h1>
            <form>
                <Input name={"username"} error={username} label={t("Username")} onChange={onChange} />
                <Input name={"displayname"} error={displayname} label={t("Display Name")} onChange={onChange} />
                <Input name={"password"} error={password} label={t("Password")} onChange={onChange} type={"password"} />
                <Input name={"passwordrepeat"} error={paswordMismatch} label={t("Password Repeat")} onChange={onChange} type={"password"} />
                <ButtonWithLoading onClick={onClick} disabled={pendingApiCall || paswordMismatch} text={t("Sign Up")} pendingApiCall={pendingApiCall} />
            </form>
        </div>
    )
}


export default UserSignUpPage;