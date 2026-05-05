import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function ApplicationsPage() {
  const { jobId } = useParams();

  const [applications, setApplications] = useState([]);
  const [keyword, setKeyword] = useState("");
  const [status, setStatus] = useState("");

  useEffect(() => {
    fetchApplications();
  }, [jobId]);

  const fetchApplications = async () => {
    try {
      const res = await API.get(`/applications/job/${jobId}`);
      setApplications(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load applications");
    }
  };

  const handleSearch = async () => {
    try {
      const res = await API.get(`/applications/job/${jobId}/search`, {
        params: {
          email: keyword || undefined,
          status: status || undefined
        }
      });

      setApplications(res.data);
    } catch (err) {
      console.error(err);
      alert("Search failed");
    }
  };

  const handleContact = async (id) => {
    try {
      await API.put(`/applications/${id}/contact`, {
        message: "We will contact you soon"
      });

      alert("Đã liên hệ ứng viên!");
      fetchApplications();
    } catch (err) {
      console.error(err);
      alert("Contact failed");
    }
  };

  const handleReset = () => {
    setKeyword("");
    setStatus("");
    fetchApplications();
  };

  // 🎯 màu status
  const getStatusColor = (status) => {
    switch (status) {
      case "PENDING":
        return "#fbbf24";
      case "CONTACTED":
        return "#22c55e";
      case "APPROVED":
        return "#3b82f6";
      case "REJECTED":
        return "#ef4444";
      default:
        return "#999";
    }
  };

  return (
    <EmployerLayout>
      <h1 style={{ marginBottom: "20px" }}>👥 Applications</h1>

      {/* 🔍 SEARCH */}
      <div style={searchBox}>
        <input
          type="text"
          placeholder="🔎 Search email..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          style={input}
        />

        <select
          value={status}
          onChange={(e) => setStatus(e.target.value)}
          style={input}
        >
          <option value="">All status</option>
          <option value="PENDING">Pending</option>
          <option value="CONTACTED">Contacted</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
        </select>

        <button style={btnSearch} onClick={handleSearch}>
          🔍 Search
        </button>

        <button style={btnReset} onClick={handleReset}>
          Reset
        </button>
      </div>

      {/* 📄 LIST */}
      <div>
        {applications.length === 0 ? (
          <p style={{ color: "#555" }}>No applications yet</p>
        ) : (
          applications.map(app => {
            const email =
              app.user?.email || app.email || "Không có email";

            return (
              <div key={app.id} style={card}>

                {/* LEFT */}
                <div>
                  <h3 style={{ margin: 0 }}>📧 {email}</h3>

                  <p style={{ margin: "5px 0" }}>
                    📄{" "}
                    <a
                    href={`http://localhost:8080${app.cvUrl}`}
                    target="_blank"
                    rel="noreferrer"
                    style={{ color: "#2563eb", fontWeight: "bold" }}
                  >
                    View CV
                  </a>
                  </p>

                  <span
                    style={{
                      ...statusTag,
                      background: getStatusColor(app.status)
                    }}
                  >
                    {app.status}
                  </span>
                </div>

                {/* RIGHT */}
                <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>
                  {app.status !== "CONTACTED" && (
                    <button
                      onClick={() => handleContact(app.id)}
                      style={btnContact}
                    >
                      📩 Contact
                    </button>
                  )}
                </div>

              </div>
            );
          })
        )}
      </div>
    </EmployerLayout>
  );
}

//
// ===== STYLE =====
//

const searchBox = {
  display: "flex",
  gap: "10px",
  marginBottom: "20px",
  flexWrap: "wrap"
};

const input = {
  padding: "10px",
  borderRadius: "8px",
  border: "1px solid #ddd",
  minWidth: "200px"
};

const btnSearch = {
  background: "#3b82f6",
  color: "white",
  border: "none",
  padding: "10px 16px",
  borderRadius: "8px",
  cursor: "pointer"
};

const btnReset = {
  background: "#6b7280",
  color: "white",
  border: "none",
  padding: "10px 16px",
  borderRadius: "8px",
  cursor: "pointer"
};

const card = {
  background: "white",
  padding: "20px",
  borderRadius: "12px",
  marginBottom: "15px",
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
  boxShadow: "0 4px 12px rgba(0,0,0,0.08)"
};

const statusTag = {
  padding: "5px 10px",
  borderRadius: "20px",
  color: "white",
  fontSize: "12px",
  fontWeight: "bold",
  display: "inline-block",
  marginTop: "5px"
};

const btnContact = {
  background: "#22c55e",
  color: "white",
  border: "none",
  padding: "8px 12px",
  borderRadius: "8px",
  cursor: "pointer"
};

const btnView = {
  background: "#6366f1",
  color: "white",
  border: "none",
  padding: "8px 12px",
  borderRadius: "8px",
  cursor: "pointer"
};