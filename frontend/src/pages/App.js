import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import JobList from "./pages/JobList";
import JobDetail from "./pages/JobDetail";
import Company from "./pages/Company";
import Applicants from "./pages/Applicants";
import CreateJob from "./pages/Employer/CreateJob";
import CreateCompany from "./pages/Employer/CreateCompany";
import MyJobs from "./pages/Employer/MyJobs";
import EditCompany from "./pages/Employer/EditCompany";
import ApplicationsPage from "./pages/Employer/ApplicationsPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/jobs" element={<JobList />} />
        <Route path="/employer/jobs" element={<MyJobs />} />
        <Route path="/employer/jobs/:jobId/applications" element={<ApplicationsPage />} />
        <Route path="/job" element={<JobDetail />} />
        <Route path="/company" element={<Company />} />
        <Route path="/employer/company/create" element={<CreateCompany />} />
        <Route path="/applicants" element={<Applicants />} />
        <Route path="/employer/create" element={<CreateJob />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;