import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function JobDetail() {
  const { id } = useParams();

  const [job, setJob] = useState(null);
  const [application, setApplication] = useState(null);
  const [file, setFile] = useState(null);

  const email = localStorage.getItem("email");
  const token = localStorage.getItem("token");

  // ================= LOAD DATA =================
  const loadData = async () => {
    try {
      // 🔹 load job (không cần token)
      const jobRes = await fetch(`http://localhost:8080/jobs/${id}`);
      const jobData = await jobRes.json();
      setJob(jobData);

      // 🔹 load application (cần token)
      if (!token || !email) return;

      const res = await fetch(
        `http://localhost:8080/applications/my?email=${email}`,
        {
          headers: {
            Authorization: `Bearer ${token}`
          }
        }
      );

      if (!res.ok) {
        console.error("API lỗi:", res.status);
        return;
      }

      const apps = await res.json();

      const found = apps.find(app => app.job?.id === Number(id));
      setApplication(found || null);

    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    loadData();
  }, [id]);

  // ================= APPLY =================
  const handleApply = async () => {
    try {
      const formData = new FormData();
      formData.append("email", email);

      if (file) {
        formData.append("file", file);
      }

      const res = await fetch(`http://localhost:8080/applications/${id}`, {
        method: "POST",
        headers: {
          Authorization: `Bearer ${token}`
        },
        body: formData
      });

      if (!res.ok) {
        alert("Apply failed (403 hoặc lỗi auth)");
        return;
      }

      alert("Ứng tuyển thành công!");
      setFile(null);
      loadData();

    } catch (err) {
      console.error(err);
    }
  };

  // ================= CANCEL =================
  const handleCancel = async () => {
    if (!application) return;

    const confirm = window.confirm("Bạn có chắc muốn hủy ứng tuyển?");
    if (!confirm) return;

    try {
      await fetch(`http://localhost:8080/applications/${application.id}`, {
        method: "DELETE",
        headers: {
          Authorization: `Bearer ${token}`
        }
      });

      alert("Đã hủy ứng tuyển!");
      loadData();

    } catch (err) {
      console.error(err);
    }
  };

  // ================= UI =================
  if (!job) return <p style={{ color: "white" }}>Loading...</p>;

  return (
    <div style={{ maxWidth: "1000px", margin: "30px auto", padding: "20px" }}>
      
      <div style={{
        background: "#fff",
        borderRadius: "16px",
        padding: "25px",
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        boxShadow: "0 8px 25px rgba(0,0,0,0.15)"
      }}>

        <div>
          <h2>{job.title}</h2>
          <p>{job.company?.name}</p>
          <p>{job.location}</p>
        </div>

        <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end" }}>

          {!application && (
            <div
              style={{
                border: "2px dashed #ccc",
                padding: "15px",
                borderRadius: "10px",
                marginBottom: "10px",
                width: "240px",
                textAlign: "center",
                background: "#fafafa",
                cursor: "pointer"
              }}
              onClick={() => document.getElementById("fileInput").click()}
            >
              {!file ? (
                <p>Kéo thả CV hoặc click để chọn</p>
              ) : (
                <div>
                  <p>📄 {file.name}</p>
                  <button onClick={(e) => {
                    e.stopPropagation();
                    setFile(null);
                  }}>Xóa</button>
                </div>
              )}

              <input
                id="fileInput"
                type="file"
                accept=".pdf"
                style={{ display: "none" }}
                onChange={(e) => setFile(e.target.files[0])}
              />
            </div>
          )}

          {application ? (
          <button
            onClick={handleCancel}
            style={{
              padding: "10px 20px",
              borderRadius: "10px",
              border: "none",
              background: "#636e72",
              color: "#fff",
              cursor: "pointer"
            }}
          >
            ❌ Hủy ứng tuyển
          </button>
        ) : (
          <button
            onClick={handleApply}
            disabled={!file}
            style={{
              marginTop: "10px",
              padding: "12px 20px",
              borderRadius: "10px",
              border: "none",
              fontWeight: "bold",
              fontSize: "14px",
              width: "240px",
              background: !file 
                ? "#dfe6e9" 
                : "linear-gradient(135deg, #ff4d4f, #ff7675)",
              color: !file ? "#999" : "#fff",
              cursor: !file ? "not-allowed" : "pointer",
              transition: "0.3s",
              boxShadow: !file 
                ? "none" 
                : "0 4px 12px rgba(255,77,79,0.4)"
            }}
            onMouseEnter={(e) => {
              if (file) {
                e.currentTarget.style.transform = "scale(1.05)";
              }
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.transform = "scale(1)";
            }}
          >
            {file ? "Ứng tuyển ngay" : "Chọn CV để ứng tuyển"}
          </button>
        )}

        </div>
      </div>
    </div>
  );
}