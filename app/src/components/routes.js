import React from 'react';
import {  Router, Switch, Route} from 'react-router-dom';
import { Home } from './home';
import { Course } from './course';
import history from './history';







export const Routes = ({state,courses, handleChange, handleSearch}) => {
    return (
        <Router history={history}>
        <Switch>
        <Route  exact path="/"  render={(props)=><Home state={state} courses={courses} handleChange={handleChange} handleSearch={handleSearch} /> } />
        <Route path="/degreePrograms/:id"  component={Course} />
        </Switch>
        </Router>
    )
}