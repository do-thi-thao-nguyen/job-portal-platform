import { useEffect, useState } from "react";
import API from "../../services/api";
import AdminLayout from "./AdminLayout";

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);

  useEffect(() => {
    fetchReport();
  }, []);

  const fetchReport = async () => {
    try {
      const res = await API.get("/api/admin/reports");
      setStats(res.data);
    } catch (err) {
      console.error(err);
      alert("Load dashboard failed");
    }
  };

  if (!stats) {
    return (
      <AdminLayout>
        <p style={{ color: "white" }}>Loading...</p>
      </AdminLayout>
    );
  }

  return (
    <AdminLayout>
      <h1 style={{ color: "white" }}>📊 System Dashboard</h1>

      <div style={grid}>
        <Card title="👤 Users" value={stats.totalUsers} />
        <Card title="🏢 Employers" value={stats.totalEmployers} />

        <Card title="🏢 Companies" value={stats.totalCompanies} />
        <Card title="⏳ Pending Companies" value={stats.pendingCompanies} />
        <Card title="✅ Approved Companies" value={stats.approvedCompanies} />

        <Card title="📄 Jobs" value={stats.totalJobs} />
        <Card title="⏳ Pending Jobs" value={stats.pendingJobs} />
        <Card title="✅ Approved Jobs" value={stats.approvedJobs} />
      </div>
    </AdminLayout>
  );
}

// ===== COMPONENT CARD =====
function Card({ title, value }) {
  return (
    <div style={card}>
      <h3 style={{ marginBottom: "10px" }}>{title}</h3>
      <h1 style={{ color: "#1e293b" }}>{value}</h1>
    </div>
  );
}

// ===== STYLE =====
const grid = {
  display: "grid",
  gridTemplateColumns: "repeat(3, 1fr)",
  gap: "20px",
  marginTop: "20px",
};

const card = {
  background: "white",
  padding: "20px",
  borderRadius: "12px",
  textAlign: "center",
  boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
};