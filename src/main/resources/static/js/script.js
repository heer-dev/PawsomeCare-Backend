// This function is called once the DOM content is fully loaded.
document.addEventListener('DOMContentLoaded', function() {
    setupEndpointLinks();
});

// This function sets up endpoint links with appropriate event handlers.
function setupEndpointLinks() {
    const endpoints = [
        { name: 'Appointments', method: 'GET', path: '/api/appointments' },
        { name: 'Caretaker Register', method: 'POST', path: '/api/caretakers/register' },
        { name: 'Dog Owner Register', method: 'POST', path: '/api/dogowners/register' },
        { name: 'Support Inquiry', method: 'GET', path: '/api/support/{id}' }  // Assumes dynamic ID input is required
    ];

    const list = document.getElementById('endpoint-list');  // Assuming there's an <ul> or <ol> element with id="endpoint-list"
    endpoints.forEach(endpoint => {
        let listItem = document.createElement('li');
        let link = document.createElement('a');
        link.textContent = endpoint.name;
        link.href = '#';
        link.onclick = function() {
            let actualPath = endpoint.path;
            if (actualPath.includes("{id}")) {
                let id = prompt("Enter ID:");  // Prompt user to enter an ID for dynamic paths
                actualPath = actualPath.replace("{id}", id);
            }
            fetchAndDisplayData(actualPath, endpoint.method);
            return false;
        };
        listItem.appendChild(link);
        list.appendChild(listItem);
    });
}

// This function performs the fetch operation and handles the response.
function fetchAndDisplayData(path, method, body = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json'
        }
    };

    if (body) {
        options.body = JSON.stringify(body);  // Ensure the body is properly formatted as a JSON string
    }

    fetch(path, options)
        .then(response => {
            if (!response.ok) throw new Error(`HTTP status ${response.status}`);
            return response.json();
        })
        .then(data => displayData(data))
        .catch(error => {
            console.error('Error fetching data:', error);
            displayError(`Failed to load data: ${error.message}`);
        });
}

// This function displays the fetched data in a preformatted block.
function displayData(data) {
    const displayDiv = document.getElementById('data-display');  // Assumes there's a div with id="data-display"
    displayDiv.innerHTML = '';  // Clear previous content
    const pre = document.createElement('pre');
    pre.textContent = JSON.stringify(data, null, 2);  // Pretty print JSON data
    displayDiv.appendChild(pre);
}

// This function displays error messages.
function displayError(message) {
    const displayDiv = document.getElementById('data-display');
    displayDiv.innerHTML = `<p>${message}</p>`;  // Update the display with the error message
}
