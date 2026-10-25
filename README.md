# CRM Backend

Spring Boot REST API for a CRM: JWT authentication, role-based access (ADMIN / USER), user management, and lead capture with status tracking.

## Tech Stack

Java 17, Spring Boot 4.1.1, Spring Security, JWT (JJWT), Spring Data JPA / Hibernate, MySQL, Actuator, Logback (text + JSON logs), Maven, Docker

## Features

- JWT login with ADMIN / USER roles
- Public lead form with input validation and duplicate detection (email or phone)
- Lead listing with pagination, sorting, status filter and search
- Bean Validation on all request bodies with field-level error messages
- Lead status tracking: `NEW`, `CONTACTED`, `CONVERTED`, `LOSS`
- User management (list, view, update, activate / deactivate)
- Custom 401 / 403 responses and global exception handling
- Rolling text and JSON log files

## Run Locally

Requires JDK 17 and MySQL.

```bash
git clone https://github.com/akashtyagi709/CRM.git
cd CRM
mysql -u root -p -e "CREATE DATABASE CRM;"
```

Create `src/main/resources/application-local.properties` (git-ignored):

```properties
spring.datasource.password=<your-db-password>
app.jwt.secret=<any-random-string-of-32-or-more-characters>
```

Run with the `local` profile:

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```

Runs on `http://localhost:8080`. Before registering the first user, add a company:

```sql
INSERT INTO company (company_name, created_at) VALUES ('My Company', NOW());
```

## Environment Variables

| Variable | Required | Default |
|---|---|---|
| `DB_PASSWORD` | Yes | none |
| `JWT_SECRET` | Yes (min 32 characters) | none |
| `DB_URL` | No | `jdbc:mysql://localhost:3306/CRM` |
| `DB_USERNAME` | No | `root` |
| `CORS_ALLOWED_ORIGINS` | No | `http://localhost:4200` |
| `PORT` | No | `8080` |

## Docker

```bash
docker build -t crm-backend .
docker run -p 8080:8080 -e DB_URL=jdbc:mysql://<host>:3306/CRM -e DB_USERNAME=<user> -e DB_PASSWORD=<password> -e JWT_SECRET=<secret> crm-backend
```

## API

Response format: `{ "msg": "...", "success": true, "data": {} }`

Protected endpoints need the header `Authorization: Bearer <token>`.

| Method | Endpoint | Description | Access |
|---|---|---|---|
| POST | `/api/auth/register` | Register first admin | Public |
| POST | `/api/auth/user-register` | Register a USER | Admin |
| POST | `/api/auth/login` | Login, returns JWT | Public |
| POST | `/api/contact` | Submit a lead | Public |
| GET | `/api/leads` | List leads (paginated, filter, search) | Authenticated |
| POST | `/api/leads/{id}/status` | Change lead status | Authenticated |
| GET | `/api/users` | List users | Admin |
| GET | `/api/users/{id}` | Get user | Admin |
| PUT | `/api/updateUser/{id}` | Update user | Admin |
| PATCH | `/api/user/{id}/status?active=true` | Activate / deactivate user | Admin |
| GET | `/health` | Health check | Public |

Login body: `{ "email": "admin@example.com", "password": "..." }`
Lead body: `{ "name": "...", "email": "...", "phone": "...", "message": "..." }`
Status body: `{ "status": "CONTACTED" }`

Leads query parameters: `status` (`NEW`, `CONTACTED`, `CONVERTED`, `LOSS`), `search` (name or email), `page`, `size` (max 100), `sort` (`id`, `name`, `email`, `status`, `createdAt`, `updatedAt`).
Example: `GET /api/leads?status=NEW&search=john&page=0&size=10&sort=createdAt,desc`

Validation errors return `400` with field messages: `{ "msg": "Validation failed", "success": false, "data": { "email": "Email is not valid" } }`