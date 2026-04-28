import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function CreateJob() {

  const navigate = useNavigate();

  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [salary, setSalary] = useState("");
  const [categoryId, setCategoryId] = useState("");

  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(false);

  // 🔥 CHECK TOKEN NGAY KHI LOAD
  useEffect(() => {
    const token = localStorage.getItem("token");
    console.log("🔥 TOKEN:", token);

    if (token) {
      try {
        const payload = JSON.parse(atob(token.split(".")[1]));
        console.log("🔥 ROLE:", payload.role);
      } catch (e) {
        console.log("❌ Token decode lỗi");
      }
    }
  }, []);

  // 🔥 CHECK COMPANY
  useEffect(() => {
    const checkCompany = async () => {
      try {
        console.log("🚀 CALL /company/my");

        const res = await API.get("/company/my");

        console.log("✅ Company:", res.data);

        if (!res.data || !res.data.id) {
          alert("Bạn cần tạo công ty trước!");
          navigate("/employer/company/create");
        }

      } catch (err) {
        console.error("❌ COMPANY ERROR:", err.response || err);
        alert("Lỗi xác thực công ty");
      }
    };

    checkCompany();
  }, [navigate]);

  // 🔥 LOAD CATEGORY
  useEffect(() => {
    fetchCategories();
  }, []);

  const fetchCategories = async () => {
    try {
      console.log("🚀 CALL /categories");

      const res = await API.get("/categories");

      console.log("✅ Categories:", res.data);

      setCategories(res.data);
    } catch (err) {
      console.error("❌ CATEGORY ERROR:", err);
      alert("Failed to load categories");
    }
  };

  // 🔥 SUBMIT JOB
  const handleSubmit = async () => {

    if (!title || !description || !salary || !categoryId) {
      alert("Please fill all fields");
      return;
    }

    if (isNaN(salary)) {
      alert("Salary must be a number");
      return;
    }

    try {
      setLoading(true);

      const payload = {
        title,
        description,
        salary: Number(salary),
        category: {
          id: Number(categoryId)
        }
      };

      console.log("🚀 POST /jobs");
      console.log("📦 Payload:", payload);

      await API.post("/jobs", payload);

      console.log("✅ CREATE SUCCESS");

      alert("✅ Job created!");
      navigate("/employer/jobs");

    } catch (err) {
      console.error("❌ CREATE JOB ERROR:", err.response || err);

      if (err.response) {
        console.log("🔥 STATUS:", err.response.status);
        console.log("🔥 DATA:", err.response.data);
      }

      alert("❌ Create failed");
    } finally {
      setLoading(false);
    }
  };

  return (
    <EmployerLayout>
      <h1>➕ Create Job</h1>

      <div className="form-card">

        <input
          placeholder="Job title"
          value={title}
          onChange={e => setTitle(e.target.value)}
        />

        <textarea
          placeholder="Description"
          value={description}
          onChange={e => setDescription(e.target.value)}
        />

        <input
          type="number"
          placeholder="Salary"
          value={salary}
          onChange={e => setSalary(e.target.value)}
        />

        <select
          value={categoryId}
          onChange={e => setCategoryId(e.target.value)}
        >
          <option value="">Select category</option>
          {categories.map(c => (
            <option key={c.id} value={c.id}>
              {c.name}
            </option>
          ))}
        </select>

        <button onClick={handleSubmit} disabled={loading}>
          {loading ? "Creating..." : "Create Job"}
        </button>

      </div>
    </EmployerLayout>
  );
}