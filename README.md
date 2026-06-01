# 🚑 Smart Healthcare Emergency Dispatcher

An intelligent healthcare emergency management system that streamlines emergency response by connecting patients with the most suitable healthcare facilities in real time. The platform helps dispatch emergency cases efficiently, improves response coordination, and enables hospitals to manage emergency requests effectively.

## 📌 Overview

Smart Healthcare Emergency Dispatcher is a full-stack healthcare solution designed to reduce emergency response delays and improve patient care coordination. The system allows patients to submit emergency requests, enables dispatchers to process cases efficiently, and helps hospitals manage incoming emergencies through a centralized platform.

The project demonstrates real-world healthcare workflow management using Java, Spring Boot, and MySQL while following modern backend development practices.

---

## ✨ Features

### Patient Management

* Register and manage patient information
* Submit emergency requests
* Track emergency case details
* Maintain patient records

### Hospital Management

* Register hospitals in the system
* Manage hospital information
* Monitor incoming emergency requests
* Maintain healthcare facility records

### Emergency Dispatch System

* Intelligent emergency request handling
* Patient-to-hospital allocation workflow
* Centralized emergency coordination
* Faster response management

### REST API Support

* Structured RESTful endpoints
* CRUD operations for core entities
* Backend service integration
* Scalable architecture

### User Interface

* Simple and responsive web interface
* Emergency dispatch dashboard
* Hospital management views
* Patient registration and monitoring screens

---

## 🏗️ System Architecture

```text
Patient
   │
   ▼
Frontend (HTML/CSS/JS)
   │
   ▼
Spring Boot Controllers
   │
   ▼
Service Layer
   │
   ▼
Repository Layer
   │
   ▼
MySQL Database
   │
   ▼
Hospital & Emergency Records
```

### Backend Flow

1. Patient submits an emergency request.
2. Controller receives the request.
3. Service layer processes business logic.
4. Repository layer interacts with the database.
5. Appropriate hospital information is retrieved.
6. Emergency case is dispatched and recorded.
7. Response is returned to the user interface.

---

## 🛠️ Tech Stack

### Backend

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* Maven

### Database

* MySQL

### Frontend

* HTML
* CSS
* JavaScript

### Tools

* Git
* GitHub
* Postman
* IntelliJ IDEA / Eclipse

---

## 📂 Project Structure

```text
src
├── controller
│   ├── PatientController
│   ├── HospitalController
│   └── DispatcherController
│
├── service
│   ├── PatientService
│   ├── HospitalService
│   └── DispatcherService
│
├── repository
│   ├── PatientRepository
│   └── HospitalRepository
│
├── model
│   ├── Patient
│   └── Hospital
│
└── resources
    ├── templates
    ├── static
    └── application.properties
```

---

## 📸 Screenshots

### Home Page

![Home Page](assets/screenshots/home-page.png)

### Emergency Dispatch Dashboard

![Dispatch Dashboard](assets/screenshots/dispatch-dashboard.png)

### Patient Registration

![Patient Registration](assets/screenshots/patient-registration.png)

### Hospital Management

![Hospital Management](assets/screenshots/hospital-management.png)

### Emergency Allocation Result

![Emergency Allocation](assets/screenshots/allocation-result.png)

### API Testing

![Postman Testing](assets/screenshots/postman-api-testing.png)

---

## 🚀 Installation & Setup

### Clone Repository

```bash
git clone https://github.com/Vikasdh1031/Smart-Healthcare-Emergency-Dispatcher.git
```

### Navigate to Project

```bash
cd Smart-Healthcare-Emergency-Dispatcher
```

### Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/healthcare_dispatcher
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Build Project

```bash
mvn clean install
```

### Run Application

```bash
mvn spring-boot:run
```

Application will start on:

```text
http://localhost:8080
```

---

## 🔄 API Workflow

### Patient Registration

```http
POST /patients
```

### Hospital Registration

```http
POST /hospitals
```

### Emergency Dispatch

```http
POST /dispatch
```

### Get Hospital Details

```http
GET /hospitals
```

### Get Patient Details

```http
GET /patients
```

---

## 🎯 Key Learning Outcomes

* Spring Boot application development
* REST API design and implementation
* Layered architecture (Controller-Service-Repository)
* Database integration using JPA
* Healthcare workflow automation
* Backend system design
* Full-stack application development

---

## 🔮 Future Enhancements

* Google Maps integration for nearest hospital detection
* Real-time ambulance tracking
* JWT authentication and authorization
* WebSocket-based live emergency updates
* Hospital bed and ICU availability tracking
* SMS and email notifications
* Docker containerization
* CI/CD pipeline integration
* Microservices architecture migration
* AI-assisted emergency prioritization

---

## 👨‍💻 Author

**Vikas D H**

Aspiring Software Engineer | Java Full Stack Developer

GitHub: https://github.com/Vikasdh1031

---

## ⭐ Support

If you found this project useful, consider giving it a star on GitHub.
