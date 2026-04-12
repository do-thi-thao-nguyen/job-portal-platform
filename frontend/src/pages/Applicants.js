const applicants = [
  { id: 1, email: "user1@gmail.com", jobId: 1 },
  { id: 2, email: "user2@gmail.com", jobId: 1 },
];

export default function Applicants() {
  return (
    <div>
      <h2>Applicants</h2>

      {applicants.map((a) => (
        <div key={a.id}>
          <p>{a.email}</p>
        </div>
      ))}
    </div>
  );
}