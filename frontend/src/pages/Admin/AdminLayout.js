import { useNavigate, useLocation } from "react-router-dom";

export default function AdminLayout({ children }) {
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const menu = [
    { name: "📊 Dashboard", path: "/admin/dashboard" }, // 🔥 thêm
    { name: "📄 Jobs", path: "/admin/jobs" },
    { name: "🏢 Companies", path: "/admin/companies" },
    { name: "👨‍💼 Users", path: "/admin/users" },
    { name: "📂 Categories", path: "/admin/categories" },
    { name: "🔔 Notifications", path: "/admin/notifications" },
  ];

  return (
    <div style={{ display: "flex" }}>

      {/* Sidebar */}
      <div style={sidebar}>
        <div>
          <h2 style={{ marginBottom: "20px" }}>Admin</h2>

          {menu.map((item) => (
            <div
              key={item.path}
              onClick={() => navigate(item.path)}
              style={{
                ...link,
                ...(location.pathname === item.path ? active : {})
              }}
            >
              {item.name}
            </div>
          ))}
        </div>

        {/* Logout */}
        <button onClick={handleLogout} style={logoutBtn}>
          Logout
        </button>
      </div>

      {/* Content */}
      <div style={{ flex: 1, padding: "30px" }}>
        {children}
      </div>
    </div>
  );
}

// ===== STYLE =====
const sidebar = {
  width: "220px",
  background: "linear-gradient(180deg, #1e293b, #0f172a)", // 🔥 đẹp hơn
  color: "white",
  height: "100vh",
  padding: "20px",
  display: "flex",
  flexDirection: "column",
  justifyContent: "space-between",
};

const link = {
  margin: "8px 0",
  cursor: "pointer",
  opacity: 0.8,
  padding: "10px",
  borderRadius: "8px",
  transition: "0.2s",
};

const active = {
  background: "#334155",
  opacity: 1,
  fontWeight: "bold",
};

const logoutBtn = {
  background: "#ef4444",
  color: "white",
  border: "none",
  padding: "10px",
  borderRadius: "8px",
  cursor: "pointer",
};