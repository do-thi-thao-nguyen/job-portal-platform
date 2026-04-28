import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import EmployerLayout from "./EmployerLayout";

function CreateCompany() {
  const navigate = useNavigate();

  const [company, setCompany] = useState({
    name: "",
    description: "",
    address: ""
  });

  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setCompany({
      ...company,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!company.name.trim()) {
      alert("Vui lòng nhập tên công ty");
      return;
    }

    try {
      setLoading(true);

      await axios.post("http://localhost:8080/company", company, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`
        }
      });

      alert("Tạo công ty thành công!");
      navigate("/employer/create");

    } catch (err) {
      console.error(err);
      alert("Tạo công ty thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <EmployerLayout>
    <div style={{ padding: "40px" }}>
      <div style={{
        background: "white",
        padding: "25px",
        borderRadius: "12px",
        width: "400px",
        boxShadow: "0 8px 20px rgba(0,0,0,0.1)"
      }}>

        <h2 style={{ marginBottom: "20px" }}>
          Tạo công ty
        </h2>

        <form onSubmit={handleSubmit}>

          <input
            type="text"
            name="name"
            placeholder="Tên công ty"
            value={company.name}
            onChange={handleChange}
            style={inputStyle}
          />

          <textarea
            name="description"
            placeholder="Mô tả công ty"
            value={company.description}
            onChange={handleChange}
            style={{ ...inputStyle, height: "80px" }}
          />

          <input
            type="text"
            name="address"
            placeholder="Địa chỉ"
            value={company.address}
            onChange={handleChange}
            style={inputStyle}
          />

          <button
            type="submit"
            disabled={loading}
            style={{
              marginTop: "10px",
              width: "100%",
              padding: "12px",
              background: loading ? "#aaa" : "#6366f1",
              color: "white",
              border: "none",
              borderRadius: "8px",
              cursor: "pointer",
              fontSize: "15px"
            }}
          >
            {loading ? "Đang tạo..." : "Tạo công ty"}
          </button>

        </form>
      </div>
    </div>
  </EmployerLayout>
  );
}

// style reuse
const inputStyle = {
  width: "100%",
  padding: "10px",
  marginBottom: "12px",
  borderRadius: "8px",
  border: "1px solid #ddd",
  fontSize: "14px",
  outline: "none"
};

export default CreateCompany;