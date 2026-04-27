import { BrowserRouter, Routes, Route, Link } from "react-router-dom";

import Welcome from "./pages/Welcome";
import Login from "./pages/Login";
import Register from "./pages/Register";
import JobList from "./pages/JobList";
import JobDetail from "./pages/JobDetail";
import Company from "./pages/Company";
import Applicants from "./pages/Applicants";
import CreateJob from "./pages/Employer/CreateJob";
import CreateCompany from "./pages/Employer/CreateCompany";
import MyJobs from "./pages/Employer/MyJobs";
import ApplicationsPage from "./pages/Employer/ApplicationsPage";

function App() {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  return (
    <BrowserRouter>

      {/* 🔥 NAVBAR */}
      <nav style={{ padding: "10px", background: "#222", color: "#fff" }}>
        <Link to="/" style={{ marginRight: 10, color: "#fff" }}>Home</Link>
        <Link to="/jobs" style={{ marginRight: 10, color: "#fff" }}>Jobs</Link>

        {!token && (
          <>
            <Link to="/login" style={{ marginRight: 10, color: "#fff" }}>Login</Link>
            <Link to="/register" style={{ marginRight: 10, color: "#fff" }}>Register</Link>
          </>
        )}

        {role === "EMPLOYER" && (
          <>
            <Link to="/employer/jobs" style={{ marginRight: 10, color: "#fff" }}>My Jobs</Link>
            <Link to="/employer/create" style={{ marginRight: 10, color: "#fff" }}>Create Job</Link>
            <Link to="/employer/company/create" style={{ marginRight: 10, color: "#fff" }}>Create Company</Link>
          </>
        )}

        {token && (
          <button
            onClick={() => {
              localStorage.clear();
              window.location.href = "/";
            }}
            style={{ marginLeft: 10 }}
          >
            Logout
          </button>
        )}
      </nav>

      {/* 🔥 ROUTES */}
      <Routes>
        <Route path="/" element={<Welcome />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />

        <Route path="/jobs" element={<JobList />} />
        <Route path="/job/:id" element={<JobDetail />} />

        <Route path="/company" element={<Company />} />
        <Route path="/applicants" element={<Applicants />} />

        {/* EMPLOYER */}
        <Route path="/employer/jobs" element={<MyJobs />} />
        <Route path="/employer/create" element={<CreateJob />} />
        <Route path="/employer/company/create" element={<CreateCompany />} />
        <Route path="/employer/jobs/:jobId/applications" element={<ApplicationsPage />} />
      </Routes>

    </BrowserRouter>
  );
}

export default App;