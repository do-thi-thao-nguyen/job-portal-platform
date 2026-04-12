import { useState } from "react";

export default function Company() {
  const [name, setName] = useState("");

  const handleCreate = () => {
    alert("Company created (mock)");
  };

  return (
    <div>
      <h2>Create Company</h2>

      <input
        placeholder="Company Name"
        onChange={(e) => setName(e.target.value)}
      />

      <button onClick={handleCreate}>Create</button>
    </div>
  );
}