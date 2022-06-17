import { useTranslation } from 'react-i18next';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';
import logo from '../assets/logo.png'
import { logoutAction } from '../redux/authAction';
import ProfileImage from './ProfileImage';
import { Icon } from '@iconify/react';
import { useEffect, useRef, useState } from 'react';

const TopBar = (props: any) => {

    //static contextType: React.Context<any> = Authentication;
    // const context = useContext(Authentication);
    // const {state,onLogOut:onLogOutSuccess} = context as any ;
    // const { isLoggedIn, username } = state;

    const { t } = useTranslation();
    const { isLoggedIn, username, displayname, image } = useSelector((store: any) => {
        return {
            isLoggedIn: store.isLoggedIn,
            username: store.username,
            displayname: store.displayname,
            image: store.image
        }
    });
    const dispatch = useDispatch();
    const [menuView,setMenuView] = useState(false);
    const menuRef = useRef(null);



    const onLogOutSuccess = () => {
        dispatch(logoutAction())
    }

    const clickHandler = (event: any) => {
        // console.log(event.target)
        // console.log(menuRef.current);
        if(menuRef.current && !(menuRef.current as any).contains(event.target))
        {
            setMenuView(false);
        }
    }

    useEffect(() => {

        document.addEventListener("click", clickHandler);

        return () => {
            document.removeEventListener("click", clickHandler);
        }
    })


    let link = (<ul className="nav ml-auto">
        <li className="nav-item">
            <Link className="nav-link" to="/login">{t("Login")}</Link>
        </li>
        <li className="nav-item">
            <Link className="nav-link" to="/signup">{t("Sign Up")}</Link>
        </li>
    </ul>)



    if (isLoggedIn) {

        let dropdownView = "dropdown-menu p-0 shadow";
        if(menuView)
        {
            dropdownView += " show";
        }

        link = (<ul className="nav ml-auto" ref={menuRef}  onClick={() => {setMenuView(!menuView);}}>
            <li className="nav-item dropdown">
                <div className='d-flex' style={{ cursor: "pointer" }}>
                    <ProfileImage imageDb={image} width={32} height={32} />
                    <span className='nav-link dropdown-toggle'>{displayname}</span>
                </div>
                <div className={dropdownView}>
                    <Link className="dropdown-item" to={`/user/${username}`}>
                        <Icon className='text-info' icon="bi:person-circle" /><span className='ms-1'>{t("My Profile")}</span></Link>
                    <Link className="dropdown-item" to="/" onClick={onLogOutSuccess} >
                        <Icon className='text-danger' icon="fa:power-off" /><span className='ms-1'>{t("Logout")}</span></Link>
                </div>
            </li>
        </ul>)

    }


    return (

        <div id="navbar" className="navbar navbar-expand navbar-fixed-top bg-light">
            <div className="container">
                <Link className="navbar-brand" to="/">
                    <img src={logo} alt="logo" width={100} /><span className='ps-3'>PazarFy</span>
                </Link>
                {link}
            </div>
        </div>

    )

}

export default TopBar;




