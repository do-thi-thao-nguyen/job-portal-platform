import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ children, roleRequired }) {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  // chưa login
  if (!token) {
    return <Navigate to="/login" />;
  }

  // check role đúng format ROLE_
  if (roleRequired && role !== `ROLE_${roleRequired}`) {
    if (role === "ROLE_EMPLOYER") {
      return <Navigate to="/employer" />;
    }

    return <Navigate to="/jobs" />;
  }

  return children;
}