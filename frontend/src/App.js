import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Welcome from "./pages/Welcome";

import EmployerDashboard from "./pages/Employer/EmployerDashboard";
import MyJobs from "./pages/Employer/MyJobs";
import CreateJob from "./pages/Employer/CreateJob";
import CreateCompany from "./pages/Employer/CreateCompany";
import MyCompany from "./pages/Employer/MyCompany";
import EditCompany from "./pages/Employer/EditCompany";
import ApplicationsPage from "./pages/Employer/ApplicationsPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* PUBLIC */}
        <Route path="/" element={<Login />} />
        <Route path="/welcome" element={<Welcome />} />
        <Route path="/register" element={<Register />} />

        {/* EMPLOYER */}
        <Route path="/employer" element={<EmployerDashboard />} />
        <Route path="/employer/company" element={<MyCompany />} />
        <Route path="/employer/company/create" element={<CreateCompany />} />
        <Route path="/employer/company/edit" element={<EditCompany />} />

        <Route path="/employer/jobs" element={<MyJobs />} />
        <Route path="/employer/create" element={<CreateJob />} />

        {/* 🔥 QUAN TRỌNG */}
        <Route
          path="/employer/jobs/:jobId/applications"
          element={<ApplicationsPage />}
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;