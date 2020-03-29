import React from 'react';
import {  Router, Switch, Route} from 'react-router-dom';
import { Home } from './home';
import { Course } from './course';
import history from './history';
import { Signup } from './signup';
import SecureRoute from '@okta/okta-react/dist/SecureRoute';
import ImplicitCallback from '@okta/okta-react/dist/ImplicitCallback';
import Security from '@okta/okta-react/dist/Security';







export const Routes = ({authenticated, user, state,courses, handleChange, handleSearch, links, setState}) => {
    return (
        <Router history={history}>
            <Security issuer='https://dev-397378.okta.com/oauth2/default'
              clientId='0oa4x8n4hyfnUKjug4x6'
              redirectUri={window.location.origin + '/implicit/callback'}
              pkce={true}>
          <Route path='/implicit/callback' component={ImplicitCallback} />
        <Switch>
        <Route  exact={true} path="/"  render={(props)=><Home  authenticated={authenticated} user={user} state={state} courses={courses} 
        handleChange={handleChange} handleSearch={handleSearch} 
        links={links} setState={setState} /> } />
        <SecureRoute>
        <Route path="/degreePrograms/:id"  render={(props)=><Course authenticated={authenticated} user={user}/>}/>
        </SecureRoute>
        </Switch>
        </Security>
        </Router>
    )
}