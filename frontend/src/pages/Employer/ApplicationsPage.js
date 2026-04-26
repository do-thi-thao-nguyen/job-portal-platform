import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function ApplicationsPage() {

  const { jobId } = useParams();
  const [applications, setApplications] = useState([]);

  useEffect(() => {
    fetchApplications();
  }, []);

  const fetchApplications = async () => {
    try {
      const res = await API.get(`/applications/job/${jobId}`);
      console.log("Applications:", res.data); // 👈 debug
      setApplications(res.data);
    } catch (err) {
      console.error(err);
      alert("Failed to load applications");
    }
  };

  return (
    <EmployerLayout>
      <h1>👥 Applications</h1>

      <div style={{ marginTop: "20px" }}>
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
              <p><b>CV:</b> {app.cvUrl}</p>
              <p><b>Status:</b> {app.status}</p>
            </div>
          ))
        )}
      </div>
    </EmployerLayout>
  );
}