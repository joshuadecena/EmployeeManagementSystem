  document.getElementById("empForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const newEmployee = {
      name: document.getElementById("empName").value,
      department: document.getElementById("empDepartment").value,
      dateOfBirth: document.getElementById("empDob").value,
      salary: document.getElementById("empSalary").value
    };

    try {
      const response = await fetch("/api/employees", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        credentials: "include",
        body: JSON.stringify(newEmployee)
      });

      if (response.ok) {
        alert("Employee added successfully.");
        document.getElementById("empForm").reset();
      } else {
        alert("Failed to add employee. Please check your input.");
      }
    } catch (error) {
      console.error("Error adding employee:", error);
      alert("An error occurred. Please try again.");
    }
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>a
  });
