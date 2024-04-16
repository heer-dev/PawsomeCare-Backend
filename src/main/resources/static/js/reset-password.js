// JavaScript logic for handling password reset form submission
document.getElementById('reset-password-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent default form submission behavior

    const newPassword = document.getElementById('new-password').value;

    // Make API call to reset password
    fetch('/api/caretakers/reset-password', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            token: window.location.search.split('=')[1], // Extract token from URL query parameter
            newPassword: newPassword
        })
    })
        .then(response => {
            if (response.ok) {
                // Password reset successful, redirect to login page or display success message
                window.location.href = '/login.html'; // Redirect to login page
            } else {
                // Handle error response
                return response.json().then(data => {
                    throw new Error(data.message);
                });
            }
        })
        .catch(error => {
            // Display error message to the user
            alert('Password reset failed: ' + error.message);
        });
});
