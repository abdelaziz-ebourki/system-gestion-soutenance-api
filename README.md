# system-gestion-soutenance-api

API RESTful pour la gestion des soutenances de projets de fin d'études.

## Stack

- **Spring Boot 3.4.6** (Java 17)
- **Spring Data JPA** + **H2** (dev) / **MySQL** (prod)
- **Spring Security** + **JWT** (jjwt)
- **Lombok**

## Auth

### `POST /api/login`

Authentifie un utilisateur et retourne un JWT.

**Request:**
```json
{ "email": "admin@univ.com", "password": "1234" }
```

**Response 200:**
```json
{
  "user": { "id": "1", "email": "admin@univ.com", "role": "admin", "lastName": "Ahmadi", "firstName": "Mohamed", "isActive": true },
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "expiresAt": 1779215552096
}
```

**Response 401:**
```json
{ "message": "Identifiants invalides (E-mail ou mot de passe incorrect)" }
```

### `POST /api/auth/verify-account`

Active un compte après inscription (set password).

**Request:**
```json
{ "token": "<base64(userId)>", "password": "new-password" }
```

**Response 200:**
```json
{ "message": "Account verified successfully" }
```

## Utilisateurs de test (seed data)

| Email | Mot de passe | Rôle |
|---|---|---|
| admin@univ.com | 1234 | admin |
| coord@univ.com | 1234 | coordinator |
| teacher@univ.com | 1234 | teacher |
| moussa@univ.com | 1234 | teacher |
| student@univ.com | 1234 | student |
| student1..50@univ.com | 1234 | student |

## Endpoints prévus (à implémenter)

| Méthode | Path | Rôle |
|---|---|---|
| GET/POST/PUT/DELETE | `/api/admin/users` | admin |
| POST | `/api/admin/users/bulk` | admin |
| GET/POST/PUT/DELETE | `/api/admin/departments` | admin |
| GET/POST/PUT/DELETE | `/api/admin/sessions` | admin |
| GET/POST/PUT/DELETE | `/api/admin/rooms` | admin |
| GET/POST/PUT/DELETE | `/api/admin/config/**` | admin |
| GET | `/api/admin/stats` | admin |
| GET/POST/PUT/DELETE | `/api/coordinator/projects` | coordinator |
| GET/POST | `/api/coordinator/jurys` | coordinator |
| POST | `/api/coordinator/schedule` | coordinator |
| GET/POST | `/api/teacher/evaluations` | teacher |
| GET/POST | `/api/teacher/unavailability` | teacher |
| GET | `/api/teacher/schedule` | teacher |
| GET | `/api/teacher/stats` | teacher |
| GET/POST | `/api/student/group` | student |
| POST | `/api/student/group/:id/join` | student |
| GET | `/api/student/defense` | student |
| GET | `/api/student/documents` | student |
| GET | `/api/student/convocation` | student |
| GET | `/api/student/stats` | student |

## Démarrage

```bash
cd api
./mvnw spring-boot:run
```

H2 console: http://localhost:8080/h2-console (JDBC URL: `jdbc:h2:mem:defensedb`)
