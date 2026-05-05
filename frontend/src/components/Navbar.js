import { useNavigate, useLocation } from "react-router-dom";
import { useState } from "react";

export default function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();

  const email = localStorage.getItem("email");
  const role = localStorage.getItem("role"); // ROLE_USER / ROLE_EMPLOYER

  const [open, setOpen] = useState(false);

  // ẨN NAVBAR Ở EMPLOYER + AUTH + HOME
  const hideNavbar =
    location.pathname === "/" ||
    location.pathname === "/login" ||
    location.pathname === "/register" ||
    location.pathname.startsWith("/employer");

  if (hideNavbar) return null;

  const handleLogout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const isActive = (path) => location.pathname === path;

  const menuStyle = (path) => ({
    color: isActive(path) ? "#fff" : "#cbd5e1",
    fontWeight: isActive(path) ? "bold" : "normal",
    cursor: "pointer"
  });

  // ROLE CHECK CHUẨN
  const isUser = role === "ROLE_USER";
  const isEmployer = role === "ROLE_EMPLOYER";

  return (
    <div style={{
      position: "sticky",
      top: 0,
      zIndex: 1000,
      background: "#0f172a",
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      padding: "10px 20px",
      boxShadow: "0 2px 8px rgba(0,0,0,0.3)"
    }}>

      {/* LEFT */}
      <div style={{ display: "flex", alignItems: "center", gap: "20px" }}>

        {/* LOGO */}
        <h2
          style={{ color: "#0ea5e9", cursor: "pointer", margin: 0 }}
          onClick={() => navigate("/")}
        >
          JobPortal
        </h2>

        {/* SEARCH */}
        <input
          placeholder="🔍 Tìm job..."
          style={{
            padding: "6px 10px",
            borderRadius: "6px",
            border: "none",
            outline: "none",
            width: "200px"
          }}
        />

        {/* USER MENU */}
        {isUser && (
          <>
            <span style={menuStyle("/jobs")} onClick={() => navigate("/jobs")}>
              Jobs
            </span>

            <span style={menuStyle("/saved-jobs")} onClick={() => navigate("/saved-jobs")}>
              Saved
            </span>

            <span style={menuStyle("/my-applications")} onClick={() => navigate("/my-applications")}>
              Applied
            </span>

            <span style={menuStyle("/dashboard")} onClick={() => navigate("/dashboard")}>
              Dashboard
            </span>
          </>
        )}

        {/* ❌ KHÔNG SHOW MENU EMPLOYER Ở NAVBAR NỮA */}
      </div>

      {/* RIGHT */}
      <div style={{ position: "relative" }}>

        {email ? (
          <div
            onClick={() => setOpen(!open)}
            style={{
              display: "flex",
              alignItems: "center",
              gap: "8px",
              cursor: "pointer",
              color: "#fff"
            }}
          >
            <div style={{
              width: "35px",
              height: "35px",
              borderRadius: "50%",
              background: "#0ea5e9",
              display: "flex",
              alignItems: "center",
              justifyContent: "center",
              fontWeight: "bold"
            }}>
              {email[0].toUpperCase()}
            </div>

            <span>{email}</span>
          </div>
        ) : (
          <div style={{ display: "flex", gap: "10px" }}>
            <button onClick={() => navigate("/login")}>Login</button>
            <button onClick={() => navigate("/register")}>Register</button>
          </div>
        )}

        {/* DROPDOWN */}
        {open && (
          <div style={{
            position: "absolute",
            right: 0,
            top: "45px",
            background: "#1e293b",
            padding: "10px",
            borderRadius: "8px",
            minWidth: "150px"
          }}>
            <p style={{ color: "#fff", margin: "5px 0" }}>{email}</p>

            <hr />

            {/* 🔥 DASHBOARD THEO ROLE */}
            {isUser && (
              <button
                onClick={() => navigate("/dashboard")}
                style={{ width: "100%", marginTop: "5px" }}
              >
                Dashboard
              </button>
            )}

            {isEmployer && (
              <button
                onClick={() => navigate("/employer")}
                style={{ width: "100%", marginTop: "5px" }}
              >
                Employer Panel
              </button>
            )}

            <button
              onClick={handleLogout}
              style={{
                width: "100%",
                marginTop: "5px",
                background: "#ef4444",
                color: "#fff"
              }}
            >
              Logout
            </button>
          </div>
        )}

      </div>
    </div>
  );
}