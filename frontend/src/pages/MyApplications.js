import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function MyApplications() {
const [applications, setApplications] = useState([]);
const [loading, setLoading] = useState(true);

const email = localStorage.getItem("email");
const token = localStorage.getItem("token");
const navigate = useNavigate();

// ================= LOAD DATA =================
const loadData = () => {
if (!email || !token) return;

setLoading(true);

fetch(`http://localhost:8080/applications/my?email=${email}`, {
  headers: {
    Authorization: `Bearer ${token}`
  }
})
  .then(res => res.ok ? res.json() : [])
  .then(data => {
    setApplications(Array.isArray(data) ? data : []);
    setLoading(false);
  })
  .catch(() => setLoading(false));
  };

useEffect(() => {
loadData();
}, [email, token]);

// ================= CANCEL =================
const handleCancel = async (id) => {
const confirm = window.confirm("Bạn chắc chắn muốn hủy ứng tuyển?");
if (!confirm) return;

await fetch(`http://localhost:8080/applications/${id}`, {
  method: "DELETE",
  headers: {
    Authorization: `Bearer ${token}`
  }
});

loadData();

};

return (
<div style={{ padding: "20px", maxWidth: "900px", margin: "0 auto" }}>

  {/* HEADER */}
  <div style={{
    display: "flex",
    justifyContent: "space-between",
    marginBottom: "20px"
  }}>
    <h2 style={{ color: "white" }}> Job đã ứng tuyển</h2>

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

  {/* LOADING */}
  {loading && <p style={{ color: "white" }}>⏳ Loading...</p>}

  {/* EMPTY */}
  {!loading && applications.length === 0 && (
    <p style={{ color: "white" }}>Bạn chưa ứng tuyển job nào</p>
  )}

  {/* LIST */}
  {applications.map(app => {
    const job = app.job;

    return (
      <div
        key={app.id}
        style={{
          display: "flex",
          flexDirection: "column",
          background: "#fff",
          padding: "20px",
          marginBottom: "20px",
          borderRadius: "12px",
          boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
        }}
      >

        {/* TOP */}
        <div style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center"
        }}>

          {/* LEFT */}
          <div style={{ display: "flex", gap: "15px", alignItems: "center" }}>
            <img
              src="https://placehold.co/60"
              alt="logo"
              style={{ borderRadius: "8px" }}
            />

            <div>
              <h3 style={{ margin: 0 }}>
                <Link to={`/jobs/${job?.id}`}>
                  {job?.title || "No title"}
                </Link>
              </h3>

              <p style={{ margin: 0 }}>{job?.company?.name}</p>

              <p style={{ color: "red", margin: 0 }}>
                {job?.salaryMin?.toLocaleString()} - {job?.salaryMax?.toLocaleString()}
              </p>

              <p style={{ margin: 0 }}>{job?.location}</p>

              {/* STATUS */}
              <div style={{ marginTop: "5px" }}>
                <span style={{
                  padding: "5px 10px",
                  borderRadius: "20px",
                  fontWeight: "bold",
                  fontSize: "12px",
                  background:
                    app.status === "PENDING"
                      ? "#fff3cd"
                      : app.status === "CONTACTED"
                      ? "#cce5ff"
                      : "#f8d7da",
                  color:
                    app.status === "PENDING"
                      ? "#856404"
                      : app.status === "CONTACTED"
                      ? "#004085"
                      : "#721c24"
                }}>
                  {app.status === "PENDING" && "⏳ Đang chờ"}
                  {app.status === "CONTACTED" && "📞 Đã liên hệ"}
                  {app.status === "REJECTED" && "❌ Bị từ chối"}
                </span>
              </div>

              {/* CV BUTTON */}
              {app.cvUrl && (
                <button
                  onClick={() =>
                    window.open(`http://localhost:8080/uploads/${app.cvUrl}`, "_blank")
                  }
                  style={{
                    marginTop: "8px",
                    background: "#0984e3",
                    color: "#fff",
                    border: "none",
                    padding: "6px 12px",
                    borderRadius: "6px",
                    cursor: "pointer"
                  }}
                >
                  📄 Xem CV
                </button>
              )}
            </div>
          </div>

          {/* RIGHT */}
          <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
            <button
              onClick={() => navigate(`/jobs/${job?.id}`)}
              style={{
                background: "#00b894",
                color: "#fff",
                border: "none",
                padding: "8px 12px",
                borderRadius: "8px",
                cursor: "pointer"
              }}
            >
              Xem chi tiết
            </button>

            <button
              onClick={() => handleCancel(app.id)}
              style={{
                background: "#ff4d4f",
                color: "#fff",
                border: "none",
                padding: "8px 12px",
                borderRadius: "8px",
                cursor: "pointer"
              }}
            >
              ❌ Hủy
            </button>
          </div>

        </div>

        {/* ================= PREVIEW CV ================= */}
        {app.cvUrl && (
          <div style={{ marginTop: "15px" }}>
            <object
              data={`http://localhost:8080/uploads/${app.cvUrl}`}
              type="application/pdf"
              width="100%"
              height="250px"
              style={{ borderRadius: "8px", border: "1px solid #ccc" }}
            >
              <p>
                Không xem được CV.
                <a
                  href={`http://localhost:8080/uploads/${app.cvUrl}`}
                  target="_blank"
                  rel="noreferrer"
                >
                  Mở tại đây
                </a>
              </p>
            </object>
          </div>
        )}

      </div>
    );
  })}
</div>
);
}