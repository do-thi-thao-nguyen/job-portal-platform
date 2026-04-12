const jobs = [
  { id: 1, title: "Java Dev", company: "FPT", location: "HCM" },
  { id: 2, title: "Frontend Dev", company: "VNG", location: "HCM" },
];

export default function JobList() {
  return (
    <div>
      <h2>Job List</h2>

      {jobs.map((job) => (
        <div key={job.id} style={{ border: "1px solid", margin: 10 }}>
          <h3>{job.title}</h3>
          <p>{job.company}</p>
          <p>{job.location}</p>
        </div>
      ))}
    </div>
  );
}