let employeesData = [];
let currentSortColumn = "id";
let currentSortOrder = "asc";
let currentPage = 1;
const recordsPerPage = 5;

document.addEventListener("DOMContentLoaded", () => {
  fetchDashboard();

  document.querySelectorAll(".sort-button").forEach(btn => {
    btn.addEventListener("click", () => {
      const column = btn.dataset.column;
      let order = btn.dataset.order === "asc" ? "desc" : "asc";
      currentSortColumn = column;
      currentSortOrder = order;
      currentPage = 1; // Reset page on new sort
      sortAndDisplay(column, order);
    });
  });
});

async function fetchDashboard() {
  try {
    const opts = { credentials: "include" };

    const [empRes, salaryRes, ageRes] = await Promise.all([
      fetch("/api/employees", opts),
      fetch("/api/employees/average-salary", opts),
      fetch("/api/employees/average-age", opts)
    ]);

    if (empRes.status === 401) {
      window.location.href = "/login";
      return;
    }

    employeesData = await empRes.json();

    const avgSalary = parseFloat(await salaryRes.text());
    const avgAge = Math.floor(parseFloat(await ageRes.text()));

    document.getElementById("avgSalary").textContent = isNaN(avgSalary)
      ? "N/A"
      : `₱${avgSalary.toLocaleString("en-PH", {
          minimumFractionDigits: 2,
          maximumFractionDigits: 2
        })}`;
    document.getElementById("avgAge").textContent = isNaN(avgAge) ? "N/A" : avgAge;

    currentPage = 1; // Reset page after fetching
    sortAndDisplay(currentSortColumn, currentSortOrder);
  } catch (err) {
    alert("Failed to load dashboard: " + err.message);
  }
}

function sortAndDisplay(column, order) {
  employeesData.sort((a, b) => {
    let valA, valB;

    switch (column) {
      case "id":
        valA = Number(a.id);
        valB = Number(b.id);
        break;
      case "name":
        valA = a.name?.toLowerCase() || "";
        valB = b.name?.toLowerCase() || "";
        return order === "asc" ? valA.localeCompare(valB) : valB.localeCompare(valA);
      case "department":
        valA = a.department?.name?.toLowerCase() || a.department?.toLowerCase() || "";
        valB = b.department?.name?.toLowerCase() || b.department?.toLowerCase() || "";
        return order === "asc" ? valA.localeCompare(valB) : valB.localeCompare(valA);
      case "dob":
        valA = new Date(a.dateOfBirth || a.dob || "1970-01-01");
        valB = new Date(b.dateOfBirth || b.dob || "1970-01-01");
        break;
      case "salary":
        valA = Number(a.salary) || 0;
        valB = Number(b.salary) || 0;
        break;
      default:
        valA = "";
        valB = "";
    }

    return order === "asc" ? valA - valB : valB - valA;
  });

  displayEmployees();
  updateSortIcons(column, order);
}

function displayEmployees() {
  const tbody = document.querySelector("#empTable tbody");
  tbody.innerHTML = "";

  if (!employeesData || employeesData.length === 0) {
    tbody.innerHTML = `<tr><td colspan="5" class="text-center">No employees found</td></tr>`;
    clearPaginationControls();
    return;
  }

  const start = (currentPage - 1) * recordsPerPage;
  const end = start + recordsPerPage;
  const pageData = employeesData.slice(start, end);

  pageData.forEach(emp => {
    const row = document.createElement("tr");
    row.innerHTML = `
      <td>${emp.id}</td>
      <td>${emp.name || ""}</td>
      <td>${emp.department?.name || emp.department || ""}</td>
      <td>${formatDate(emp.dateOfBirth || emp.dob)}</td>
      <td>${emp.salary ? `₱${Number(emp.salary).toLocaleString("en-PH", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}` : ""}</td>
    `;
    tbody.appendChild(row);
  });

  renderPaginationControls();
}

function formatDate(dateString) {
  if (!dateString) return "";
  const options = { year: "numeric", month: "short", day: "numeric" };
  const date = new Date(dateString);
  return date.toLocaleDateString("en-PH", options);
}

function updateSortIcons(activeColumn, order) {
  document.querySelectorAll(".sort-button").forEach(btn => {
    if (btn.dataset.column === activeColumn) {
      btn.classList.add("active");
      btn.innerHTML = order === "asc" ? "&#9650;" : "&#9660;";
      btn.dataset.order = order;
    } else {
      btn.classList.remove("active");
      btn.innerHTML = "&#9650;";
      btn.dataset.order = "asc";
    }
  });
}

function renderPaginationControls() {
  const containerId = "paginationControls";
  let container = document.getElementById(containerId);

  if (!container) {
    container = document.createElement("div");
    container.id = containerId;
    container.className = "d-flex justify-content-center my-3";
    document.querySelector("#empTable").after(container);
  }

  const totalPages = Math.ceil(employeesData.length / recordsPerPage);
  if (totalPages <= 1) {
    container.innerHTML = "";  // No pagination needed
    return;
  }

  let html = "";

  html += `<button class="btn btn-outline-primary mx-1" ${currentPage === 1 ? "disabled" : ""} onclick="changePage(${currentPage - 1})">Prev</button>`;

  let startPage = Math.max(1, currentPage - 2);
  let endPage = Math.min(totalPages, startPage + 4);
  if (endPage - startPage < 4) {
    startPage = Math.max(1, endPage - 4);
  }

  for (let i = startPage; i <= endPage; i++) {
    html += `<button class="btn mx-1 ${i === currentPage ? "btn-primary" : "btn-outline-primary"}" onclick="changePage(${i})">${i}</button>`;
  }

  html += `<button class="btn btn-outline-primary mx-1" ${currentPage === totalPages ? "disabled" : ""} onclick="changePage(${currentPage + 1})">Next</button>`;

  container.innerHTML = html;
}

function clearPaginationControls() {
  const container = document.getElementById("paginationControls");
  if (container) container.innerHTML = "";
}

function changePage(page) {
  currentPage = page;
  displayEmployees();
}

//function logout() {
//  fetch("/logout", { method: "POST", credentials: "include" })
//    .then(() => window.location.href = "/login");
//}
