import { useEffect, useState } from "react";
import API from "../../services/api";
import AdminLayout from "./AdminLayout";

export default function AdminCategories() {
  const [categories, setCategories] = useState([]);
  const [name, setName] = useState("");

  // 🔥 edit state
  const [editingId, setEditingId] = useState(null);
  const [editName, setEditName] = useState("");

  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    const res = await API.get("admin/categories");
    setCategories(res.data);
  };

  // ===== CREATE =====
  const handleCreate = async () => {
    if (!name.trim()) return;

    await API.post("admin/categories", { name });
    setName("");
    fetchCategories();
  };

  // ===== DELETE =====
  const handleDelete = async (id) => {
    if (!window.confirm("Delete this category?")) return;

    await API.delete(`admin/categories/${id}`);
    fetchCategories();
  };

  // ===== EDIT CLICK =====
  const handleEdit = (c) => {
    setEditingId(c.id);
    setEditName(c.name);
  };

  // ===== UPDATE =====
  const handleUpdate = async () => {
    await API.put(`admin/categories/${editingId}`, {
      name: editName,
    });

    setEditingId(null);
    setEditName("");
    fetchCategories();
  };

  return (
    <AdminLayout>
      <h1 style={{ color: "white" }}>📂 Categories</h1>

      {/* CREATE */}
      <div style={card}>
        <input
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="New category"
          style={input}
        />
        <button onClick={handleCreate} style={btnGreen}>
          Add
        </button>
      </div>

      {/* LIST */}
      {categories.map((c) => (
        <div key={c.id} style={card}>
          {editingId === c.id ? (
            <>
              <input
                value={editName}
                onChange={(e) => setEditName(e.target.value)}
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
              <span>{c.name}</span>

              <button
                onClick={() => handleEdit(c)}
                style={btnBlue}
              >
                Edit
              </button>

              <button
                onClick={() => handleDelete(c.id)}
                style={btnRed}
              >
                Delete
              </button>
            </>
          )}
        </div>
      ))}
    </AdminLayout>
  );
}

// ===== STYLE =====
const card = {
  background: "white",
  padding: "15px",
  marginTop: "15px",
  borderRadius: "10px",
};

const input = {
  padding: "8px",
  marginRight: "10px",
  borderRadius: "6px",
  border: "1px solid #ddd",
};

const btnGreen = {
  background: "#22c55e",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};

const btnBlue = {
  background: "#3b82f6",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
  marginLeft: "10px",
};

const btnRed = {
  background: "#ef4444",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
  marginLeft: "10px",
};

const btnGray = {
  background: "#9ca3af",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
  marginLeft: "10px",
};