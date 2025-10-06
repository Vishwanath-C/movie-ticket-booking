# **Movie Ticket Booking System**

A fullstack web application for booking movie tickets online.

Built with :
    - Spring boot (backend and REST APIs with JWT authentication)
    - React.js (frontend)
    - MySQL (database)

Features
👤 User Features :
    - Register & login with JWT authentication
    - Browse available movies and theatres
    - View showtimes & seat availability
    - Book tickets with seat selection
    - View booking history

🔑 Admin Features :
    - Manage movies (add, update, delete)
    - Manage theatres and shows
    - View all bookings

Tech stack :
    - Frontend : React, MUI
    - Backend : Spring Boot
    - Database : MySQL
    - Tools : Gradle

## 📂 Project Structure
```
movie-ticket-booking/
│── backend/               # Spring Boot application
│   ├── src/main/java/...  # Backend code
│   ├── src/main/resources # application.properties
│   └── build.gradle       # Gradle config
│
│── frontend/              # React application
│   ├── public/            # Static assets
│   ├── src/               # React components, pages, services
│   └── package.json       # Dependencies
│
└── README.md

## ⚙️ Setup Instructions  

### 1. Clone the repository  
```bash
git clone https://github.com/your-username/movie-ticket-booking.git
cd movie-ticket-booking
```

### 2. Setup Backend (Spring Boot)
```bash
cd backend
```
- Configure **MySQL database** in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/moviedb
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.security.jwt.secret=your_jwt_secret
```
- Run the backend:
```bash
./gradlew bootRun
```

Backend will start at **http://localhost:8080**

---

### 3. Setup Frontend (React)
```bash
cd frontend
npm install   # or yarn install
npm start     # or yarn start
```

Frontend will run at **http://localhost:3000**

---

## 🔑 API Endpoints (Examples)

---

## 📸 Screenshots


---













