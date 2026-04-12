import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import JobList from "./pages/JobList";
import JobDetail from "./pages/JobDetail";
import Company from "./pages/Company";
import Applicants from "./pages/Applicants";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/jobs" element={<JobList />} />
        <Route path="/job" element={<JobDetail />} />
        <Route path="/company" element={<Company />} />
        <Route path="/applicants" element={<Applicants />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;