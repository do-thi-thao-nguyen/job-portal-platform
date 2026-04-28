import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function MyJobs() {

  const [jobs, setJobs] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchJobs();
  }, []);

  const fetchJobs = async () => {
    try {
      const res = await API.get("/jobs/my");
      setJobs(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load jobs");
    }
  };

  // 🔥 THÊM FUNCTION DELETE
  const handleDelete = async (id) => {
    const confirmDelete = window.confirm("Bạn có chắc muốn xóa job này?");
    if (!confirmDelete) return;

    try {
      await API.delete(`/jobs/${id}`);
      alert("Đã xóa job");
      fetchJobs(); // reload lại list
    } catch (err) {
      console.error(err);
      alert("Xóa job thất bại");
    }
  };

  return (
    <EmployerLayout>
      <h1>📄 My Jobs</h1>

      <div style={{ marginTop: "20px" }}>
        {jobs.length === 0 ? (
          <p>No jobs yet</p>
        ) : (
          jobs.map(job => (
            <div key={job.id} style={cardStyle}>
              <h3>{job.title}</h3>
              <p>{job.description}</p>
              <p>{job.location}</p>
              <p> {job.salaryMin} - {job.salaryMax}</p>

              <p>
                Status:{" "}
                <span style={{
                  color: job.status === "APPROVED" ? "green" : "orange",
                  fontWeight: "bold"
                }}>
                  {job.status}
                </span>
              </p>

              {/* 🔥 BUTTON GROUP */}
              <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>

                <button
                  onClick={() => navigate(`/employer/jobs/${job.id}/applications`)}
                  style={viewBtn}
                >
                  View Applications
                </button>

                <button
                  onClick={() => handleDelete(job.id)}
                  style={deleteBtn}
                >
                  Delete
                </button>

                <button
                onClick={() => navigate(`/employer/jobs/edit/${job.id}`)}
                style={{
                  padding: "8px 15px",
                  background: "#10b981",
                  color: "white",
                  border: "none",
                  borderRadius: "5px",
                  cursor: "pointer"
                }}
              >
                Edit
              </button>

              </div>

            </div>
          ))
        )}
      </div>
    </EmployerLayout>
  );
}


// ===== STYLE =====

const cardStyle = {
  background: "white",
  padding: "20px",
  borderRadius: "10px",
  marginBottom: "15px",
  boxShadow: "0 5px 15px rgba(0,0,0,0.1)"
};

const viewBtn = {
  padding: "8px 15px",
  background: "#007bff",
  color: "white",
  border: "none",
  borderRadius: "5px",
  cursor: "pointer"
};

const deleteBtn = {
  padding: "8px 15px",
  background: "#ef4444",
  color: "white",
  border: "none",
  borderRadius: "5px",
  cursor: "pointer"
};