import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function UserDashboard() {
  const [stats, setStats] = useState({
    total: 0,
    pending: 0,
    contacted: 0,
    rejected: 0
  });

  const email = localStorage.getItem("email");
  const navigate = useNavigate();

  useEffect(() => {
    fetch(`http://localhost:8080/applications/my?email=${email}`)
      .then(res => res.json())
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
      });
  }, [email]);

  return (
    <div style={{ padding: "30px", color: "white" }}>

      {/* HEADER */}
      <div style={{
        display: "flex",
        justifyContent: "space-between",
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

      {/* CARD */}
      <div style={{ display: "flex", gap: "20px", flexWrap: "wrap" }}>

        <Card title="Tổng" value={stats.total} color="#0984e3" />
        <Card title="Đang chờ" value={stats.pending} color="#fdcb6e" />
        <Card title="Đã liên hệ" value={stats.contacted} color="#00b894" />
        <Card title="Bị từ chối" value={stats.rejected} color="#d63031" />

      </div>

    </div>
  );
}

function Card({ title, value, color }) {
  return (
    <div style={{
      background: color,
      padding: "20px",
      borderRadius: "12px",
      width: "160px",
      textAlign: "center",
      boxShadow: "0 4px 12px rgba(0,0,0,0.2)"
    }}>
      <h2 style={{ margin: 0 }}>{value}</h2>
      <p>{title}</p>
    </div>
  );
}