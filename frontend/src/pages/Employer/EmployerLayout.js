import { useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import API from "../../services/api";

export default function EmployerLayout({ children }) {
  const navigate = useNavigate();
  const location = useLocation();

  const [count, setCount] = useState(0);

  // 🔥 LOAD BAN ĐẦU + LISTEN EVENT
  useEffect(() => {
    fetchNotifications();

    const handler = () => {
      // ✅ KHÔNG gọi API nữa → tránh bị nhảy lại số
      setCount((prev) => Math.max(prev - 1, 0));
    };

    window.addEventListener("notificationUpdated", handler);

    return () => {
      window.removeEventListener("notificationUpdated", handler);
    };
  }, []);

  // 🔥 LOAD BAN ĐẦU
  const fetchNotifications = async () => {
    try {
      const res = await API.get("/api/notifications");

      const unread = res.data.filter((n) => n.isRead === false);

      setCount(unread.length);
    } catch (e) {
      console.error("Load notification count failed", e);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem("token");
    window.location.href = "/";
  };

  // 🔥 COMPONENT MENU ITEM
  const MenuItem = ({ text, path, badge }) => {
    const isActive = location.pathname === path;

    return (
      <div
        onClick={() => navigate(path)}
        style={{
          ...item,
          background: isActive ? "#1e293b" : "transparent",
        }}
      >
        {text}

        {badge > 0 && <span style={badgeStyle}>{badge}</span>}
      </div>
    );
  };

  return (
    <div style={layout}>
      {/* SIDEBAR */}
      <div style={sidebar}>
        <div>
          <h2 style={logo}>🏢 Employer</h2>

          <div style={menu}>
            <MenuItem text="📊 Dashboard" path="/employer" />

            <MenuItem
              text="🔔 Notifications"
              path="/notifications"
              badge={count}
            />

            <MenuItem text="🏢 My Company" path="/employer/company" />
            <MenuItem text="📄 My Jobs" path="/employer/jobs" />
            <MenuItem text="➕ Create Job" path="/employer/create" />
            <MenuItem text="💳 Buy Package" path="/packages" />
          </div>
        </div>

        <button style={logout} onClick={handleLogout}>
          Logout
        </button>
      </div>

      {/* CONTENT */}
      <div style={content}>{children}</div>
    </div>
  );
}

//
// ===== STYLE =====
//

const layout = {
  display: "flex",
};

const sidebar = {
  width: "240px",
  height: "100vh",
  background: "linear-gradient(180deg, #0f172a, #1e293b)",
  color: "white",
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
  padding: "20px",
};

const logo = {
  fontSize: "20px",
  fontWeight: "bold",
  marginBottom: "20px",
};

const menu = {
  display: "flex",
  flexDirection: "column",
  gap: "8px",
};

const item = {
  padding: "10px 12px",
  borderRadius: "8px",
  cursor: "pointer",
  position: "relative",
  transition: "0.2s",
};

const badgeStyle = {
  position: "absolute",
  right: "10px",
  top: "50%",
  transform: "translateY(-50%)",
  background: "#ef4444",
  borderRadius: "50%",
  padding: "2px 7px",
  fontSize: "12px",
  fontWeight: "bold",
};

const logout = {
  background: "#ef4444",
  border: "none",
  padding: "10px",
  borderRadius: "8px",
  color: "white",
  cursor: "pointer",
};

const content = {
  flex: 1,
  padding: "20px",
  background: "#f1f5f9",
};