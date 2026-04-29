import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../services/api";

export default function UserNotifications() {
  const [list, setList] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchNotifications();
  }, []);

  const fetchNotifications = async () => {
    try {
      const res = await API.get("/api/notifications");
      setList(res.data);
    } catch (err) {
      console.error(err);
      alert("Load notifications failed");
    }
  };

  // 🔥 FIX CHÍNH Ở ĐÂY
  const markRead = async (id) => {
    try {
      await API.put(`/api/notifications/${id}/read`);

      // ✅ update UI ngay lập tức
      setList((prev) =>
        prev.map((n) =>
          n.id === id ? { ...n, isRead: true } : n
        )
      );

      // ✅ update sidebar badge
      window.dispatchEvent(new Event("notificationUpdated"));

      // ✅ chuyển trang luôn (không cần delay)
      navigate("/employer");

    } catch (err) {
      console.error(err);
      alert("Mark read failed");
    }
  };

  return (
    <div style={container}>
      <h2 style={title}>🔔 Notifications</h2>

      {list.length === 0 ? (
        <p style={empty}>No notifications</p>
      ) : (
        list.map((n) => (
          <div
            key={n.id}
            style={{
              ...card,
              background: n.isRead ? "#f8fafc" : "#ffffff",
              borderLeft: n.isRead
                ? "5px solid transparent"
                : "5px solid #3b82f6",
            }}
            onMouseEnter={(e) => {
              e.currentTarget.style.transform = "scale(1.02)";
            }}
            onMouseLeave={(e) => {
              e.currentTarget.style.transform = "scale(1)";
            }}
          >
            <div style={headerRow}>
              <h4 style={cardTitle}>{n.title}</h4>
              {!n.isRead && <span style={dot}></span>}
            </div>

            <p style={content}>{n.content}</p>

            <div style={footer}>
              <small style={date}>
                {formatDate(n.createdAt)}
              </small>

              {!n.isRead && (
                <button
                  onClick={() => markRead(n.id)}
                  style={btn}
                >
                  Mark as read
                </button>
              )}
            </div>
          </div>
        ))
      )}
    </div>
  );
}

// ===== FORMAT DATE =====
function formatDate(date) {
  return new Date(date).toLocaleString();
}

// ===== STYLE =====

const container = {
  maxWidth: "700px",
  margin: "30px auto",
};

const title = {
  marginBottom: "20px",
  fontSize: "24px",
};

const empty = {
  textAlign: "center",
  marginTop: "30px",
  color: "#94a3b8",
};

const card = {
  padding: "16px",
  marginBottom: "15px",
  borderRadius: "12px",
  boxShadow: "0 4px 12px rgba(0,0,0,0.08)",
  transition: "0.2s",
};

const headerRow = {
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
};

const cardTitle = {
  margin: 0,
};

const dot = {
  width: "10px",
  height: "10px",
  background: "#3b82f6",
  borderRadius: "50%",
};

const content = {
  margin: "10px 0",
  color: "#475569",
};

const footer = {
  display: "flex",
  justifyContent: "space-between",
  alignItems: "center",
};

const date = {
  color: "#94a3b8",
};

const btn = {
  padding: "6px 12px",
  background: "#3b82f6",
  color: "white",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer",
};