import { useState, useEffect } from "react";
import API from "../../services/api";
import { useNavigate } from "react-router-dom";
import EmployerLayout from "./EmployerLayout";

export default function CreateJob() {

  const navigate = useNavigate();

  const [job, setJob] = useState({
    title: "",
    description: "",
    location: "",
    salaryMin: "",
    salaryMax: "",
    category: { id: "" }
  });

  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);

  // 🔥 load category
  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      const res = await API.get("/categories");
      setCategories(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;

    if (name === "categoryId") {
      setJob({
        ...job,
        category: { id: value }
      });
    } else {
      setJob({
        ...job,
        [name]: value
      });
    }
  };

  // 🔥 VALIDATE
  const validate = () => {
    if (!job.title.trim()) {
      alert("Nhập tiêu đề job");
      return false;
    }

    if (!job.salaryMin || !job.salaryMax) {
      alert("Nhập đầy đủ lương");
      return false;
    }

    if (Number(job.salaryMin) > Number(job.salaryMax)) {
      alert("Lương min phải nhỏ hơn max");
      return false;
    }

    if (!job.category.id) {
      alert("Chọn category");
      return false;
    }

    return true;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validate()) return;

    try {
      setLoading(true);

      await API.post("/jobs", {
        ...job,
        salaryMin: Number(job.salaryMin),
        salaryMax: Number(job.salaryMax)
      });

      alert("Tạo job thành công!");
      navigate("/employer/jobs");

    } catch (err) {
      console.error(err);
      alert(err.response?.data || "Tạo job thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <EmployerLayout>
      <div style={{ padding: "40px" }}>

        <h1 style={{ marginBottom: "20px" }}>➕ Create Job</h1>

        <div style={cardStyle}>
          <form onSubmit={handleSubmit}>

            <input
              name="title"
              placeholder="Job title"
              value={job.title}
              onChange={handleChange}
              style={inputStyle}
            />

            <textarea
              name="description"
              placeholder="Description"
              value={job.description}
              onChange={handleChange}
              style={{ ...inputStyle, height: "80px" }}
            />

            <input
              name="location"
              placeholder="Location"
              value={job.location}
              onChange={handleChange}
              style={inputStyle}
            />

            <input
              type="number"
              name="salaryMin"
              placeholder="Salary Min"
              value={job.salaryMin}
              onChange={handleChange}
              style={inputStyle}
            />

            <input
              type="number"
              name="salaryMax"
              placeholder="Salary Max"
              value={job.salaryMax}
              onChange={handleChange}
              style={inputStyle}
            />

            <select
              name="categoryId"
              value={job.category.id}
              onChange={handleChange}
              style={inputStyle}
            >
              <option value="">Select category</option>
              {categories.map(c => (
                <option key={c.id} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>

            <button
              type="submit"
              disabled={loading}
              style={{
                width: "100%",
                padding: "12px",
                background: loading ? "#aaa" : "#10b981",
                color: "white",
                border: "none",
                borderRadius: "8px",
                cursor: "pointer",
                fontSize: "15px"
              }}
            >
              {loading ? "Đang tạo..." : "Create Job"}
            </button>

          </form>
        </div>

      </div>
    </EmployerLayout>
  );
}

// 🎨 STYLE
const cardStyle = {
  background: "white",
  padding: "25px",
  borderRadius: "12px",
  width: "420px",
  boxShadow: "0 10px 25px rgba(0,0,0,0.1)"
};

const inputStyle = {
  width: "100%",
  padding: "10px",
  marginBottom: "12px",
  borderRadius: "8px",
  border: "1px solid #ddd",
  fontSize: "14px",
  outline: "none"
};