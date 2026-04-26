import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await API.post("/auth/login", { email, password });

      const token = res.data;
      localStorage.setItem("token", token);

      // 🔥 decode role từ token
      const payload = JSON.parse(atob(token.split(".")[1]));
      const role = payload.role;

      // 🔥 redirect theo role
      if (role === "EMPLOYER") {
        navigate("/employer");
      } else if (role === "ADMIN") {
        navigate("/admin");
      } else {
        navigate("/");
      }

    } catch (err) {
      alert("Login failed");
    }
  };

  return (
    <div>
      <h2>Login</h2>

      <input placeholder="Email" onChange={e => setEmail(e.target.value)} />
      <input placeholder="Password" type="password" onChange={e => setPassword(e.target.value)} />

      <button onClick={handleLogin}>Login</button>
    </div>
  );
}