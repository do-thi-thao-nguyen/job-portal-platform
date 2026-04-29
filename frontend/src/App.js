import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";

// 🔐 Protected
import ProtectedRoute from "./components/ProtectedRoute";

// ADMIN
import AdminJobs from "./pages/Admin/AdminJobs";
import AdminCompanies from "./pages/Admin/AdminCompanies";
import AdminCategories from "./pages/Admin/AdminCategories";
import AdminUsers from "./pages/Admin/AdminUsers";
import AdminDashboard from "./pages/Admin/AdminDashboard";
import AdminNotifications from "./pages/Admin/AdminNotifications";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Welcome from "./pages/Welcome";

// NOTIFICATIONS
import UserNotifications from "./pages/UserNotifications";

// EMPLOYER

import EmployerDashboard from "./pages/Employer/EmployerDashboard";
import MyJobs from "./pages/Employer/MyJobs";
import CreateJob from "./pages/Employer/CreateJob";
import CreateCompany from "./pages/Employer/CreateCompany";
import MyCompany from "./pages/Employer/MyCompany";
import EditCompany from "./pages/Employer/EditCompany";
import ApplicationsPage from "./pages/Employer/ApplicationsPage";
import PackagesPage from "./pages/Employer/PackagesPage";
import PaymentSuccess from "./pages/Employer/PaymentSuccess";
import EditJob from "./pages/Employer/EditJob";

function App() {
  return (
    <BrowserRouter>
      <Routes>

  {/* ================= PUBLIC ================= */}
  <Route path="/" element={<Welcome />} />
  <Route path="/login" element={<Login />} />
  <Route path="/register" element={<Register />} />

  <Route
    path="/notifications"
    element={
      <ProtectedRoute>
        <UserNotifications />
      </ProtectedRoute>
    }
  />

  {/* ================= EMPLOYER ================= */}
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

  {/* ================= ADMIN ================= */}
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
    </BrowserRouter>
  );
}

export default App;