const AuthWrapper = withAuth(class WrappedRoutes extends Component {

    constructor(props) {
      super(props);
      this.state = { authenticated: null, user: null, api: new Api() };
      this.checkAuthentication = this.checkAuthentication.bind(this);
    }
  
    async checkAuthentication() {
      const authenticated = await this.props.auth.isAuthenticated();
      if (authenticated !== this.state.authenticated) {
        if (authenticated) {
          const user = await this.props.auth.getUser();
          let accessToken = await this.props.auth.getAccessToken();
          this.setState({ authenticated, user, api: new Api(accessToken) });
        }
        else {
          this.setState({ authenticated, user:null, api: new Api() });
        }
      }
    }
  
    async componentDidMount() {
      this.checkAuthentication();
    }
  
    async componentDidUpdate() {
      this.checkAuthentication();
    }
  
    async login() {
      if (this.state.authenticated === null) return; // do nothing if auth isn't loaded yet
      this.props.auth.login('/');
    }
  
    async logout() {
      this.props.auth.logout('/');
    }
  
    render() {
      let {authenticated, user, api} = this.state;
  
      if (authenticated === null) {
        return null;
      }
  
  
      return (
        <Switch>
          <Route
            path='/'
            exact={true}
            render={(props) => <Home {...props} authenticated={authenticated} user={user} api={api} navbar={navbar} />}
          />
          <SecureRoute
            path='/coffee-shops'
            exact={true}
            render={(props) => <CoffeeShopsList {...props} authenticated={authenticated} user={user} api={api} navbar={navbar}/>}
          />
          <SecureRoute
            path='/coffee-shops/:id'
            render={(props) => <CoffeeShopEdit {...props} authenticated={authenticated} user={user} api={api} navbar={navbar}/>}
          />
        </Switch>
      )
    }
  });
  