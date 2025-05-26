const searchForm = document.getElementById('searchForm');
const resultsBody = document.getElementById('resultsBody');

// Helper: fetch and display employees matching filters
async function fetchEmployees(filters) {
  try {
    const params = new URLSearchParams(filters);
    const res = await fetch(`/api/employees/search?${params.toString()}`, { credentials: "include" });
    if (!res.ok) throw new Error('Failed to fetch employees');
    const data = await res.json();

    // Clear old rows
    resultsBody.innerHTML = '';

    if (!data.length) {
      resultsBody.innerHTML = `<tr><td colspan="6" class="text-center">No employees found</td></tr>`;
      return;
    }

    // Populate rows
    data.forEach(emp => {
      const tr = document.createElement('tr');
      tr.innerHTML = `
        <td>${emp.id}</td>
        <td>${emp.name}</td>
        <td>${emp.department?.name || emp.department || 'N/A'}</td>
        <td>${emp.age || 'N/A'}</td>
		<td>${emp.salary ? `₱${Number(emp.salary).toLocaleString("en-PH", { minimumFractionDigits: 2, maximumFractionDigits: 2 })}` : ""}</td>
        <td>
          <button class="btn btn-danger btn-sm remove-btn" data-id="${emp.id}">Remove</button>
        </td>
      `;
      resultsBody.appendChild(tr);
    });

    // Attach remove event listeners
    document.querySelectorAll('.remove-btn').forEach(btn => {
      btn.addEventListener('click', handleRemoveClick);
    });
  } catch (err) {
    alert(err.message);
  }
}

// Remove button handler
function handleRemoveClick(event) {
  const empId = event.target.getAttribute('data-id');
  if (!empId) return;

  if (confirm(`Are you sure you want to remove employee ID ${empId}? This action cannot be undone.`)) {
    // Call API to delete
    fetch(`/api/employees/${empId}`, {
      method: 'DELETE',
      credentials: 'include',
    })
    .then(res => {
      if (!res.ok) throw new Error('Failed to remove employee');
      alert('Employee removed successfully.');
      // Refresh search results by re-submitting current filters
      searchForm.dispatchEvent(new Event('submit'));
    })
    .catch(err => alert(err.message));
  }
}

// Search form submit handler
searchForm.addEventListener('submit', (e) => {
  e.preventDefault();

  const filters = {
    id: document.getElementById('searchId').value.trim(),
    name: document.getElementById('searchName').value.trim(),
    department: document.getElementById('searchDepartment').value,
    minAge: document.getElementById('searchMinAge').value.trim(),
  };

  // Remove empty filters to keep query clean
  for (const key in filters) {
    if (!filters[key]) delete filters[key];
  }

  fetchEmployees(filters);
});