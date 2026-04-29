import { Navigate } from "react-router-dom";

export default function ProtectedRoute({ children, roleRequired }) {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role"); // 🔥 nhớ lưu role

  if (!token) {
    return <Navigate to="/login" />;
  }

  if (roleRequired && !role?.includes(roleRequired)) {
    return <Navigate to="/" />; // ❗ KHÔNG return null
  }

  return children;
}