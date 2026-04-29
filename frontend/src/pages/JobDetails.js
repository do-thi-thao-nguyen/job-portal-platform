import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

export default function JobDetail() {
  const { id } = useParams();

  const [job, setJob] = useState(null);
  const [application, setApplication] = useState(null);
  const [file, setFile] = useState(null); 

  const email = localStorage.getItem("email");

  // ================= LOAD DATA =================
  const loadData = async () => {
    try {
      const jobRes = await fetch(`http://localhost:8080/jobs/${id}`);
      const jobData = await jobRes.json();
      setJob(jobData);

      const res = await fetch(
        `http://localhost:8080/applications/my?email=${email}`
      );
      const apps = await res.json();

      const found = apps.find(app => app.job?.id === Number(id));
      setApplication(found || null);

    } catch (err) {
      console.error(err);
    }
  };

  useEffect(() => {
    if (email) loadData();
  }, [id, email]);

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
        body: formData
      });

      const data = await res.json();

      if (!res.ok) {
        alert(data.error || "Apply failed");
        return;
      }

      alert("Ứng tuyển thành công!");
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
        method: "DELETE"
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

        {/* JOB INFO */}
        <div>
          <h2>{job.title}</h2>
          <p>{job.company?.name}</p>
          <p>{job.location}</p>
        </div>

        {/* ACTION */}
        <div style={{ display: "flex", flexDirection: "column", alignItems: "flex-end" }}>

          {/* ================= UPLOAD ================= */}
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
              onDragOver={(e) => e.preventDefault()}
              onDrop={(e) => {
                e.preventDefault();
                const f = e.dataTransfer.files[0];

                if (!f) return;

                if (f.type !== "application/pdf") {
                  alert("Chỉ được upload file PDF!");
                  return;
                }

                if (f.size > 5 * 1024 * 1024) {
                  alert("File quá lớn (max 5MB)");
                  return;
                }

                setFile(f);
              }}
            >
              {!file ? (
                <p style={{ fontSize: "14px", color: "#666" }}>
                   Kéo thả CV hoặc click để chọn
                </p>
              ) : (
                <div>
                  <p style={{ fontSize: "13px" }}>
                    📄 {file.name} ({(file.size / 1024 / 1024).toFixed(2)} MB)
                  </p>

                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      setFile(null);
                    }}
                    style={{
                      marginTop: "5px",
                      background: "#ff4d4f",
                      color: "#fff",
                      border: "none",
                      padding: "4px 8px",
                      borderRadius: "6px",
                      cursor: "pointer"
                    }}
                  >
                    Xóa
                  </button>
                </div>
              )}

              <input
                id="fileInput"
                type="file"
                accept=".pdf"
                style={{ display: "none" }}
                onChange={(e) => {
                  const f = e.target.files[0];

                  if (!f) return;

                  if (f.type !== "application/pdf") {
                    alert("Chỉ được upload file PDF!");
                    return;
                  }

                  if (f.size > 5 * 1024 * 1024) {
                    alert("File quá lớn (max 5MB)");
                    return;
                  }

                  setFile(f);
                }}
              />
            </div>
          )}

          {/* ================= BUTTON ================= */}
          {application ? (
            <button
              onClick={handleCancel}
              style={{
                background: "linear-gradient(135deg, #6c757d, #495057)",
                color: "#fff",
                padding: "12px 20px",
                borderRadius: "12px",
                border: "none",
                cursor: "pointer"
              }}
            >
              Hủy ứng tuyển
            </button>
          ) : (
            <button
              onClick={handleApply}
              disabled={!file}
              style={{
                opacity: !file ? 0.5 : 1,
                cursor: !file ? "not-allowed" : "pointer",
                background: "linear-gradient(135deg, #ff4d4f, #ff7875)",
                color: "#fff",
                padding: "12px 20px",
                borderRadius: "12px",
                border: "none"
              }}
            >
               Ứng tuyển ngay
            </button>
          )}

        </div>

      </div>
    </div>
  );
}