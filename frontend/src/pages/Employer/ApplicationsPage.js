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

  // ================= GET ALL =================
  const fetchApplications = async () => {
    try {
      const res = await API.get(`/applications/job/${jobId}`);
      setApplications(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load applications");
    }
  };

  // ================= SEARCH =================
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

  // ================= CONTACT =================
  const handleContact = async (id) => {
    try {
      await API.put(`/applications/${id}/contact`, {
        message: "We will contact you soon"
      });

      alert("Contacted successfully!");
      fetchApplications(); // reload lại list
    } catch (err) {
      console.error(err);
      alert("Contact failed");
    }
  };

  // ================= RESET =================
  const handleReset = () => {
    setKeyword("");
    setStatus("");
    fetchApplications();
  };

  return (
    <EmployerLayout>
      <h1>👥 Applications</h1>

      {/* SEARCH + FILTER */}
      <div style={{ marginTop: "20px", marginBottom: "20px" }}>
        <input
          type="text"
          placeholder="Search email..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
          style={{ marginRight: "10px", padding: "8px" }}
        />

        <select
          value={status}
          onChange={(e) => setStatus(e.target.value)}
          style={{ marginRight: "10px", padding: "8px" }}
        >
          <option value="">All</option>
          <option value="PENDING">Pending</option>
          <option value="CONTACTED">Contacted</option>
          <option value="APPROVED">Approved</option>
          <option value="REJECTED">Rejected</option>
        </select>

        <button onClick={handleSearch}>Search</button>
        <button onClick={handleReset} style={{ marginLeft: "10px" }}>
          Reset
        </button>
      </div>

      {/* LIST */}
      <div>
        {applications.length === 0 ? (
          <p>No applications yet</p>
        ) : (
          applications.map(app => (
            <div key={app.id} style={{
              background: "white",
              padding: "20px",
              borderRadius: "10px",
              marginBottom: "15px"
            }}>
              <p><b>Email:</b> {app.email}</p>

              <p>
                <b>CV:</b>{" "}
                <a href={app.cvUrl} target="_blank" rel="noreferrer">
                  View CV
                </a>
              </p>

              <p><b>Status:</b> {app.status}</p>

              {/* 🔥 NÚT CONTACT */}
              {app.status !== "CONTACTED" && (
                <button
                  onClick={() => handleContact(app.id)}
                  style={{
                    marginTop: "10px",
                    padding: "8px 12px",
                    background: "#4CAF50",
                    color: "white",
                    border: "none",
                    borderRadius: "5px",
                    cursor: "pointer"
                  }}
                >
                  Contact
                </button>
              )}
            </div>
          ))
        )}
      </div>
    </EmployerLayout>
  );
}