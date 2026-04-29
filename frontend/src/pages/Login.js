import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  // AUTO REDIRECT nếu đã login
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      navigate("/jobs");
    }
  }, [navigate]);

  const handleLogin = async () => {

    // ✅ VALIDATE
    if (!email || !password) {
      alert("Vui lòng nhập đầy đủ email và password");
      return;
    }

    try {
      setLoading(true);

      const res = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await res.json();
      console.log(data);

      if (!res.ok) {
        alert(data.error || "Login failed");
        setLoading(false);
        return;
      }

      // LƯU LOCAL
      localStorage.setItem("token", data.token);
      localStorage.setItem("role", data.role);
      localStorage.setItem("email", data.email);

      const role = data.role;

      // REDIRECT
      if (role === "EMPLOYER") {
        navigate("/employer/jobs");
      } else {
        navigate("/jobs");
      }

    } catch (err) {
      console.error(err);
      alert("Server error");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={{
      minHeight: "100vh",
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      background: "linear-gradient(to right, #2c3e50, #e84393)"
    }}>
      <div style={{
        background: "white",
        padding: "30px",
        borderRadius: "12px",
        width: "320px",
        boxShadow: "0 8px 25px rgba(0,0,0,0.2)"
      }}>
        <h2 style={{ marginBottom: "20px" }}>Login</h2>

        {/* EMAIL */}
        <input
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleLogin()}
          style={{
            width: "100%",
            marginBottom: "10px",
            padding: "10px",
            borderRadius: "8px",
            border: "1px solid #ccc"
          }}
        />

        {/* PASSWORD */}
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          onKeyDown={(e) => e.key === "Enter" && handleLogin()}
          style={{
            width: "100%",
            marginBottom: "15px",
            padding: "10px",
            borderRadius: "8px",
            border: "1px solid #ccc"
          }}
        />

        {/* BUTTON */}
        <button
          onClick={handleLogin}
          disabled={loading}
          style={{
            width: "100%",
            padding: "10px",
            borderRadius: "8px",
            border: "none",
            background: loading ? "#ccc" : "#e84393",
            color: "#fff",
            fontWeight: "bold",
            cursor: loading ? "not-allowed" : "pointer",
            transition: "0.3s"
          }}
        >
          {loading ? "Đang đăng nhập..." : "Login"}
        </button>

      </div>
    </div>
  );
}

export default Login;