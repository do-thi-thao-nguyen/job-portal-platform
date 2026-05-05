import { useEffect, useState } from "react";
import { Link } from "react-router-dom";

export default function MyApplications() {
  const [applications, setApplications] = useState([]);
  const [loading, setLoading] = useState(true);

  const email = localStorage.getItem("email") || "user@gmail.com";

  // ================= LOAD DATA =================
  const loadData = () => {
    setLoading(true); 
    fetch(`http://localhost:8080/applications/my?email=${email}`)
      .then(res => {
        if (!res.ok) throw new Error("API lỗi");
        return res.json();
      })
      .then(data => {
        setApplications(data);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  };

  useEffect(() => {
    loadData();
  }, [email]);

  // ================= CANCEL =================
  const handleCancel = async (id) => {
    const confirm = window.confirm("Bạn chắc chắn muốn hủy?");
    if (!confirm) return;

    try {
      await fetch(`http://localhost:8080/applications/${id}`, {
        method: "DELETE"
      });

      alert("Đã hủy ứng tuyển!");
      loadData();

    } catch (err) {
      console.error(err);
      alert("Lỗi server");
    }
  };

  // ================= UI =================
  return (
    <div style={{
      maxWidth: "900px",
      margin: "20px auto",
      padding: "20px"
    }}>

      <h2 style={{ color: "white", marginBottom: "20px" }}>
        📄 Job đã ứng tuyển
      </h2>

      <p style={{ color: "white" }}>
        👤 {email}
      </p>

      {/* LOADING */}
      {loading && (
        <p style={{ color: "white" }}>⏳ Loading...</p>
      )}

      {/* EMPTY */}
      {!loading && applications.length === 0 && (
        <p style={{ color: "white" }}>
          Bạn chưa ứng tuyển job nào
        </p>
      )}

      {/* LIST */}
      {applications.map(app => (
        <div
          key={app.id}
          style={{
            background: "#fff",
            padding: "20px",
            marginBottom: "15px",
            borderRadius: "12px",
            boxShadow: "0 4px 12px rgba(0,0,0,0.1)"
          }}
        >
          {/* TITLE */}
          <h3>
            <Link to={`/jobs/${app.job?.id}`}>
              {app.job?.title || "No title"}
            </Link>
          </h3>

          <p>{app.job?.company?.name || "No company"}</p>

          <p style={{ color: "red" }}>
            {app.job?.salaryMin?.toLocaleString?.() || 0} - {app.job?.salaryMax?.toLocaleString?.() || 0}
          </p>

          <p>{app.job?.location}</p>

          {/* CV */}
          {app.cvUrl && (
            <>
              <button
                onClick={() => window.open(
                  `http://localhost:8080/uploads/${app.cvUrl}`,
                  "_blank"
                )}
                style={{
                  marginTop: "10px",
                  background: "#007bff",
                  color: "#fff",
                  padding: "6px 12px",
                  border: "none",
                  borderRadius: "8px",
                  cursor: "pointer"
                }}
              >
                👁️ Xem CV
              </button>

              {/* 🔥 PREVIEW PDF */}
              <iframe
                src={`http://localhost:8080/uploads/${app.cvUrl}`}
                width="100%"
                height="300px"
                style={{
                  marginTop: "10px",
                  borderRadius: "10px",
                  border: "1px solid #ddd"
                }}
              />
            </>
          )}

          {/* STATUS */}
          <div style={{ marginTop: "10px" }}>
            <span style={{
              padding: "6px 12px",
              borderRadius: "20px",
              fontWeight: "bold",
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

          {/* CANCEL */}
          <button
            onClick={() => handleCancel(app.id)}
            style={{
              marginTop: "15px",
              background: "#dc3545",
              color: "#fff",
              padding: "6px 12px",
              borderRadius: "8px",
              border: "none",
              cursor: "pointer"
            }}
          >
            Hủy ứng tuyển
          </button>

        </div>
      ))}
    </div>
  );
}