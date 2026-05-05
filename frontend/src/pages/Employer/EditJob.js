import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import API from "../../services/api";
import EmployerLayout from "./EmployerLayout";

export default function EditJob() {

  const { id } = useParams();
  const navigate = useNavigate();

  const [job, setJob] = useState({
    title: "",
    description: "",
    location: "",
    salaryMin: "",
    salaryMax: ""
  });

  const [loading, setLoading] = useState(false);

  // load job detail
  useEffect(() => {
    const fetchJob = async () => {
      try {
        const res = await API.get(`/jobs/${id}`);
        setJob(res.data);
      } catch (err) {
        console.error(err);
        alert("Không load được job");
      }
    };

    fetchJob();
  }, [id]);

  // handle input
  const handleChange = (e) => {
    setJob({
      ...job,
      [e.target.name]: e.target.value
    });
  };

  // submit update
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!job.title.trim()) {
      alert("Vui lòng nhập title");
      return;
    }

    try {
      setLoading(true);

      await API.put(`/jobs/${id}`, job);

      alert("Cập nhật thành công");
      navigate("/employer/jobs");

    } catch (err) {
      console.error(err);
      alert("Cập nhật thất bại");
    } finally {
      setLoading(false);
    }
  };

  return (
    <EmployerLayout>
      <div style={{ padding: "30px" }}>

        <h2>Edit Job</h2>

        <form onSubmit={handleSubmit} style={cardStyle}>

          <input
            name="title"
            placeholder="Title"
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
            name="salaryMin"
            placeholder="Salary Min"
            value={job.salaryMin}
            onChange={handleChange}
            style={inputStyle}
          />

          <input
            name="salaryMax"
            placeholder="Salary Max"
            value={job.salaryMax}
            onChange={handleChange}
            style={inputStyle}
          />

          <button
            type="submit"
            disabled={loading}
            style={btnStyle}
          >
            {loading ? "Đang cập nhật..." : "Update Job"}
          </button>

        </form>
      </div>
    </EmployerLayout>
  );
}


// ===== STYLE =====

const cardStyle = {
  background: "white",
  padding: "20px",
  borderRadius: "10px",
  width: "400px",
  boxShadow: "0 5px 15px rgba(0,0,0,0.1)"
};

const inputStyle = {
  width: "100%",
  padding: "10px",
  marginBottom: "10px",
  borderRadius: "6px",
  border: "1px solid #ddd"
};

const btnStyle = {
  width: "100%",
  padding: "10px",
  background: "#10b981",
  color: "white",
  border: "none",
  borderRadius: "6px",
  cursor: "pointer"
};