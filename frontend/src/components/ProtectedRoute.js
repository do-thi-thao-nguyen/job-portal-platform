import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ children, role }) {
  const token = localStorage.getItem("token");
  const userRole = localStorage.getItem("role");

  //  chưa login
  if (!token) {
    return <Navigate to="/login" />;
  }
  // BONUS: CHẶN EMPLOYER VÀO USER PAGE
  if (userRole === "EMPLOYER" && !role) {
    return <Navigate to="/employer/jobs" />;
  }
  //  sai role
  if (role && userRole !== role) {
    return <Navigate to="/jobs" />;
  }

  return children;
}