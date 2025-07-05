document.addEventListener('DOMContentLoaded', function() {
    // DOM Elements
    const createUserForm = document.getElementById('createUserForm');
    const getUserForm = document.getElementById('getUserForm');
    const getAllUsersBtn = document.getElementById('getAllUsersBtn');
    const updateUserForm = document.getElementById('updateUserForm');
    const deleteUserForm = document.getElementById('deleteUserForm');

    const createResult = document.getElementById('createResult');
    const getUserResult = document.getElementById('getUserResult');
    const getAllUsersResult = document.getElementById('getAllUsersResult');
    const updateResult = document.getElementById('updateResult');
    const deleteResult = document.getElementById('deleteResult');

    // Create User
    createUserForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const username = document.getElementById('username').value;
        const sensitiveData = document.getElementById('sensitiveData').value;

        try {
            const response = await fetch('/api/users', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    username: username,
                    sensitiveData: sensitiveData
                })
            });

            const result = await response.json();

            if (response.ok) {
                createResult.innerHTML = `
                    <div class="success">
                        <p>User created successfully!</p>
                        <p>Username: ${result.username}</p>
                        <p>ID: ${result.id}</p>
                    </div>
                `;
                createUserForm.reset();
            } else {
                throw new Error(result.error || 'Failed to create user');
            }
        } catch (error) {
            createResult.innerHTML = `
                <div class="error">
                    <p>Error creating user:</p>
                    <p>${error.message}</p>
                </div>
            `;
        }
    });

    // Get User Data
    getUserForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const userId = document.getElementById('userId').value;

        try {
            const response = await fetch(`/api/users/${userId}`);
            const result = await response.json();

            if (response.ok) {
                getUserResult.innerHTML = `
                    <div class="success">
                        <p>User data retrieved:</p>
                        <pre>${JSON.stringify(result.data, null, 2)}</pre>
                    </div>
                `;
            } else {
                throw new Error(result.error || 'User not found');
            }
        } catch (error) {
            getUserResult.innerHTML = `
                <div class="error">
                    <p>Error retrieving user:</p>
                    <p>${error.message}</p>
                </div>
            `;
        }
    });

    // Get All Users
    getAllUsersBtn.addEventListener('click', async function() {
        try {
            const response = await fetch('/api/users');
            const users = await response.json();

            if (response.ok) {
                getAllUsersResult.innerHTML = `
                    <div class="success">
                        <h3>All Users (${users.length})</h3>
                        <div class="user-list">
                            ${users.map(user => `
                                <div class="user-card">
                                    <p><strong>ID:</strong> ${user.id}</p>
                                    <p><strong>Username:</strong> ${user.username}</p>
                                    <p class="encrypted-data">Encrypted data: ${user.encryptedData.substring(0, 20)}...</p>
                                </div>
                            `).join('')}
                        </div>
                    </div>
                `;
            } else {
                throw new Error('Failed to fetch users');
            }
        } catch (error) {
            getAllUsersResult.innerHTML = `
                <div class="error">
                    <p>Error fetching users:</p>
                    <p>${error.message}</p>
                </div>
            `;
        }
    });

    // Update User
    updateUserForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const updateUserId = document.getElementById('updateUserId').value;
        const newData = document.getElementById('newData').value;

        try {
            const response = await fetch(`/api/users/${updateUserId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    sensitiveData: newData
                })
            });

            const result = await response.json();

            if (response.ok) {
                updateResult.innerHTML = `
                    <div class="success">
                        <p>User updated successfully!</p>
                        <p>New encrypted data stored.</p>
                    </div>
                `;
                updateUserForm.reset();
            } else {
                throw new Error(result.error || 'Failed to update user');
            }
        } catch (error) {
            updateResult.innerHTML = `
                <div class="error">
                    <p>Error updating user:</p>
                    <p>${error.message}</p>
                </div>
            `;
        }
    });

    // Delete User
    deleteUserForm.addEventListener('submit', async function(e) {
        e.preventDefault();

        const deleteUserId = document.getElementById('deleteUserId').value;

        try {
            const response = await fetch(`/api/users/${deleteUserId}`, {
                method: 'DELETE'
            });

            const result = await response.json();

            if (response.ok) {
                deleteResult.innerHTML = `
                    <div class="success">
                        <p>${result.message || 'User deleted successfully'}</p>
                    </div>
                `;
                deleteUserForm.reset();
            } else {
                throw new Error(result.error || 'Failed to delete user');
            }
        } catch (error) {
            deleteResult.innerHTML = `
                <div class="error">
                    <p>Error deleting user:</p>
                    <p>${error.message}</p>
                </div>
            `;
        }
    });
});