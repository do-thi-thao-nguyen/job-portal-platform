import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function SavedJobs() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  const email = localStorage.getItem("email");
  const navigate = useNavigate();

  const loadSavedJobs = () => {
    fetch(`http://localhost:8080/saved-jobs?email=${email}`)
      .then(res => res.json())
      .then(data => {
        setJobs(data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  };

  useEffect(() => {
    loadSavedJobs();
  }, [email]);

  const handleRemove = async (jobId) => {
    const confirm = window.confirm("Bỏ lưu job này?");
    if (!confirm) return;

    try {
      await fetch(`http://localhost:8080/saved-jobs/${jobId}?email=${email}`, {
        method: "DELETE"
      });

      loadSavedJobs();

    } catch (err) {
      console.error(err);
      alert("Lỗi khi xoá");
    }
  };

  return (
    <div style={{ padding: "20px", maxWidth: "900px", margin: "0 auto" }}>

      <div style={{
        display: "flex",
        justifyContent: "space-between",
        marginBottom: "20px"
      }}>
        <h2 style={{ color: "white" }}> Job đã lưu</h2>

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
          ← Quay lại
        </button>
      </div>

      {loading && <p style={{ color: "white" }}>⏳ Loading...</p>}

      {!loading && jobs.length === 0 && (
        <p style={{ color: "white" }}>Bạn chưa lưu job nào</p>
      )}

      {jobs.map(item => {
        if (!item.job) return null;

        const job = item.job;

        return (
          <div
            key={item.id}
            style={{
              background: "#fff",
              padding: "20px",
              marginBottom: "15px",
              borderRadius: "12px",
              boxShadow: "0 4px 12px rgba(0,0,0,0.1)",
              cursor: "pointer",
              transition: "0.3s"
            }}
            onClick={() => navigate(`/jobs/${job.id}`)}

            onMouseEnter={(e) => {
              e.currentTarget.style.transform = "scale(1.03)";
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.transform = "scale(1)";
            }}
          >
            <h3>{job.title}</h3>

            <p>{job.company?.name}</p>

            <p style={{ color: "red" }}>
              {job.salaryMin?.toLocaleString()} - {job.salaryMax?.toLocaleString()}
            </p>

            <p>{job.location}</p>

            <button
              onClick={(e) => {
                e.stopPropagation();
                handleRemove(job.id);
              }}
              style={{
                marginTop: "10px",
                background: "#dc3545",
                color: "#fff",
                border: "none",
                padding: "6px 12px",
                borderRadius: "8px",
                cursor: "pointer"
              }}
            >
              ❌ Bỏ lưu
            </button>
          </div>
        );
      })}
    </div>
  );
}