import { useEffect, useState } from "react";
import EmployerLayout from "./EmployerLayout";
import axios from "axios";

export default function EmployerDashboard() {
  const [totalJobs, setTotalJobs] = useState(0);
  const [pendingJobs, setPendingJobs] = useState(0);
  const [approvedJobs, setApprovedJobs] = useState(0);

  useEffect(() => {
    const fetchJobs = async () => {
      try {
        const token = localStorage.getItem("token");

        const res = await axios.get("http://localhost:8080/jobs/my", {
          headers: {
            Authorization: `Bearer ${token}`
          }
        });

        console.log("jobs:", res.data); // 👉 debug

        setTotalJobs(res.data.length);

        setPendingJobs(
          res.data.filter(j => j.status === "PENDING").length
        );
        setApprovedJobs(
          res.data.filter(j => j.status === "APPROVED").length
        );

      } catch (err) {
        console.error("Error fetch jobs:", err);
      }
    };

    fetchJobs();
  }, []);

  return (
    <EmployerLayout>
      <h1>📊 Employer Dashboard</h1>

      <div style={{ display: "flex", gap: "20px", marginTop: "20px" }}>
        
        <div style={{
          background: "white",
          padding: "20px",
          borderRadius: "10px",
          width: "150px"
        }}>
          <h3>Total Jobs</h3>
          <p>{totalJobs}</p>
        </div>

        <div style={{
          background: "white",
          padding: "20px",
          borderRadius: "10px",
          width: "150px"
        }}>
          <h3>Pending Jobs</h3>
          <p>{pendingJobs}</p>
        </div>

        <div style={{
        background: "white",
        padding: "20px",
        borderRadius: "10px",
        width: "150px"
      }}>
        <h3>Approved Jobs</h3>
        <p>{approvedJobs}</p>
      </div>

      </div>
    </EmployerLayout>
  );
}