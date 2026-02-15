import { useState } from "react";
import "../styles/createResume.css";

function CreateResume() {
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    phone: "",
    dob: "",
    city: "",
    linkedin: "",
    github: "",
    careerObjective: "",
    education: "",
    technicalSkills: "",
    projects: "",
    internships: "",
    certifications: "",
    softSkills: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const generateResume = async () => {
    try {
      const response = await fetch("http://localhost:8080/api/resume/generate", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(form),
      });

      if (!response.ok) throw new Error("Failed to generate resume");

      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement("a");
      a.href = url;
      a.download = "resume.pdf";
      a.click();
    } catch (error) {
      console.error(error);
      alert("Something went wrong.");
    }
  };

  return (
    <div className="resume-wrapper">
      <div className="resume-card">
        <h2>Create Your Professional Resume</h2>

        <div className="form-grid">
          <input name="fullName" placeholder="Full Name" onChange={handleChange} />
          <input name="email" placeholder="Email" onChange={handleChange} />
          <input name="phone" placeholder="Phone" onChange={handleChange} />
          <input name="dob" type="date" onChange={handleChange} />
          <input name="city" placeholder="City / State" onChange={handleChange} />
          <input name="linkedin" placeholder="LinkedIn URL" onChange={handleChange} />
          <input name="github" placeholder="GitHub URL" onChange={handleChange} />

          <textarea name="careerObjective" placeholder="Career Objective" onChange={handleChange} />
          <textarea name="education" placeholder="Education Details" onChange={handleChange} />
          <textarea name="technicalSkills" placeholder="Technical Skills" onChange={handleChange} />
          <textarea name="projects" placeholder="Projects" onChange={handleChange} />
          <textarea name="internships" placeholder="Internships" onChange={handleChange} />
          <textarea name="certifications" placeholder="Certifications" onChange={handleChange} />
          <textarea name="softSkills" placeholder="Soft Skills" onChange={handleChange} />
        </div>

        <button className="generate-btn" onClick={generateResume}>
          Generate Resume PDF
        </button>
      </div>
    </div>
  );
}

export default CreateResume;
