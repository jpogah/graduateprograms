import React from 'react';
import TextField from '@material-ui/core/TextField';
import { makeStyles } from '@material-ui/core/styles';
import { Grid, Button, Typography } from '@material-ui/core';

const useStyles = makeStyles(theme => ({
  root: {
    '& .MuiTextField-root': {
      margin: theme.spacing(1),
      width: '25ch',
    },
  },
  button: {
    marginLeft: 10,
   // textAlign: "center",
    width: '25ch'
  }
}));

export const Signup = ({ state, handleChange, handleSignup}) => {
  const classes = useStyles();

  return (
    <>
    <Grid container  spacing={2} direction="column" alignItems="center" justify="center">
    <Grid item justify='center' > <Typography variant='h4' >Sign up</Typography></Grid>
    <Grid item>
    <form className={classes.root} noValidate autoComplete="off">
      
      <Grid container direction="column" spacing={2} >
      <Grid item justify="center">
        <TextField
          required
          id="outlined-required"
          label="Name"
          name="name"
          placeholder="Name"
          value={state.name}
          variant="outlined"
          onChange={handleChange}
        />
        </Grid>
        <Grid item justify="center">
        <TextField
          required
          id="outlined-required"
          label="Email"
          name="email"
          placeholder="Email"
          value={state.email}
          variant="outlined"
          onChange={handleChange}
        />
        </Grid>
       <Grid item justify="center">
        <TextField
          id="outlined-password-input"
          label="Password"
          name="password"
          type="password"
          value={state.password}
          autoComplete="current-password"
          variant="outlined"
          onChange={handleChange}
        /> 
        </Grid> 
        <Grid item  justify="center">
        <TextField
          id="outlined-password-input"
          label="Confirmation"
          name="confirmation"
          type="password"
          value={state.confirmation}

          variant="outlined"
          onChange={handleChange}
        /> 
        </Grid> 

       <Grid item justify="center">
          <Button variant="contained" className={classes.button} color="primary" onClick={handleSignup}>Create My Account</Button>
        </Grid>  
      </Grid>
    </form>
    </Grid>
    </Grid>
    </>
  );
}
