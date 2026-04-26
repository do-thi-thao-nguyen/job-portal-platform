import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function EditCompany() {

  const navigate = useNavigate();

  const [company, setCompany] = useState({
    name: "",
    description: "",
    address: ""
  });

  const [loading, setLoading] = useState(true);

  // 🔥 load company
  useEffect(() => {
    const fetchCompany = async () => {
      try {
        const res = await API.get("/company/my");
        setCompany(res.data);
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchCompany();
  }, []);

  const handleChange = (e) => {
    setCompany({
      ...company,
      [e.target.name]: e.target.value
    });
  };

  const handleSubmit = async () => {
    try {
      await API.put("/company/my", company);

      alert("Updated!");
      navigate("/employer/company");

    } catch (err) {
      console.error(err);
      alert("Update failed");
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <EmployerLayout>
      <h1>Edit Company</h1>

      <div className="form-card">

        <input
          name="name"
          value={company.name}
          onChange={handleChange}
          placeholder="Tên công ty"
        />

        <textarea
          name="description"
          value={company.description || ""}
          onChange={handleChange}
          placeholder="Mô tả"
        />

        <input
          name="address"
          value={company.address || ""}
          onChange={handleChange}
          placeholder="Địa chỉ"
        />

        <button onClick={handleSubmit}>
          Save
        </button>

      </div>
    </EmployerLayout>
  );
}