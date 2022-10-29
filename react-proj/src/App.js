import * as React from 'react';
import {
  Router,
  Switch,
  Route,
} from "react-router-dom";
import { createBrowserHistory } from "history";

function App() {
  let baseURI = window.location.pathname.split('/').filter((item, i, arr) => i < arr.length - 1).join('/');
  const customHistory = createBrowserHistory(baseURI);
  window.ameHistory = history;
  return (
    <Router history={customHistory}>
    <Switch>
    </Switch>
  </Router>
  );
}

export default App;
