import React, { Component } from 'react';

const initialstate = {
    state: {
        isLoggedIn: false,
        username: undefined,
        password: undefined,
        displayname: undefined,
        image: undefined
    },
    onLoginSuccess: undefined,
    onLogOut: undefined
}


export const Authentication = React.createContext(initialstate);

class AuthenticationContext extends Component {

    state = {
        isLoggedIn: true,
        username:  "context",
        password: undefined,
        displayname: undefined,
        image: undefined
    }

    onLoginSuccess = (authParam: any) => {
        this.setState(
            {
                ...authParam,
                isLoggedIn: true
            }
        )
        console.log(`LoginSuccess:${authParam.username}`);

    }

    onLogOut = () => {
        this.setState({
            username: undefined,
            password: undefined,
            displayname: undefined,
            image: undefined,
            isLoggedIn: false
        })
        console.log("Logout")
    }

    render() {
        return (
            <Authentication.Provider value={
                {
                    state: this.state,
                    onLoginSuccess: this.onLoginSuccess,
                    onLogOut: this.onLogOut
                } as any
            }>
                {(this.props as any).children}
            </Authentication.Provider>
        );
    }
}

export default AuthenticationContext;