import React from 'react';
import { Routes } from './components/routes';
import { CookiesProvider, useCookies } from 'react-cookie';
import { withCookies} from 'react-cookie';
import { MenuAppBar } from './components/menu-app-bar';


function App() {
    const [query, setQuery] = React.useState('');
     const [cookies, setCookie] = useCookies('XSRF-TOKEN');
    const [state, setState] = React.useState({
        searchTerm: null,
        location: null,
        degree: '',
        isLoading: true,
        isAuthenticated: false,
        user: null,
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
        console.log(query) ;
        let searchParam = '';
        searchParam = state.searchTerm ? `searchTerm=${state.searchTerm}` : searchParam;
        searchParam = state.location ? `${searchParam}&location=${state.location}`: searchParam;
        setQuery(searchParam);
        console.log(searchParam);
    }

    React.useEffect(()=>{
        const getAsynUser = async () =>{
            const response = await fetch('/api/user', {credentials: 'include'});
            const body = await response.text();
            setCookie('XSRF-TOKEN');
            if (body === ''){
                setState(({isAuthenticated: false}));
            } else {
                setState({isAuthenticated: true, user: JSON.parse(body)});
            }
        };
        getAsynUser();
    }, [])

    const onLogin= () => {
        let port = (window.location.port ? ':' + window.location.port : '');
        if (port === ':3000') {
          port = ':8080';
        }
        window.location.href = '//' + window.location.hostname + port + '/private';
      }
    
     const  onLogout = () => {
        fetch('/api/logout', {method: 'POST', credentials: 'include',
          headers: {'X-XSRF-TOKEN': state.searchTermcsrfToken}}).then(res => res.json())
          .then(response => {
            window.location.href = response.logoutUrl + "?id_token_hint=" +
              response.idToken + "&post_logout_redirect_uri=" + window.location.origin;
          });
      }

    React.useEffect(() => {
        const apiUrl = query.length === 0 ? '/api/degreePrograms' 
        : `/api/degreePrograms/search/searchBy?${query}`;
        fetch(apiUrl).then(
            response => response.json()).then(result => {
                setCourses(result._embedded.degreePrograms);
                console.log('courses', result);
            })
    }, [query])
    return (
        <>
        <CookiesProvider>
            <MenuAppBar state={state} onLogin={onLogin}  onLogout={onLogout}/>
            <Routes state={state} courses={courses} handleChange={handleChange} handleSearch={handleSearch}/>
        </CookiesProvider>

        </>)
}

export default withCookies(App);
