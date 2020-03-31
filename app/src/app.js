import React from 'react';
import ReactDOM from 'react-dom';
import { Routes } from './components/routes';
import { CookiesProvider, useCookies } from 'react-cookie';
import { withCookies} from 'react-cookie';
import { MenuAppBar } from './components/menu-app-bar';

const API_URL = '/api/degreePrograms';
function App() {
    const [query, setQuery] = React.useState('');
     const [cookies, setCookie] = useCookies('XSRF-TOKEN');
     const [links , setLinks]  = React.useState({});
    const [state, setState] = React.useState({
        searchTerm: null,
        location: null,
        degree: '',
        isLoading: true,
        isAuthenticated: false,
        user: null,
        url: API_URL,
    });

    const [courses, setCourses] = React.useState([]);
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

    const handlePagination = (event) => {

        console.log(this.reply_click(event.target.name));

    }

    // React.useEffect(()=>{
    //     const getAsynUser = async () =>{
    //         const response = await fetch('/api/user', {credentials: 'include'});
    //         const body = await response.text();
    //         setCookie('XSRF-TOKEN');
    //         if (body === ''){
    //             setState(({isAuthenticated: false}));
    //         } else {
    //             setState({isAuthenticated: true, user: JSON.parse(body)});
    //         }
    //     };
    //     getAsynUser();
    // }, [])

    const onLogin= () => {
        // let port = (window.location.port ? ':' + window.location.port : '');
        // if (port === ':3000') {
        //   port = ':8080';
        // }
        // window.location.href = '//' + window.location.hostname + port + '/private';
      }
    
     const  onLogout = () => {
        // fetch('/api/logout', {method: 'POST', credentials: 'include',
        //   headers: {'X-XSRF-TOKEN': state.searchTermcsrfToken}}).then(res => res.json())
        //   .then(response => {
        //     window.location.href = response.logoutUrl + "?id_token_hint=" +
        //       response.idToken + "&post_logout_redirect_uri=" + window.location.origin;
        //   });
      }

    React.useEffect(() => {
        fetch(state.url, {headers :{authorization: 'Basic ' + window.btoa('admin' + ":" + 'password') }}).then(
            response => response.json()).then(result => {
                setCourses(result._embedded.degreePrograms);
                setLinks(result._links);
                console.log('courses', result);
            })
    }, [state.url])
    return (
        <>
        <CookiesProvider>
            <MenuAppBar state={state} onLogin={onLogin}  onLogout={onLogout}/>
            <Routes state={state} courses={courses} handleChange={handleChange} handleSearch={handleSearch} links={links} setState={setState}/>
        </CookiesProvider>

        </>)
}

export default withCookies(App);
