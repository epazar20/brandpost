import UserSignUpPage from '../pages/UserSignUp';
import LanguageSelector from './LanguageSelector';
import LoginPage from '../pages/LoginPage';
import { HashRouter as Router, Redirect, Route, RouteProps, Switch } from 'react-router-dom'
import HomePage from '../pages/HomePage';
import UserPage from '../pages/UserPage';
import TopBar from './TopBar';
import { useSelector } from 'react-redux';
import Loading from './Loading';


const App = (props:any) => {

    //const { isLoggedIn } = (this.context as any).state;
    const { isLoggedIn } = useSelector((store:any)=>{
      return {
        isLoggedIn:store.isLoggedIn
      }
    });

    return (
      <div className='container'>

        <Router >
          <TopBar />
          {/* <Loading/> */}
          <Switch>
            <Route exact path="/" component={HomePage} />
            {!isLoggedIn && <Route path="/login" component={(props: RouteProps) => {
              return <LoginPage {...props} />
            }} />}
            <Route path="/signup" component={UserSignUpPage} />
            <Route path="/user/:username" component={(props: RouteProps) => {
              return <UserPage {...props} />;
            }} />
            <Redirect to={"/"} />
          </Switch>
        </Router>
        <LanguageSelector />
      </div>
    );
}

export default App;
