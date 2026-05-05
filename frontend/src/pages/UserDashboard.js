import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function UserDashboard() {
  const [stats, setStats] = useState({
    total: 0,
    pending: 0,
    contacted: 0,
    rejected: 0
  });

  const [loading, setLoading] = useState(true);

  const email = localStorage.getItem("email");
  const token = localStorage.getItem("token");

  const navigate = useNavigate();

  useEffect(() => {
    if (!email || !token) return;

    fetch(`http://localhost:8080/applications/my?email=${email}`, {
      headers: {
        Authorization: `Bearer ${token}`
      }
    })
      .then(res => {
        if (!res.ok) {
          console.error("API lỗi:", res.status);
          return [];
        }
        return res.json();
      })
      .then(data => {
        const s = {
          total: data.length,
          pending: 0,
          contacted: 0,
          rejected: 0
        };

        data.forEach(app => {
          if (app.status === "PENDING") s.pending++;
          if (app.status === "CONTACTED") s.contacted++;
          if (app.status === "REJECTED") s.rejected++;
        });

        setStats(s);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, [email, token]);

  return (
    <div style={{
      padding: "30px",
      minHeight: "100vh",
      background: "linear-gradient(to right, #2c3e50, #e84393)",
      color: "white"
    }}>

      {/* HEADER */}
      <div style={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        marginBottom: "30px"
      }}>
        <h2>📊 Dashboard</h2>

        <button
          onClick={() => navigate("/jobs")}
          style={{
            padding: "8px 15px",
            borderRadius: "8px",
            border: "none",
            background: "#6c5ce7",
            color: "#fff",
            cursor: "pointer"
          }}
        >
          ← Quay lại Job
        </button>
      </div>

      {/* LOADING */}
      {loading && <p>⏳ Loading...</p>}

      {/* CARDS */}
      {!loading && (
        <div style={{
          display: "grid",
          gridTemplateColumns: "repeat(auto-fit, minmax(180px, 1fr))",
          gap: "20px"
        }}>

          <Card title="Tổng" value={stats.total} color="#0984e3" />
          <Card title="Đang chờ" value={stats.pending} color="#fdcb6e" />
          <Card title="Đã liên hệ" value={stats.contacted} color="#00b894" />
          <Card title="Bị từ chối" value={stats.rejected} color="#d63031" />

        </div>
      )}
    </div>
  );
}

// CARD COMPONENT
function Card({ title, value, color }) {
  return (
    <div style={{
      background: color,
      padding: "25px",
      borderRadius: "16px",
      textAlign: "center",
      boxShadow: "0 6px 20px rgba(0,0,0,0.3)",
      transition: "0.3s",
      cursor: "pointer"
    }}
      onMouseEnter={(e) => {
        e.currentTarget.style.transform = "scale(1.05)";
      }}
      onMouseLeave={(e) => {
        e.currentTarget.style.transform = "scale(1)";
      }}
    >
      <h1 style={{ margin: 0, fontSize: "32px" }}>{value}</h1>
      <p style={{ margin: "10px 0 0" }}>{title}</p>
    </div>
  );
}