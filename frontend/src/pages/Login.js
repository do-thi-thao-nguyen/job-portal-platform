import { useState } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      if (!res.ok) {
        const text = await res.text();
        alert(text || "Login failed");
        return;
      }

      const data = await res.json();
      const token = data.token || data.accessToken;

      if (!token) {
        alert("Không nhận được token");
        return;
      }

      console.log("TOKEN:", token);
      localStorage.setItem("token", token);
      localStorage.setItem("role", data.role || "");
      localStorage.setItem("email", email);

      const role = data.role || "";

      if (role.includes("ROLE_ADMIN")) {
        navigate("/admin/jobs");
      } else if (role.includes("ROLE_EMPLOYER")) {
        navigate("/employer");
      } else {
        navigate("/jobs");
      }

    } catch (err) {
      console.error(err);
      alert("Login failed");
    }
  };

  return (
    <div style={wrapper}>
      <div style={card}>
        <h2 style={title}>Login</h2>

        <input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          style={input}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          style={input}
        />

        <button onClick={handleLogin} style={button}>
          Login
        </button>

        <p style={footer}>
          Chưa có tài khoản?{" "}
          <span style={link} onClick={() => navigate("/register")}>
            Đăng ký
          </span>
        </p>
      </div>
    </div>
  );
}

export default Login;

//
// ===== STYLE =====
//

const wrapper = {
  minHeight: "100vh",
  display: "flex",
  justifyContent: "center",
  alignItems: "center",
  background: "linear-gradient(to right, #2c3e50, #e84393)" // 🔥 giống hình
};

const card = {
  background: "#fff",
  padding: "30px",
  borderRadius: "12px",
  width: "320px",
  boxShadow: "0 10px 30px rgba(0,0,0,0.2)",
  textAlign: "center"
};

const title = {
  marginBottom: "20px"
};

const input = {
  width: "100%",
  padding: "10px",
  marginBottom: "12px",
  borderRadius: "8px",
  border: "1px solid #ddd",
  outline: "none"
};

const button = {
  width: "100%",
  padding: "10px",
  borderRadius: "8px",
  border: "none",
  background: "linear-gradient(to right, #6c5ce7, #e84393)",
  color: "#fff",
  fontWeight: "bold",
  cursor: "pointer",
  transition: "0.3s"
};

const footer = {
  marginTop: "15px",
  fontSize: "13px"
};

const link = {
  color: "#e84393",
  cursor: "pointer",
  fontWeight: "bold"
};