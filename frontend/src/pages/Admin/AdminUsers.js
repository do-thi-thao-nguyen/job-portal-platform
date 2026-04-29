import { useEffect, useState } from "react";
import API from "../../services/api";
import AdminLayout from "./AdminLayout";

export default function AdminUsers() {
  const [users, setUsers] = useState([]);
  const [editingId, setEditingId] = useState(null);
  const [email, setEmail] = useState("");

  useEffect(() => {
    fetchUsers();
  }, []);

  const fetchUsers = async () => {
    try {
      const res = await API.get("/api/admin/users/employers");
      setUsers(res.data);
    } catch (err) {
      console.error(err);
      alert("Load users failed");
    }
  };

  // 🔥 DELETE
  const handleDelete = async (id) => {
    if (!window.confirm("Delete this user?")) return;

    try {
      await API.delete(`/api/admin/users/${id}`);
      fetchUsers();
    } catch (err) {
      alert(err?.response?.data?.message || "Delete failed");
    }
  };

  // 🔥 EDIT CLICK
  const handleEdit = (user) => {
    setEditingId(user.id);
    setEmail(user.email);
  };

  // 🔥 UPDATE
  const handleUpdate = async () => {
    try {
      await API.put(`/api/admin/users/${editingId}`, {
        email: email,
      });

      setEditingId(null);
      setEmail("");
      fetchUsers();
    } catch (err) {
      alert("Update failed");
    }
  };

  return (
    <AdminLayout>
      <h1 style={{ color: "white" }}>👨‍💼 Manage Employers</h1>

      {users.length === 0 ? (
        <p style={{ color: "white" }}>No employers found</p>
      ) : (
        users.map((u) => (
          <div key={u.id} style={card}>
            {editingId === u.id ? (
              <>
                <input
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  style={input}
                />

                <button onClick={handleUpdate} style={btnBlue}>
                  Save
                </button>

                <button
                  onClick={() => setEditingId(null)}
                  style={btnGray}
                >
                  Cancel
                </button>
              </>
            ) : (
              <>
                <h3>{u.email}</h3>
                <p>Role: {u.role}</p>

                <button
                  onClick={() => handleEdit(u)}
                  style={btnBlue}
                >
                  Edit
                </button>

                <button
                  onClick={() => handleDelete(u.id)}
                  style={btnRed}
                >
                  Delete
                </button>
              </>
            )}
          </div>
        ))
      )}
    </AdminLayout>
  );
}

// ===== STYLE =====
const card = {
  background: "white",
  padding: "20px",
  marginTop: "15px",
  borderRadius: "10px",
};

const input = {
  padding: "8px",
  marginRight: "10px",
  borderRadius: "6px",
  border: "1px solid #ccc",
};

const btnBlue = {
  background: "#3b82f6",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
  marginRight: "10px",
};

const btnRed = {
  background: "#ef4444",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};

const btnGray = {
  background: "#9ca3af",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};