import { useEffect, useState } from "react";
import API from "../../services/api";

export default function JobsPage() {
  const [jobs, setJobs] = useState([]);

  useEffect(() => {
    API.get("/jobs")
      .then(res => setJobs(res.data))
      .catch(err => console.error(err));
  }, []);

  return (
    <div>
      <h2>Danh sách công việc</h2>

      {jobs.map(job => (
        <div key={job.id}>
          <h3>{job.title}</h3>
          <p>{job.location}</p>
          <p>{job.salaryMin} - {job.salaryMax}</p>
        </div>
      ))}
    </div>
  );
}