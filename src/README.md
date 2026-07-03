# 📝 TaskManager - Spring Boot REST API

A robust backend for task management that evolved from a classic Java console application into a modern web server architecture.

This project was developed with a strong focus on clean architecture, object-oriented design principles, and security (perfectly aligned with professional software engineering standards).

## 🚀 About the Project
The TaskManager allows users to create, manage, and categorize tasks. The core of the application is a Spring Boot server that communicates with clients (e.g., web browsers or API clients like Postman) via a REST API and securely persists data in a MySQL database.

## 🛠️ Technologies & Architecture
* **Language:** Java (17+)
* **Framework:** Spring Boot (Web, REST Controllers)
* **Database:** MySQL (connected via native JDBC / DAO pattern)
* **Security:** BCrypt for secure password hashing
* **Testing:** JUnit 5 & Mockito
* **Build Tool:** Maven

## ✨ Core Features
* **CRUD Operations:** Full capabilities to create, read, update, and delete tasks.
* **RESTful API:** Clean and standardized endpoints (e.g., `/api/tasks`) for seamless frontend integration.
* **Advanced Task Models:** Support for priorities (LOW, MEDIUM, HIGH), categories (WORK, PERSONAL, SCHOOL), and specialized deadline tasks.
* **User Management:** DAO-based user management with encrypted passwords.
* **Unit Testing:** Core logic and database interactions are secured by automated tests.

## ⚙️ Installation & Setup (Local)

1. **Clone the repository:**
   ```bash
   git clone https://github.com/SelcukAhjin/Task-Manager-Java
   ```