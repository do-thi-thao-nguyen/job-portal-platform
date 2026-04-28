import { useNavigate } from "react-router-dom";

export default function EmployerLayout({ children }) {

  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  return (
    <div className="dashboard">

      <div className="sidebar">
        <h2>🏢 Employer</h2>

        <p onClick={() => navigate("/employer")} style={{ cursor: "pointer" }}>
          Dashboard
        </p>

        <li onClick={() => navigate("/employer/company")}>
          My Company
        </li>

        <p onClick={() => navigate("/employer/jobs")} style={{ cursor: "pointer" }}>
          My Jobs
        </p>

        <p onClick={() => navigate("/employer/create")} style={{ cursor: "pointer" }}>
          Create Job
        </p>

        <p onClick={() => navigate("/packages")}>
          Buy Package
        </p>

        <hr />

        <p style={{ color: "red", cursor: "pointer" }} onClick={handleLogout}>
          Logout
        </p>
      </div>

      <div className="content">
        {children}
      </div>

    </div>
  );
}