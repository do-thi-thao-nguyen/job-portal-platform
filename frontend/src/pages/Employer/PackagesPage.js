import EmployerLayout from "./EmployerLayout";
import API from "../../services/api";

export default function PackagesPage() {

  const handleBuy = async (packageId) => {
    try {
      console.log("🔥 BUY CLICK");

      const res = await API.get("/company/my");
      const company = res.data;

      if (!company || !company.id) {
        alert("Bạn chưa có công ty");
        return;
      }

      const companyId = company.id;

      console.log("🔥 CALL MOMO:", packageId, companyId);

      const payment = await API.post(
        `/momo/create?packageId=${packageId}&companyId=${companyId}`
      );

      window.location.href = payment.data.payUrl;

    } catch (err) {
      console.error("❌ BUY ERROR:", err.response || err);
      alert("Mua gói thất bại");
    }
  };

  return (
    <EmployerLayout>
      <div style={{ padding: "30px" }}>
        <h1 style={{ marginBottom: "30px" }}>💳 Buy Package</h1>

        <div style={{
          display: "flex",
          gap: "30px"
        }}>

          {/* BASIC */}
          <div style={cardStyle}>
            <div style={titleStyle}>Basic</div>
            <div style={priceStyle}>100.000đ</div>
            <p>Post 5 jobs</p>

            <button
              style={btnStyle}
              onClick={() => handleBuy(1)}
              onMouseOver={(e) => {
                e.target.style.transform = "scale(1.05)";
                e.target.style.boxShadow = "0 6px 18px rgba(99,102,241,0.6)";
              }}
              onMouseOut={(e) => {
                e.target.style.transform = "scale(1)";
                e.target.style.boxShadow = "0 4px 12px rgba(99,102,241,0.4)";
              }}
            >
              Buy Now
            </button>
          </div>

          {/* PREMIUM */}
          <div style={{
            ...cardStyle,
            border: "2px solid #6366f1"
          }}>
            <div style={titleStyle}>Premium 🔥</div>
            <div style={priceStyle}>300.000đ</div>
            <p>Post 20 jobs</p>

            <button
              style={btnStyle}
              onClick={() => handleBuy(2)}
              onMouseOver={(e) => {
                e.target.style.transform = "scale(1.05)";
                e.target.style.boxShadow = "0 6px 18px rgba(99,102,241,0.6)";
              }}
              onMouseOut={(e) => {
                e.target.style.transform = "scale(1)";
                e.target.style.boxShadow = "0 4px 12px rgba(99,102,241,0.4)";
              }}
            >
              Buy Now
            </button>
          </div>

        </div>
      </div>
    </EmployerLayout>
  );
}


// ===== STYLE =====

const cardStyle = {
  background: "white",
  padding: "25px",
  borderRadius: "15px",
  width: "250px",
  boxShadow: "0 10px 25px rgba(0,0,0,0.1)",
  transition: "0.2s"
};

const titleStyle = {
  fontSize: "20px",
  fontWeight: "600",
  marginBottom: "10px"
};

const priceStyle = {
  fontSize: "24px",
  fontWeight: "bold",
  color: "#6366f1",
  marginBottom: "10px"
};

const btnStyle = {
  marginTop: "15px",
  padding: "10px",
  width: "100%",
  background: "linear-gradient(135deg, #6366f1, #8b5cf6)",
  color: "white",
  border: "none",
  borderRadius: "8px",
  cursor: "pointer",
  fontWeight: "600",
  transition: "0.2s",
  boxShadow: "0 4px 12px rgba(99,102,241,0.4)"
};