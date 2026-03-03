# 🚀 Java Spring Boot Full-Stack Portfolio  

> Production-Ready Cloud Deployed Spring Boot Application  
> Built with Security, Clean Architecture & DevOps Practices  

---

## 👨‍💻 About The Project  

This is my personal **Full-Stack Portfolio Application**, built using **Java 17 + Spring Boot** and deployed using **Docker containerization** on cloud infrastructure.

This project demonstrates:

- Enterprise-level backend architecture  
- Secure authentication implementation  
- Distributed cloud deployment  
- Real-world debugging & DevOps problem solving  
- Modern AI-assisted development workflow  

---

## 🌐 Live Application  

🔗 **Live URL:**  
 https://adityasaini-dev.onrender.com
---

## 🛠️ Tech Stack  

### 🔹 Backend
- Java 17  
- Spring Boot 2.7.18  
- Spring Security  
- Spring Data JPA  
- Hibernate  

### 🔹 Frontend
- Thymeleaf  
- HTML5  
- CSS3  
- Bootstrap  

### 🔹 Database
- MySQL (Hosted on Clever Cloud)

### 🔹 DevOps & Deployment
- Docker  
- Render (Cloud Hosting)  
- Maven  

### 🔹 Architecture
- MVC (Model-View-Controller)  
- Layered Service Architecture  

---

## 🏗️ Project Structure  

```
com.aditya.portfolio
│
├── entities/       → Database Models
├── dto/            → Data Transfer Objects
├── repository/     → Data Access Layer
├── services/       → Business Logic Layer
├── controllers/    → HTTP Request Handling
└── config/         → Security Configuration
```

### Why This Structure?

- Clean separation of concerns  
- Highly maintainable  
- Scalable  
- Industry-level modular backend design  

---

## 🔐 Security Implementation  

### ✔ BCrypt Password Encryption  

```java
PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
```

- Passwords are never stored in plain text  
- Industry standard hashing  

---

### ✔ Spring Security Integration  

- Authentication configured  
- CSRF protection enabled  
- Secure route handling  

---

### ✔ JWT Authentication  

- Stateless authentication  
- Token-based security flow  
- API-ready structure  

---

## 🌍 Distributed Cloud Architecture  

Instead of hosting everything together:

- **Backend Application** → Docker container on Render  
- **Database** → MySQL hosted on Clever Cloud  

### Why?

- Better security isolation  
- Production-style deployment  
- Scalable infrastructure mindset  

---

## 🧗 Deployment Challenges Solved  

### 1️⃣ Lombok Constructor Conflict  
Removed Lombok to stabilize Maven build lifecycle.

---

### 2️⃣ Dependency Cleanup  
Removed duplicate dependencies and unused JSP libraries.

---

### 3️⃣ WAR vs JAR Docker Issue  
Fixed artifact mismatch in Dockerfile.

---

### 4️⃣ Hibernate Dialect Error  

```properties
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
```

---

### 5️⃣ Render Port Binding Issue  

```properties
server.port=${PORT:8080}
```

Configured dynamic port binding to fix deployment crash and resolved HikariCP timeout issues through proper DB URL formatting.

---

## 🤖 AI-Assisted Development Approach  

This project reflects a modern development workflow.

- The **Backend architecture, business logic, security implementation, and deployment configuration** were independently designed and implemented.
- AI tools were used as a **productivity assistant** for:
  - Debugging complex cloud deployment issues  
  - Optimizing Maven and Docker configurations  
  - Improving frontend structure while learning UI development  

AI was treated as an engineering support tool — not a replacement for backend development skills.

This demonstrates the ability to:
- Leverage AI responsibly  
- Improve development speed  
- Solve production-level issues efficiently  
- Adapt to modern software engineering practices  

---

## 📈 Production Practices Followed  

- Layered architecture  
- Environment-based configuration  
- Secure password hashing  
- Docker containerization  
- Cloud debugging experience  
- Clean dependency management  

---

## 💻 Run Locally  

### 1️⃣ Clone Repository  

```bash
git clone https://github.com/adityaasaini/java-spring-portfolio.git
```

---

### 2️⃣ Create Database  

Create MySQL database named:

```
portfolio
```

---

### 3️⃣ Update application.properties  

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/portfolio
spring.datasource.username=root
spring.datasource.password=your_password
```

---

### 4️⃣ Run Application  

```bash
mvn clean install
mvn spring-boot:run
```

---

## 🚀 Future Improvements  

- Global Exception Handling  
- Unit Testing (JUnit + Mockito)  
- Logging (Log4j2 / SLF4J)  
- CI/CD (GitHub Actions)  
- Redis Caching  
- AWS Deployment (EC2 + RDS)  

---

## 🎯 What This Project Demonstrates  

✔ Backend Development  
✔ Security Implementation  
✔ Docker Knowledge  
✔ Cloud Deployment  
✔ Debugging Real-World Issues  
✔ AI-Integrated Modern Workflow  
✔ Clean Code Architecture  

---

## 👨‍💻 Author  

**Aditya Saini**  
Java Backend Developer | Security Enthusiast  

---