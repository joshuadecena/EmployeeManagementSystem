 const searchForm = document.getElementById("searchForm");
 const resultsBody = document.getElementById("resultsBody");
 let currentEmployees = [];
 let currentSortColumn = "id";
 let currentSortOrder = "asc";

 async function fetchEmployees(queryParams = "") {
   try {
     const response = await fetch(`/api/employees/search${queryParams}`);
     if (!response.ok) throw new Error("Failed to fetch data");

     const data = await response.json();
     currentEmployees = data || [];
     if(currentSortColumn) {
       sortAndRender(currentSortColumn, currentSortOrder);
     } else {
       renderTable(currentEmployees);
     }
   } catch (error) {
     console.error("Error:", error);
     resultsBody.innerHTML = `<tr><td colspan="5" class="text-danger text-center">Error loading results</td></tr>`;
   }
 }

 function renderTable(data) {
   resultsBody.innerHTML = "";
   if (!data.length) {
     resultsBody.innerHTML = `<tr><td colspan="5" class="text-center">No matching employees found.</td></tr>`;
     return;
   }
   data.forEach(emp => {
     const deptName = emp.department?.name || emp.department || "N/A";
     const age = emp.age ?? "N/A";
     const salary = emp.salary !== undefined 
	? new Intl.NumberFormat('en-PH', { style: 'currency', currency: 'PHP' }).format(emp.salary)
	: "N/A";

     const row = `
       <tr>
         <td>${emp.id}</td>
         <td>${emp.name}</td>
         <td>${deptName}</td>
         <td>${age}</td>
         <td>${salary}</td>
       </tr>
     `;
     resultsBody.innerHTML += row;
   });
 }

 function compareValues(key, order = 'asc') {
   return function(a, b) {
     let valA = a[key];
     let valB = b[key];

     if (key === "department") {
       valA = valA?.name || valA || "";
       valB = valB?.name || valB || "";
     }

     if (valA === undefined || valA === null) valA = "";
     if (valB === undefined || valB === null) valB = "";

     if (key === "id" || key === "age" || key === "salary") {
       valA = Number(valA);
       valB = Number(valB);
       if (isNaN(valA)) valA = order === 'asc' ? Infinity : -Infinity;
       if (isNaN(valB)) valB = order === 'asc' ? Infinity : -Infinity;
     }

     if (valA < valB) return order === 'asc' ? -1 : 1;
     if (valA > valB) return order === 'asc' ? 1 : -1;
     return 0;
   };
 }

 function updateSortIcons(activeColumn, order) {
   const buttons = document.querySelectorAll(".sort-button");
   buttons.forEach(btn => {
     if (btn.dataset.column === activeColumn) {
       btn.classList.add("active");
       btn.innerHTML = order === "asc" ? "&#9650;" : "&#9660;";
       btn.dataset.order = order;
       btn.title = `Sort ${order === "asc" ? "descending" : "ascending"}`;
     } else {
       btn.classList.remove("active");
       btn.innerHTML = "&#9650;";
       btn.dataset.order = "asc";
       btn.title = "Sort ascending";
     }
   });
 }

 function sortAndRender(column, order) {
   currentEmployees.sort(compareValues(column, order));
   renderTable(currentEmployees);
   updateSortIcons(column, order);
   currentSortColumn = column;
   currentSortOrder = order;
 }

 // Sort buttons event listeners
 document.querySelectorAll(".sort-button").forEach(btn => {
   btn.addEventListener("click", () => {
     const column = btn.dataset.column;
     let order = btn.dataset.order;

     // Toggle order
     order = order === "asc" ? "desc" : "asc";

     sortAndRender(column, order);
   });
 });

 searchForm.addEventListener("submit", (e) => {
   e.preventDefault();

   const id = document.getElementById("searchId").value.trim();
   const name = document.getElementById("searchName").value.trim();
   const department = document.getElementById("searchDepartment").value.trim();
   const minAge = document.getElementById("searchMinAge").value.trim();

   const params = new URLSearchParams();
   if (id) params.append("id", id);
   if (name) params.append("name", name);
   if (department) params.append("department", department);
   if (minAge) params.append("minAge", minAge);

   const query = params.toString() ? `?${params.toString()}` : "";
   fetchEmployees(query);
 });

 document.getElementById("resetButton").addEventListener("click", () => {
   // Clear filters
   document.getElementById("searchId").value = "";
   document.getElementById("searchName").value = "";
   document.getElementById("searchDepartment").value = "";
   document.getElementById("searchMinAge").value = "";

   // Reset sort state to default
   currentSortColumn = "id";
   currentSortOrder = "asc";
   updateSortIcons(currentSortColumn, currentSortOrder);

   // Fetch and display all employees
   fetchEmployees();
 });

 // On page load, show the full list of employees
 fetchEmployees();
 updateSortIcons(currentSortColumn, currentSortOrder);
 
<script src="search.html"></script>