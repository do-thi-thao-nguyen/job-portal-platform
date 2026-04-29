import { useEffect, useState } from "react";
import API from "../../services/api";
import AdminLayout from "./AdminLayout";

export default function AdminJobs() {
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const res = await API.get("/api/admin/jobs/pending");
      setJobs(res.data);
    } catch (err) {
      console.error(err);
      alert("Load jobs failed");
    }
  };

  const handleApprove = async (id) => {
    try {
      await API.put(`/api/admin/jobs/${id}/approve`);
      fetchJobs();
    } catch (err) {
      alert("Approve failed");
    }
  };

  const handleReject = async (id) => {
    try {
      await API.put(`/api/admin/jobs/${id}/reject`);
      fetchJobs();
    } catch (err) {
      alert("Reject failed");
    }
  };

  return (
    <AdminLayout>
      <h1 style={{ color: "white" }}>📄 Manage Jobs</h1>

      {jobs.length === 0 ? (
        <p style={{ color: "white" }}>No pending jobs</p>
      ) : (
        jobs.map((j) => (
          <div key={j.id} style={card}>
            <h3>{j.title}</h3>
            <p>{j.description}</p>
            <p><b>Location:</b> {j.location}</p>
            <p><b>Salary:</b> {j.salaryMin} - {j.salaryMax}</p>

            <p>
              Status:{" "}
              <span style={{ color: "orange", fontWeight: "bold" }}>
                {j.status}
              </span>
            </p>

            <button onClick={() => handleApprove(j.id)} style={btnGreen}>
              Approve
            </button>

            <button onClick={() => handleReject(j.id)} style={btnRed}>
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