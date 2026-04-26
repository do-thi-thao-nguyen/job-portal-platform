import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

function CreateCompany() {
  const navigate = useNavigate();

  const [company, setCompany] = useState({
    name: "",
    description: "",
    address: ""
  });

  const handleChange = (e) => {
    setCompany({
      ...company,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      await axios.post("http://localhost:8080/company", company, {
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`
        }
      });

      alert("Tạo công ty thành công!");
      navigate("/employer/create"); // quay lại tạo job

    } catch (err) {
      console.error(err);
      alert("Tạo công ty thất bại");
    }
  };

  return (
    <div style={{ padding: "40px" }}>
      <h2>Tạo công ty</h2>

      <form onSubmit={handleSubmit}>
        <input
          type="text"
          name="name"
          placeholder="Tên công ty"
          value={company.name}
          onChange={handleChange}
        />
        <br /><br />

        <textarea
          name="description"
          placeholder="Mô tả công ty"
          value={company.description}
          onChange={handleChange}
        />
        <br /><br />

        <input
          type="text"
          name="address"
          placeholder="Địa chỉ"
          value={company.address}
          onChange={handleChange}
        />
        <br /><br />

        <button type="submit">Tạo công ty</button>
      </form>
    </div>
  );
}

export default CreateCompany;