import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // 🔥 thêm dòng này
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function MyJobs() {

  const [jobs, setJobs] = useState([]);
  const navigate = useNavigate(); // 🔥 thêm dòng này

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

  return (
    <EmployerLayout>
      <h1>📄 My Jobs</h1>

      <div style={{ marginTop: "20px" }}>
        {jobs.length === 0 ? (
          <p>No jobs yet</p>
        ) : (
          jobs.map(job => (
            <div key={job.id} style={{
              background: "white",
              padding: "20px",
              borderRadius: "10px",
              marginBottom: "15px",
              boxShadow: "0 5px 15px rgba(0,0,0,0.1)"
            }}>
              <h3>{job.title}</h3>
              <p>{job.description}</p>

              <p>
                Status:{" "}
                <span style={{
                  color: job.status === "APPROVED" ? "green" : "orange",
                  fontWeight: "bold"
                }}>
                  {job.status}
                </span>
              </p>

              <button
                onClick={() => navigate(`/employer/jobs/${job.id}/applications`)}
                style={{
                  marginTop: "10px",
                  padding: "8px 15px",
                  background: "#007bff",
                  color: "white",
                  border: "none",
                  borderRadius: "5px",
                  cursor: "pointer"
                }}
              >
                View Applications
              </button>

            </div>
          ))
        )}
      </div>
    </EmployerLayout>
  );
}