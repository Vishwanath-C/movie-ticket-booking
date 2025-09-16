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

### Authentication
| Method | Endpoint             | Description       |
|--------|----------------------|-------------------|
| POST   | `/api/auth/signup`   | Register new user |
| POST   | `/api/auth/login`    | Login user (JWT)  |

### Movies
| Method | Endpoint            | Role   | Description      |
|--------|---------------------|--------|------------------|
| GET    | `/api/movies`       | User   | List all movies  |
| POST   | `/api/movies`       | Admin  | Add new movie    |

### Theatres & Shows
| Method | Endpoint              | Role   | Description       |
|--------|-----------------------|--------|-------------------|
| POST   | `/api/theatres`       | Admin  | Add theatre       |
| POST   | `/api/movieshows`     | Admin  | Add movie show    |
| GET    | `/api/movieshows`     | User   | Browse showtimes  |

### Bookings
| Method | Endpoint              | Role   | Description         |
|--------|-----------------------|--------|---------------------|
| POST   | `/api/bookings`       | User   | Book ticket         |
| GET    | `/api/bookings/user`  | User   | View booking history|
| GET    | `/api/bookings`       | Admin  | View all bookings   |

---

## 📸 Screenshots
(Add some UI screenshots here: homepage, movie list, booking page, admin dashboard)

---

## 📌 Future Enhancements
- Online payment gateway integration
- Email/SMS ticket confirmation
- Movie recommendation system
- Real-time seat availability

---

## 🤝 Contributing
Contributions are welcome! Fork the repo and submit a pull request.

---

## 📜 License
This project is licensed under the **MIT License**.  








