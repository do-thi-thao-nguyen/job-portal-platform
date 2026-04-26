import EmployerLayout from "./EmployerLayout";

export default function EmployerDashboard() {
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
          <p>0</p>
        </div>

        <div style={{
          background: "white",
          padding: "20px",
          borderRadius: "10px",
          width: "150px"
        }}>
          <h3>Pending Jobs</h3>
          <p>0</p>
        </div>
      </div>
    </EmployerLayout>
  );
}