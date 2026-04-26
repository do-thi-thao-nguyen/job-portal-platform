import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [companyName, setCompanyName] = useState("");

  const navigate = useNavigate();

  const handleRegister = async () => {
    try {
      await API.post(`/auth/register?companyName=${companyName}`, {
        email,
        password
      });

      alert("Register success");
      navigate("/login");
    } catch {
      alert("Register failed");
    }
  };

  return (
  <div className="container">
    <h2>Register</h2>

    <input
      className="input"
      placeholder="Email"
      onChange={(e) => setEmail(e.target.value)}
    />

    <input
      className="input"
      type="password"
      placeholder="Password"
      onChange={(e) => setPassword(e.target.value)}
    />

    <input
      className="input"
      placeholder="Company (optional)"
      onChange={(e) => setCompanyName(e.target.value)}
    />

    <button className="btn btn-primary" onClick={handleRegister}>
      Register
    </button>
  </div>
);
}