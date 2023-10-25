import { useEffect, useState, useCallback } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import "./App.css";


import Error from "./components/Error";
import NavBar from "./components/NavBar";
import LoginForm from "./components/LoginForm";
import SignUpForm from "./components/SignUpForm";
import AuthContext from "./contexts/AuthContext";

// pages
import Products from './pages/Products';

import { refreshToken, logout } from "./services/authAPI";

const TIMEOUT_MILLISECONDS = 14 * 60 * 1000;

function App() {
  const [user, setUser] = useState();
  const [initialized, setInitialized] = useState(false);

  const resetUser = useCallback(() => {
    refreshToken()
      .then((user) => {
        setUser(user);
        setTimeout(resetUser, TIMEOUT_MILLISECONDS);
      })
      .catch((err) => {
        console.log(err);
      })
      .finally(() => setInitialized(true));
  }, []);

  useEffect(() => {
    resetUser();
  }, [resetUser]);

  const auth = {
    user: user,
    handleLoggedIn(user) {
      setUser(user);
      setTimeout(resetUser, TIMEOUT_MILLISECONDS);
    },
    hasAuthority(authority) {
      return user?.authorities.includes(authority);
    },
    logout() {
      logout();
      setUser(null);
    },
  };

  if (!initialized) {
    return null;
  }

  const renderWithAuthority = (Component, ...authorities) => {
    for (let authority of authorities) {
      if (auth.hasAuthority(authority)) {
        return <Component />;
      }
    }
    return <Error />;
  };

  return (
    <div className="container">
      {/* <AuthContext.Provider value={auth}> */}
      <Router>
        <NavBar></NavBar>
        <Routes>
          <Route path='/' element={<Products />} />
          <Route path="/login" element={<LoginForm />} />
          <Route path="/signup" element={<SignUpForm />} />
          <Route path="/error" element={<Error />} />
          <Route path="*" element={<Error />} />
        </Routes>
      </Router>
      {/* </AuthContext.Provider> */}
    </div>
  );
}

export default App;
