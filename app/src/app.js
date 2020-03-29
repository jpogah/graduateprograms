import React from 'react';
import { Routes } from './components/routes';
import { postData } from './http';
import history from './components/history';
import Api from './Api';
import { withAuth } from '@okta/okta-react';
import { Security, SecureRoute, ImplicitCallback } from '@okta/okta-react';
import { MenuAppBar } from './components/menu-app-bar';
const API_URL = '/api/degreePrograms';
const USER_API_URL = '/api/users';



const App = ({ auth }) => {
     const [links , setLinks]  = React.useState({});
    const [state, setState] = React.useState({
        searchTerm: null,
        location: null,
        degree: '',
        isLoading: true,
        isAuthenticated: false,
        user: null,
        url: API_URL,
        name:'',
        email: '',
        password: '',
        confirmation:'',
        authenticated:null,
        user: null,
        api: new Api()
        
    });

    const [courses, setCourses] = React.useState([]);

    const checkAuthentication = async() => {
       const authenticated = await auth.isAuthenticated();
       if ( authenticated !== state.authenticated){
           if (authenticated) {
               const user = await auth.getUser();
               console.log("User is: " , user);
               let accessToken = await auth.getAccessToken();
               setState({authenticated, user, api: new Api(accessToken)});
           }

       } else{
           setState({authenticated, user: null, api:new Api()});
       }
    }

    React.useEffect(() => {
        checkAuthentication();
    });


    const handleChange = (event) => {
        const value = event.target.value;
        setState({
            ...state,
            [event.target.name]: value
        })
    }
    const handleSearch = () => {
        let searchParam = '';
        console.log('before url',state.url)
        searchParam = state.searchTerm ? `searchTerm=${state.searchTerm}` : searchParam;
        searchParam = state.location ? `${searchParam}&location=${state.location}`: searchParam;
        setState({ url : searchParam.length === 0 ? API_URL
        : `${API_URL}/search/searchBy?${searchParam}`});
        console.log('searchparam',searchParam);
        console.log('after url', state.url);
    }


    const login= () => {
        if (state.authenticated == null ) return;
        auth.login('/')
      }
    
     const  logout = () => {
        auth.logout('/') ;
      }

    // React.useEffect(() => {
    //     fetch(state.url, {headers :{authorization: `Basic ${window.btoa(`admin:password`) }`}}).then(
    //         response => response.json()).then(result => {
    //             setCourses(result._embedded.degreePrograms);
    //             setLinks(result._links);
    //             console.log('courses', result);
    //         })
    // }, [state.url])

    let {authenticated, user, api} = state;

    React.useEffect(() =>{
        const result = api.getAll(state.url);
        console.log('result', result);
        setCourses(result._embedded.degreePrograms);
        setLinks(result._links);
    }, [api, state.url])

    if (authenticated == null) return null;
    return (

        
        <> 
            <MenuAppBar isAuthenticated={authenticated} state={state} login={login}  logout={logout}/>
            <Routes authenticated={authenticated} user={user} state={state} courses={courses} handleChange={handleChange} 
            handleSearch={handleSearch} links={links} setState={setState}/>
        </>)
}

export default withAuth(App);
