import { useEffect, useState } from "react";
import API from "../../services/api";
import AdminLayout from "./AdminLayout";

export default function AdminNotifications() {
  const [list, setList] = useState([]);

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    try {
      const res = await API.get("/api/admin/notifications");
      setList(res.data);
    } catch (err) {
      console.error(err);
      alert("Load notifications failed");
    }
  };

  const handleDelete = async (id) => {
    if (!window.confirm("Delete this notification?")) return;

    try {
      await API.delete(`/api/admin/notifications/${id}`);
      fetchNotifications();
    } catch {
      alert("Delete failed");
    }
  };

  return (
    <AdminLayout>
      <h1 style={{ color: "white" }}>🔔 Manage Notifications</h1>

      {list.length === 0 ? (
        <p style={{ color: "white" }}>No notifications</p>
      ) : (
        list.map((n) => (
          <div key={n.id} style={card}>
            <h3>{n.title}</h3>
            <p>{n.content}</p>

            <small>
              User: {n.user?.email || "N/A"}
            </small>
            <br />
            <small>{formatDate(n.createdAt)}</small>

            <button
              onClick={() => handleDelete(n.id)}
              style={btnRed}
            >
              Delete
            </button>
          </div>
        ))
      )}
    </AdminLayout>
  );
}

// ===== FORMAT DATE =====
function formatDate(date) {
  return new Date(date).toLocaleString();
}

// ===== STYLE =====
const card = {
  background: "white",
  padding: "15px",
  marginTop: "15px",
  borderRadius: "10px",
};

const btnRed = {
  background: "#ef4444",
  color: "white",
  padding: "6px 12px",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
  marginTop: "10px",
};