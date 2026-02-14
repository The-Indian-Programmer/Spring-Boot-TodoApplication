
## TodoApplication API

A comprehensive RESTful API for task management with user authentication and full CRUD operations.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
- [Request & Response Examples](#request--response-examples)
- [Error Handling](#error-handling)
- [Best Practices](#best-practices)

## 🎯 Overview

The TodoApplication API is a RESTful service that provides a complete task management system. It allows users to register, authenticate, and manage their tasks with full CRUD (Create, Read, Update, Delete) capabilities.

## ✨ Features

### Authentication System
- 🔐 Secure user registration
- 🔑 JWT token-based authentication
- ✅ Token verification endpoint
- 🛡️ Protected routes requiring authentication

### Task Management
- ➕ Create new tasks with custom details
- 📋 List tasks with pagination support
- 🔍 Retrieve individual task details
- ✏️ Update tasks (full or partial updates)
- 🗑️ Delete tasks
- 🏷️ Support for task status, priority, and due dates

## 🚀 Getting Started

### Prerequisites

- Postman (for testing the API)
- Valid API base URL
- User account credentials

### Setup Instructions

1. **Import the Collection**
   - Open Postman
   - Import the TodoApplication collection
   - Navigate to your workspace

2. **Configure Base URL**
   - Create or select an environment
   - Add a variable named `BASE_URL`
   - Set the value to your API server URL (e.g., `http://localhost:3000/` or `https://api.yourdomain.com/`)
   - **Important**: Include the trailing slash

3. **Register a User**
   - Use the "Register User" endpoint
   - Provide email and password
   - Save your credentials

4. **Login and Get Token**
   - Use the "Login User" endpoint
   - Copy the authentication token from the response
   - Store it in an environment variable (e.g., `auth_token`)

5. **Start Managing Tasks**
   - Use the token in the Authorization header
   - Access all task management endpoints

## 🔐 Authentication

### Authentication Flow

```
Register → Login → Receive Token → Use Token in Headers → Access Protected Endpoints
```

### Using the Authentication Token

All protected endpoints require the authentication token in the Authorization header:

```
Authorization: Bearer <your-token-here>
```

### Setting Up in Postman

**Option 1: Manual**
- Copy token from login response
- Paste into Authorization tab of each request

**Option 2: Automated (Recommended)**
- Add this script to the Login request's "Tests" tab:

```javascript
if (pm.response.code === 200) {
    const response = pm.response.json();
    pm.environment.set("auth_token", response.token);
}
```

- Use `{{auth_token}}` in your Authorization headers

## 📡 API Endpoints

### Authentication Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register a new user | No |
| POST | `/api/auth/login` | Login and receive token | No |
| GET | `/api/auth/verify` | Verify token validity | Yes |

### Task Management Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/tasks` | Create a new task | Yes |
| GET | `/api/tasks` | List all tasks (paginated) | Yes |
| GET | `/api/tasks/:id` | Get a specific task | Yes |
| PUT | `/api/tasks/:id` | Full update of a task | Yes |
| PATCH | `/api/tasks/:id` | Partial update of a task | Yes |
| DELETE | `/api/tasks/:id` | Delete a task | Yes |

## 📝 Request & Response Examples

### Register User

**Request:**
```http
POST {{BASE_URL}}api/auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123",
  "name": "John Doe"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "User registered successfully",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe",
    "createdAt": "2024-01-15T10:30:00Z"
  }
}
```

### Login User

**Request:**
```http
POST {{BASE_URL}}api/auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "securePassword123"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Login successful",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "name": "John Doe"
  }
}
```

### Create Task

**Request:**
```http
POST {{BASE_URL}}api/tasks
Authorization: Bearer {{auth_token}}
Content-Type: application/json

{
  "title": "Complete project documentation",
  "description": "Write comprehensive API documentation",
  "status": "pending",
  "priority": "high",
  "dueDate": "2024-01-30T23:59:59Z"
}
```

**Response (201 Created):**
```json
{
  "success": true,
  "message": "Task created successfully",
  "task": {
    "id": 1,
    "title": "Complete project documentation",
    "description": "Write comprehensive API documentation",
    "status": "pending",
    "priority": "high",
    "dueDate": "2024-01-30T23:59:59Z",
    "createdAt": "2024-01-15T10:30:00Z",
    "updatedAt": "2024-01-15T10:30:00Z",
    "userId": 1
  }
}
```

### List Tasks (with Pagination)

**Request:**
```http
GET {{BASE_URL}}api/tasks?page=1&limit=10
Authorization: Bearer {{auth_token}}
```

**Response (200 OK):**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "title": "Complete project documentation",
      "description": "Write comprehensive API documentation",
      "status": "in-progress",
      "priority": "high",
      "dueDate": "2024-01-30T23:59:59Z",
      "createdAt": "2024-01-15T10:30:00Z",
      "updatedAt": "2024-01-16T14:20:00Z"
    }
  ],
  "pagination": {
    "page": 1,
    "limit": 10,
    "total": 45,
    "totalPages": 5,
    "hasNextPage": true,
    "hasPrevPage": false
  }
}
```

### Get Task by ID

**Request:**
```http
GET {{BASE_URL}}api/tasks/1
Authorization: Bearer {{auth_token}}
```

**Response (200 OK):**
```json
{
  "success": true,
  "task": {
    "id": 1,
    "title": "Complete project documentation",
    "description": "Write comprehensive API documentation",
    "status": "in-progress",
    "priority": "high",
    "dueDate": "2024-01-30T23:59:59Z",
    "createdAt": "2024-01-15T10:30:00Z",
    "updatedAt": "2024-01-16T14:20:00Z",
    "userId": 1
  }
}
```

### Update Task (PUT - Full Update)

**Request:**
```http
PUT {{BASE_URL}}api/tasks/1
Authorization: Bearer {{auth_token}}
Content-Type: application/json

{
  "title": "Updated: Complete project documentation",
  "description": "Write comprehensive API documentation - Updated",
  "status": "in-progress",
  "priority": "urgent",
  "dueDate": "2024-01-28T23:59:59Z"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Task updated successfully",
  "task": {
    "id": 1,
    "title": "Updated: Complete project documentation",
    "description": "Write comprehensive API documentation - Updated",
    "status": "in-progress",
    "priority": "urgent",
    "dueDate": "2024-01-28T23:59:59Z",
    "createdAt": "2024-01-15T10:30:00Z",
    "updatedAt": "2024-01-17T09:15:00Z",
    "userId": 1
  }
}
```

### Patch Update Task (PATCH - Partial Update)

**Request:**
```http
PATCH {{BASE_URL}}api/tasks/1
Authorization: Bearer {{auth_token}}
Content-Type: application/json

{
  "status": "completed"
}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Task updated successfully",
  "task": {
    "id": 1,
    "title": "Complete project documentation",
    "description": "Write comprehensive API documentation",
    "status": "completed",
    "priority": "high",
    "dueDate": "2024-01-30T23:59:59Z",
    "createdAt": "2024-01-15T10:30:00Z",
    "updatedAt": "2024-01-17T10:30:00Z",
    "userId": 1
  }
}
```

### Delete Task

**Request:**
```http
DELETE {{BASE_URL}}api/tasks/1
Authorization: Bearer {{auth_token}}
```

**Response (200 OK):**
```json
{
  "success": true,
  "message": "Task deleted successfully",
  "deletedTaskId": 1
}
```

## ⚠️ Error Handling

### Common HTTP Status Codes

| Status Code | Meaning | Common Causes |
|-------------|---------|---------------|
| 200 | OK | Request successful |
| 201 | Created | Resource created successfully |
| 400 | Bad Request | Invalid input data |
| 401 | Unauthorized | Missing or invalid token |
| 403 | Forbidden | Insufficient permissions |
| 404 | Not Found | Resource doesn't exist |
| 422 | Unprocessable Entity | Validation errors |
| 500 | Internal Server Error | Server-side error |

### Error Response Format

```json
{
  "success": false,
  "error": "Error message describing what went wrong"
}
```

### Validation Errors

```json
{
  "success": false,
  "errors": {
    "email": "Invalid email format",
    "password": "Password must be at least 8 characters"
  }
}
```

## 💡 Best Practices

### Security
- ✅ Never share your authentication tokens
- ✅ Store tokens securely in environment variables
- ✅ Use HTTPS in production environments
- ✅ Tokens may expire - handle 401 errors by re-authenticating
- ✅ Don't commit tokens or credentials to version control

### API Usage
- ✅ Use appropriate HTTP methods (GET, POST, PUT, PATCH, DELETE)
- ✅ Use PUT for full updates, PATCH for partial updates
- ✅ Implement pagination for large datasets
- ✅ Handle errors gracefully in your application
- ✅ Validate input data before sending requests

### Performance
- ✅ Use pagination to limit response sizes
- ✅ Cache frequently accessed data
- ✅ Implement retry logic for failed requests
- ✅ Use appropriate page sizes (10-50 items)

### Task Management
- ✅ Provide clear, actionable task titles
- ✅ Use consistent status values (pending, in-progress, completed)
- ✅ Set realistic due dates
- ✅ Confirm before deleting tasks (operation is permanent)

## 📊 Task Object Structure

```javascript
{
  "id": 1,                                    // Unique identifier
  "title": "Task title",                      // Required
  "description": "Detailed description",      // Optional
  "status": "pending",                        // pending, in-progress, completed, cancelled
  "priority": "high",                         // low, medium, high, urgent
  "dueDate": "2024-01-30T23:59:59Z",         // ISO 8601 format
  "createdAt": "2024-01-15T10:30:00Z",       // Auto-generated
  "updatedAt": "2024-01-16T14:20:00Z",       // Auto-updated
  "userId": 1,                                // Owner's user ID
  "tags": ["documentation", "api"],           // Optional
  "completedAt": null                         // Set when status = completed
}
```

## 🔄 Pagination

### Query Parameters

- `page` (integer): Page number (default: 1)
- `limit` (integer): Items per page (default: 10)

### Example

```
GET /api/tasks?page=2&limit=20
```

### Response Structure

```json
{
  "data": [...],
  "pagination": {
    "page": 2,
    "limit": 20,
    "total": 100,
    "totalPages": 5,
    "hasNextPage": true,
    "hasPrevPage": true
  }
}
```

## 🆘 Support

For issues, questions, or feature requests:
- Check the detailed endpoint documentation in the Postman collection
- Review the error messages for troubleshooting guidance
- Contact your API administrator


**Version:** 1.0.0  
**Last Updated:** February 2026
