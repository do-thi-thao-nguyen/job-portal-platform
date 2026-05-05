import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export default function JobList() {
  const [jobList, setJobList] = useState([]);
  const [loading, setLoading] = useState(true);

  //  SEARCH
  const [keyword, setKeyword] = useState("");

  //  FILTER
  const [location, setLocation] = useState("");

  //  FILTER
  const [minSalary, setMinSalary] = useState("");

  //  SAVE JOB (BACKEND)
  const [savedJobs, setSavedJobs] = useState([]);

  const navigate = useNavigate();
  const email = localStorage.getItem("email");

  // ================= LOAD JOB =================
  useEffect(() => {
    fetch("http://localhost:8080/jobs")
      .then(res => {
        if (!res.ok) throw new Error("API lỗi");
        return res.json();
      })
      .then(data => {
        setJobList(Array.isArray(data) ? data : []);
        setLoading(false);
      })
      .catch(err => {
        console.error(err);
        setLoading(false);
      });
  }, []);

  // ================= LOAD SAVED JOB =================
  useEffect(() => {
    if (!email) return;

    fetch(`http://localhost:8080/saved-jobs?email=${email}`)
      .then(res => res.json())
      .then(data => {
        setSavedJobs(data.map(item => item.job.id));
      })
      .catch(err => console.error(err));
  }, [email]);

  // ================= SAVE / UNSAVE =================
  const toggleSaveJob = async (jobId) => {
    const isSaved = savedJobs.includes(jobId);

    try {
      if (isSaved) {
        await fetch(`http://localhost:8080/saved-jobs/${jobId}?email=${email}`, {
          method: "DELETE"
        });
      } else {
        await fetch(`http://localhost:8080/saved-jobs/${jobId}?email=${email}`, {
          method: "POST"
        });
      }

      // reload saved jobs
      const res = await fetch(`http://localhost:8080/saved-jobs?email=${email}`);
      const data = await res.json();
      setSavedJobs(data.map(item => item.job.id));

    } catch (err) {
      console.error(err);
    }
  };

  // ================= FILTER =================
  const filteredJobs = jobList.filter(job => {
    return (
      job.title?.toLowerCase().includes(keyword.toLowerCase()) &&
      (location === "" || job.location === location) &&
      (minSalary === "" || job.salaryMin >= Number(minSalary))
    );
  });

  return (
    <div style={{ padding: "20px", maxWidth: "900px", margin: "0 auto" }}>

      {/* HEADER */}
      <div style={{ 
        display: "flex", 
        justifyContent: "space-between", 
        alignItems: "center",
        marginBottom: "20px"
      }}>
        <div>
          <h2 style={{ color: "white", margin: 0 }}>
            Job List
          </h2>

          <p style={{ color: "white", margin: 0 }}>
            👤 {email}
          </p>
        </div>

        <div style={{ display: "flex", gap: "10px" }}>
          
          <button
            onClick={() => navigate("/my-applications")}
            style={{
              padding: "8px 15px",
              borderRadius: "8px",
              border: "none",
              background: "linear-gradient(135deg, #00b894, #55efc4)",
              color: "#fff",
              fontWeight: "bold",
              cursor: "pointer"
            }}
          >
            📄 Job đã ứng tuyển
          </button>

          <button
          onClick={() => navigate("/saved-jobs")}
          style={{
            padding: "8px 15px",
            borderRadius: "8px",
            border: "none",
            background: "#e84393",
            color: "#fff",
            fontWeight: "bold",
            cursor: "pointer"
            }}
          >
             Job đã lưu
          </button>
            <button
            onClick={() => navigate("/dashboard")}
            style={{
              padding: "8px 15px",
              borderRadius: "8px",
              border: "none",
              background: "#0984e3",
              color: "#fff",
              fontWeight: "bold",
              cursor: "pointer"
            }}
          >
            📊 Dashboard
          </button>

          <button
            onClick={() => {
              localStorage.clear();
              navigate("/login");
            }}
            style={{
              padding: "8px 15px",
              borderRadius: "8px",
              border: "none",
              background: "#ff4d4f",
              color: "#fff",
              fontWeight: "bold",
              cursor: "pointer"
            }}
          >
            Logout
          </button>

        </div>
      </div>

      {/*  SEARCH */}
      <input
        placeholder="🔍 Tìm job..."
        value={keyword}
        onChange={(e) => setKeyword(e.target.value)}
        style={{
          padding: "10px",
          width: "100%",
          marginBottom: "10px",
          borderRadius: "8px",
          border: "none"
        }}
      />

      {/*  LOCATION */}
      <select
        value={location}
        onChange={(e) => setLocation(e.target.value)}
        style={{
          padding: "10px",
          width: "100%",
          marginBottom: "10px",
          borderRadius: "8px"
        }}
      >
        <option value=""> Tất cả địa điểm</option>
        <option value="HCM">HCM</option>
        <option value="HN">HN</option>
      </select>

      {/*  SALARY */}
      <input
        placeholder=" Lương tối thiểu"
        value={minSalary}
        onChange={(e) => setMinSalary(e.target.value)}
        style={{
          padding: "10px",
          width: "100%",
          marginBottom: "20px",
          borderRadius: "8px",
          border: "none"
        }}
      />

      {/* LOADING */}
      {loading && (
        <p style={{ color: "white" }}>⏳ Loading...</p>
      )}

      {/* EMPTY */}
      {!loading && filteredJobs.length === 0 && (
        <p style={{ color: "white" }}>
          Không tìm thấy job 
        </p>
      )}

      {/* LIST */}
      {filteredJobs.map(job => {

        const isSaved = savedJobs.includes(job.id);

        return (
          <div
            key={job.id}
            style={{
              display: "flex",
              alignItems: "center",
              justifyContent: "space-between",
              background: "#fff",
              padding: "15px",
              marginBottom: "15px",
              borderRadius: "10px",
              boxShadow: "0 2px 8px rgba(0,0,0,0.1)",
              transition: "0.3s",
            }}
            onMouseEnter={(e) => {
              e.currentTarget.style.transform = "scale(1.02)";
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.transform = "scale(1)";
            }}
          >

            {/* LOGO */}
            <div style={{ width: "60px", marginRight: "15px" }}>
              <img
                src="https://placehold.co/50"
                alt="logo"
                style={{ width: "100%", borderRadius: "8px" }}
              />
            </div>

            {/* INFO */}
            <div style={{ flex: 1 }}>
              <h3 style={{ margin: 0 }}>
                <Link to={`/jobs/${job.id}`}>
                  {job.title}
                </Link>
              </h3>

              <p>{job.company?.name || job.company}</p>

              {job.salaryMin && job.salaryMax && (
                <p style={{ color: "red", fontWeight: "bold" }}>
                  {job.salaryMin.toLocaleString()} - {job.salaryMax.toLocaleString()}
                </p>
              )}

              <p>{job.location}</p>
            </div>

            {/* ACTION */}
            <div style={{ display: "flex", flexDirection: "column", gap: "10px" }}>

              {/*  SAVE */}
              <button
                onClick={() => toggleSaveJob(job.id)}
                style={{
                  background: isSaved ? "#ff7675" : "#dfe6e9",
                  color: isSaved ? "#fff" : "#333",
                  border: "none",
                  padding: "5px 10px",
                  borderRadius: "6px",
                  cursor: "pointer"
                }}
              >
                {isSaved ? " Đã lưu" : " Lưu"}
              </button>

              {/* APPLY */}
              <button
                disabled={job.status !== "APPROVED"}
                onClick={() => navigate(`/jobs/${job.id}`)}
                style={{
                  background:
                    job.status !== "APPROVED"
                      ? "#ccc"
                      : "#ff4d4f",
                  color: "#fff",
                  border: "none",
                  padding: "10px",
                  borderRadius: "8px",
                  cursor:
                    job.status !== "APPROVED"
                      ? "not-allowed"
                      : "pointer"
                }}
              >
                Ứng tuyển
              </button>

            </div>
          </div>
        );
      })}
    </div>
  );
}