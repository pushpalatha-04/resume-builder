import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import "../styles/home.css";

function Home() {
  const [user, setUser] = useState(null);
  const [showMenu, setShowMenu] = useState(false);

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) setUser(JSON.parse(storedUser));
  }, []);

  const logout = () => {
    localStorage.removeItem("user");
    setUser(null);
  };

  return (
    <div className="home-container">
      <nav className="navbar">
        <h2 className="logo">Resume Builder</h2>

        <div className="nav-right">
          {!user ? (
            <>
              <Link to="/login" className="nav-btn">Login</Link>
              <Link to="/signup" className="nav-btn">Signup</Link>
            </>
          ) : (
            <div className="profile">
              <span onClick={() => setShowMenu(!showMenu)}>
                {user.username}
              </span>
              {showMenu && (
                <div className="dropdown">
                  <button onClick={logout}>Logout</button>
                </div>
              )}
            </div>
          )}
        </div>
      </nav>

      <div className="hero">
        <h1>Build Your Professional Resume</h1>
        <p>Create clean and job-ready resumes easily.</p>

        <Link to="/create">
          <button className="primary-btn">Create Resume</button>
        </Link>
      </div>
    </div>
  );
}

export default Home;
