import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
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

  // 🔥 CHECK COMPANY
  useEffect(() => {
    const checkCompany = async () => {
      try {
        const res = await axios.get("http://localhost:8080/company/my", {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`
          }
        });

        console.log("Company:", res.data); // 👈 debug

        // 🔥 FIX CHUẨN 100%
        if (!res.data || !res.data.id) {
          alert("Bạn cần tạo công ty trước!");
          navigate("/employer/company/create");
        }

      } catch (err) {
        console.error(err);
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
      const res = await API.get("/categories");
      setCategories(res.data);
    } catch (err) {
      alert("Failed to load categories");
    }
  };

  // 🔥 SUBMIT JOB
  const handleSubmit = async () => {

    if (!title || !description || !salary || !categoryId) {
      alert("Please fill all fields");
      return;
    }

    try {
      setLoading(true);

      await API.post("/jobs", {
        title,
        description,
        salary: Number(salary),
        category: {
          id: Number(categoryId)
        }
      });

      alert("✅ Job created!");
      navigate("/employer/jobs");

    } catch (err) {
      console.error(err);
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