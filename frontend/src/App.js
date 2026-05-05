import { BrowserRouter, Routes, Route, useLocation } from "react-router-dom";
import "./App.css";

// COMPONENT
import Navbar from "./components/Navbar";
import ProtectedRoute from "./components/ProtectedRoute";

// PUBLIC
import Login from "./pages/Login";
import Register from "./pages/Register";
import Welcome from "./pages/Welcome";

// USER
import JobList from "./pages/JobList";
import JobDetail from "./pages/JobDetail";
import MyApplications from "./pages/MyApplications";
import UserNotifications from "./pages/UserNotifications";
import UserDashboard from "./pages/UserDashboard";
import SavedJobs from "./pages/SavedJobs";

// EMPLOYER
import EmployerDashboard from "./pages/Employer/EmployerDashboard";
import MyCompany from "./pages/Employer/MyCompany";
import CreateCompany from "./pages/Employer/CreateCompany";
import EditCompany from "./pages/Employer/EditCompany";
import MyJobs from "./pages/Employer/MyJobs";
import CreateJob from "./pages/Employer/CreateJob";
import EditJob from "./pages/Employer/EditJob";
import ApplicationsPage from "./pages/Employer/ApplicationsPage";
import PackagesPage from "./pages/Employer/PackagesPage";
import PaymentSuccess from "./pages/Employer/PaymentSuccess";

// ADMIN
import AdminDashboard from "./pages/Admin/AdminDashboard";
import AdminJobs from "./pages/Admin/AdminJobs";
import AdminCompanies from "./pages/Admin/AdminCompanies";
import AdminCategories from "./pages/Admin/AdminCategories";
import AdminUsers from "./pages/Admin/AdminUsers";
import AdminNotifications from "./pages/Admin/AdminNotifications";

// ================= FIX NAVBAR =================
function AppContent() {
 const location = useLocation();

  const hideNavbarRoutes = ["/", "/login", "/register"];

  const shouldHideNavbar = hideNavbarRoutes.includes(location.pathname);

  return (
    <>
      {!shouldHideNavbar && <Navbar />}

      <Routes>
        {/* PUBLIC */}
        <Route path="/" element={<Welcome />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* USER */}
        <Route
          path="/notifications"
          element={
            <ProtectedRoute>
              <UserNotifications />
            </ProtectedRoute>
          }
        />

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

        {/* EMPLOYER */}
        <Route
          path="/employer"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <EmployerDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/company"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <MyCompany />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/company/create"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <CreateCompany />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/company/edit"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <EditCompany />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/jobs"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <MyJobs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/create"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <CreateJob />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/jobs/edit/:id"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <EditJob />
            </ProtectedRoute>
          }
        />

        <Route
          path="/employer/jobs/:jobId/applications"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <ApplicationsPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/packages"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <PackagesPage />
            </ProtectedRoute>
          }
        />

        <Route
          path="/payment-success"
          element={
            <ProtectedRoute roleRequired="EMPLOYER">
              <PaymentSuccess />
            </ProtectedRoute>
          }
        />

        {/* ADMIN */}
        <Route
          path="/admin/jobs"
          element={
            <ProtectedRoute roleRequired="ADMIN">
              <AdminJobs />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/companies"
          element={
            <ProtectedRoute roleRequired="ADMIN">
              <AdminCompanies />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/categories"
          element={
            <ProtectedRoute roleRequired="ADMIN">
              <AdminCategories />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/users"
          element={
            <ProtectedRoute roleRequired="ADMIN">
              <AdminUsers />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute roleRequired="ADMIN">
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/notifications"
          element={
            <ProtectedRoute roleRequired="ADMIN">
              <AdminNotifications />
            </ProtectedRoute>
          }
        />
      </Routes>
    </>
  );
}

// ROOT
function App() {
  return (
    <BrowserRouter>
      <AppContent />
    </BrowserRouter>
  );
}

export default App;