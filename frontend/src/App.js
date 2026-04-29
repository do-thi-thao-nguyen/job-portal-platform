import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";

import Navbar from "./components/Navbar";

// PUBLIC
import Login from "./pages/Login";
import Register from "./pages/Register";
import Welcome from "./pages/Welcome";

// USER
import JobList from "./pages/JobList";
import JobDetail from "./pages/JobDetails";
import MyApplications from "./pages/MyApplications";
import UserDashboard from "./pages/UserDashboard";
import SavedJobs from "./pages/SavedJobs";

// EMPLOYER
import EmployerDashboard from "./pages/Employer/EmployerDashboard";
import MyJobs from "./pages/Employer/MyJobs";
import CreateJob from "./pages/Employer/CreateJob";
import CreateCompany from "./pages/Employer/CreateCompany";
import MyCompany from "./pages/Employer/MyCompany";
import EditCompany from "./pages/Employer/EditCompany";
import ApplicationsPage from "./pages/Employer/ApplicationsPage";

// PROTECT
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  return (
    <BrowserRouter>

      {/* NAVBAR */}
      <Navbar />

      <Routes>

        {/* ================= PUBLIC ================= */}
        <Route path="/" element={<Welcome />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* ================= USER ================= */}

        <Route
          path="/jobs"
          element={
            <ProtectedRoute>
              <JobList />
            </ProtectedRoute>
          }
        />

        <Route
          path="/jobs/:id"
          element={
            <ProtectedRoute>
              <JobDetail />
            </ProtectedRoute>
          }
        />

        <Route
          path="/my-applications"
          element={
            <ProtectedRoute>
              <MyApplications />
            </ProtectedRoute>
          }
        />

        <Route
          path="/dashboard"
          element={
            <ProtectedRoute>
              <UserDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/saved-jobs"
          element={
            <ProtectedRoute>
              <SavedJobs />
            </ProtectedRoute>
          }
        />

        {/* ================= EMPLOYER ================= */}

        <Route
          path="/employer"
          element={
            <ProtectedRoute role="EMPLOYER">
              <EmployerDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/jobs"
          element={
            <ProtectedRoute role="EMPLOYER">
              <MyJobs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/create"
          element={
            <ProtectedRoute role="EMPLOYER">
              <CreateJob />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/company"
          element={
            <ProtectedRoute role="EMPLOYER">
              <MyCompany />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/company/create"
          element={
            <ProtectedRoute role="EMPLOYER">
              <CreateCompany />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/company/edit"
          element={
            <ProtectedRoute role="EMPLOYER">
              <EditCompany />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/jobs/:jobId/applications"
          element={
            <ProtectedRoute role="EMPLOYER">
              <ApplicationsPage />
            </ProtectedRoute>
          }
        />

      </Routes>
    </BrowserRouter>
  );
}

export default App;