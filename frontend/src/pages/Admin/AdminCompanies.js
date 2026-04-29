import { useEffect, useState } from "react";
import API from "../../services/api";
import AdminLayout from "./AdminLayout";

export default function AdminCompanies() {
  const [companies, setCompanies] = useState([]);

  useEffect(() => {
    fetchCompanies();
  }, []);

  const fetchCompanies = async () => {
    try {
      const res = await API.get("/api/admin/companies/pending");
      setCompanies(res.data);
    } catch (err) {
      console.error(err);
      alert("Load company failed");
    }
  };

  const handleApprove = async (id) => {
    try {
      await API.put(`/api/admin/companies/${id}/approve`);
      fetchCompanies();
    } catch (err) {
      alert("Approve failed");
    }
  };

  const handleReject = async (id) => {
    try {
      await API.put(`/api/admin/companies/${id}/reject`);
      fetchCompanies();
    } catch (err) {
      alert("Reject failed");
    }
  };

  return (
    <AdminLayout>
      <h1 style={{ color: "white" }}>🏢 Manage Companies</h1>

      {companies.length === 0 ? (
        <p style={{ color: "white" }}>No pending companies</p>
      ) : (
        companies.map((c) => (
          <div key={c.id} style={card}>
            <h3>{c.name}</h3>
            <p>{c.description}</p>

            <p>
              Status:{" "}
              <span style={{ color: "orange", fontWeight: "bold" }}>
                {c.status}
              </span>
            </p>

            <button onClick={() => handleApprove(c.id)} style={btnGreen}>
              Approve
            </button>

            <button onClick={() => handleReject(c.id)} style={btnRed}>
              Reject
            </button>
          </div>
        ))
      )}
    </AdminLayout>
  );
}

const card = {
  background: "white",
  padding: "20px",
  marginTop: "15px",
  borderRadius: "10px",
};

const btnGreen = {
  background: "#22c55e",
  color: "white",
  padding: "6px 12px",
  marginRight: "10px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};

const btnRed = {
  background: "#ef4444",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};