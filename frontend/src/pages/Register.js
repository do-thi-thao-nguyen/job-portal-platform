import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [companyName, setCompanyName] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleRegister = async () => {
    if (!email || !password) {
      alert("Vui lòng nhập đầy đủ thông tin");
      return;
    }

    try {
      setLoading(true);

      await API.post("/auth/register", {
        email,
        password,
        companyName: companyName || null
      });

      alert("Register success");
      navigate("/login");
    } catch (err) {
      console.error(err);
      alert("Register failed");
    } finally {
      setLoading(false);
    }
  };

  return (
  <div style={{
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center"
  }}>
    <div style={{
      background: "white",
      padding: "30px",
      borderRadius: "12px",
      width: "350px",
      boxShadow: "0 8px 20px rgba(0,0,0,0.1)"
    }}>

      <h2 style={{ marginBottom: "20px", textAlign: "center" }}>
        Register
      </h2>

      <input
        placeholder="Email"
        value={email}
        onChange={(e) => setEmail(e.target.value)}
        style={inputStyle}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        style={inputStyle}
      />

      <input
        placeholder="Company (optional)"
        value={companyName}
        onChange={(e) => setCompanyName(e.target.value)}
        style={inputStyle}
      />

      <button
        onClick={handleRegister}
        disabled={loading}
        style={{
          width: "100%",
          padding: "12px",
          background: loading ? "#aaa" : "#6366f1",
          color: "white",
          border: "none",
          borderRadius: "8px",
          cursor: "pointer",
          fontSize: "15px"
        }}
      >
        {loading ? "Đang đăng ký..." : "Register"}
      </button>

    </div>
  </div>
);
}

// style reuse
const inputStyle = {
  width: "100%",
  padding: "10px",
  marginBottom: "12px",
  borderRadius: "8px",
  border: "1px solid #ddd",
  fontSize: "14px",
  outline: "none"
};