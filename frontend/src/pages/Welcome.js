import { useNavigate } from "react-router-dom";

export default function Welcome() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");

  const handleLogout = () => {
    localStorage.removeItem("token");
    alert("Logged out");
    navigate("/");
  };

  return (
    <div className="welcome-container">
      <h1>🚀 Job Portal</h1>
      <p>Find your dream job or hire top talent</p>

      {token ? (
        <>
          <h3>✅ You are logged in</h3>
          <button className="btn btn-primary" onClick={handleLogout}>
            Logout
          </button>
        </>
      ) : (
        <>
          <button
            className="btn btn-primary"
            onClick={() => navigate("/login")}
          >
            Login
          </button>

          <button
            className="btn btn-primary"
            onClick={() => navigate("/register")}
          >
            Register
          </button>
        </>
      )}
    </div>
  );
}