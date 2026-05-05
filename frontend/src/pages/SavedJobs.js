import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function SavedJobs() {
  const [jobs, setJobs] = useState([]);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();

  const email = localStorage.getItem("email");
  const token = localStorage.getItem("token");

  // ================= LOAD =================
  const loadSavedJobs = async () => {
    if (!email || !token) return;

    setLoading(true);

    try {
      const res = await fetch(
        `http://localhost:8080/saved-jobs?email=${email}`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (!res.ok) {
        console.error("Lỗi API:", res.status);
        setJobs([]);
        setLoading(false);
        return;
      }

      const data = await res.json();
      setJobs(Array.isArray(data) ? data : []);
      setLoading(false);

    } catch (err) {
      console.error(err);
      setLoading(false);
    }
  };

  useEffect(() => {
    loadSavedJobs();
  }, [email, token]);

  // ================= REMOVE =================
  const handleRemove = async (jobId) => {
    const confirm = window.confirm("Bạn có chắc muốn bỏ lưu?");
    if (!confirm) return;

    try {
      const res = await fetch(
        `http://localhost:8080/saved-jobs/${jobId}?email=${email}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (!res.ok) {
        alert("Xóa thất bại!");
        return;
      }

      loadSavedJobs();

    } catch (err) {
      console.error(err);
    }
  };

  // ================= UI =================
  return (
    <div
      style={{
        padding: "20px",
        minHeight: "100vh",
        background: "linear-gradient(to right, #2c3e50, #e84393)"
      }}
    >

      {/* HEADER */}
      <div
        style={{
          maxWidth: "900px",
          margin: "0 auto",
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          marginBottom: "25px"
        }}
      >
        <h2 style={{ color: "#fff" }}>💾 Job đã lưu</h2>

        <button
          onClick={() => navigate("/jobs")}
          style={{
            background: "#6c5ce7",
            color: "#fff",
            border: "none",
            padding: "8px 15px",
            borderRadius: "8px",
            cursor: "pointer"
          }}
        >
          ← Quay lại
        </button>
      </div>

      {/* CONTENT */}
      <div style={{ maxWidth: "900px", margin: "0 auto" }}>

        {/* LOADING */}
        {loading && <p style={{ color: "#fff" }}>⏳ Loading...</p>}

        {/* EMPTY */}
        {!loading && jobs.length === 0 && (
          <p style={{ color: "#fff" }}>Bạn chưa lưu job nào</p>
        )}

        {/* LIST */}
        {jobs.map(item => {
          if (!item.job) return null;

          const job = item.job;

          return (
            <div
              key={item.id}
              onClick={() => navigate(`/jobs/${job.id}`)}
              style={{
                display: "flex",
                justifyContent: "space-between",
                alignItems: "center",
                background: "#fff",
                padding: "18px",
                marginBottom: "15px",
                borderRadius: "14px",
                boxShadow: "0 6px 20px rgba(0,0,0,0.2)",
                cursor: "pointer",
                transition: "0.25s"
              }}
              onMouseEnter={(e) =>
                (e.currentTarget.style.transform = "scale(1.03)")
              }
              onMouseLeave={(e) =>
                (e.currentTarget.style.transform = "scale(1)")
              }
            >

              {/* LEFT */}
              <div style={{ display: "flex", gap: "15px" }}>

                <img
                  src="https://placehold.co/70"
                  alt="logo"
                  style={{ borderRadius: "10px" }}
                />

                <div>
                  <h3 style={{ margin: 0 }}>{job.title}</h3>
                  <p style={{ margin: 0 }}>{job.company?.name}</p>

                  <p style={{ color: "red", margin: 0 }}>
                    {job.salaryMin?.toLocaleString()} -{" "}
                    {job.salaryMax?.toLocaleString()}
                  </p>

                  <p style={{ margin: 0 }}>{job.location}</p>
                </div>
              </div>

              {/* RIGHT */}
              <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>

                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    handleRemove(job.id);
                  }}
                  style={{
                    background: "#ff4d4f",
                    color: "#fff",
                    border: "none",
                    padding: "8px 12px",
                    borderRadius: "8px",
                    cursor: "pointer"
                  }}
                >
                  ❌ Bỏ lưu
                </button>

                <button
                  onClick={(e) => {
                    e.stopPropagation();
                    navigate(`/jobs/${job.id}`);
                  }}
                  style={{
                    background: "#00b894",
                    color: "#fff",
                    border: "none",
                    padding: "8px 12px",
                    borderRadius: "8px",
                    cursor: "pointer"
                  }}
                >
                   Xem
                </button>

              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}