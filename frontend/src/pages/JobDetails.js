export default function JobDetail() {
  const handleApply = () => {
    alert("Applied (mock)");
  };

  return (
    <div>
      <h2>Java Developer</h2>
      <p>Job description...</p>

      <button onClick={handleApply}>Apply</button>
    </div>
  );
}