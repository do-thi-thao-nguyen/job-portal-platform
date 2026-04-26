import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function MyCompany() {

  const navigate = useNavigate();

  const [company, setCompany] = useState(null);
  const [loading, setLoading] = useState(true);

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

  // ⏳ Loading
  if (loading) {
    return (
      <EmployerLayout>
        <p>Loading...</p>
      </EmployerLayout>
    );
  }

  // ❌ Chưa có company
  if (!company || !company.id) {
    return (
      <EmployerLayout>
        <h2>Bạn chưa tạo công ty</h2>
        <button onClick={() => navigate("/employer/company/create")}>
          ➕ Tạo công ty
        </button>
      </EmployerLayout>
    );
  }

  // ✅ Có company
  return (
    <EmployerLayout>
      <h1>🏢 My Company</h1>

      <div className="company-card">

        <h2>{company.name}</h2>

        <p>
          <b>Mô tả:</b> {company.description || "Chưa có"}
        </p>

        <p>
          <b>Địa chỉ:</b> {company.address || "Chưa có"}
        </p>

        <p>
          <b>Trạng thái:</b>{" "}
          <span style={{
            color: company.status === "APPROVED" ? "green" : "orange",
            fontWeight: "bold"
          }}>
            {company.status || "PENDING"}
          </span>
        </p>
        <button onClick={() => navigate("/employer/company/edit")}>
             Edit
        </button>
      </div>
    </EmployerLayout>
  );
}   