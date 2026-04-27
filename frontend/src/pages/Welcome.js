import { Link } from "react-router-dom";

function Welcome() {
  return (
    <div style={{
      height: "100vh",
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      flexDirection: "column",
      background: "linear-gradient(to right, #2c3e50, #e84393)",
      color: "white"
    }}>
      
      <h1>Welcome to Job Portal 🚀</h1>
      <p>Tìm việc hoặc đăng tuyển dễ dàng</p>

      <div style={{ marginTop: "20px" }}>
        <Link to="/login">
          <button style={{ marginRight: "10px", padding: "10px 20px" }}>
            Login
          </button>
        </Link>

        <Link to="/register">
          <button style={{ padding: "10px 20px" }}>
            Register
          </button>
        </Link>
      </div>
    </div>
  );
}

export default Welcome;