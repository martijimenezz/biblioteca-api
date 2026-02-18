# Architecture Documentation - Biblioteca API

## Overview

Biblioteca API es una aplicación REST backend desarrollada con Spring Boot que implementa un sistema de gestión de biblioteca. La arquitectura sigue el patrón de 3 capas (Controller → Service → Repository) con separación clara de responsabilidades.

## Architecture Layers

### 1. Presentation Layer (Controllers)

**Componentes:**
- `AuthorController` - Endpoints para gestión de autores
- `BookController` - Endpoints para gestión de libros
- `UserController` - Endpoints para gestión de usuarios
- `LoanController` - Endpoints para gestión de préstamos

**Responsabilidades:**
- Recibir requests HTTP
- Validar parámetros de entrada
- Llamar servicios de negocio
- Retornar respuestas JSON

**Endpoints Pattern:**
- `GET /api/{resource}` - Obtener todos
- `GET /api/{resource}/{id}` - Obtener por ID
- `POST /api/{resource}` - Crear nuevo
- `PUT /api/{resource}/{id}` - Actualizar
- `DELETE /api/{resource}/{id}` - Eliminar

### 2. Business Logic Layer (Services)

**Componentes:**
- `AuthorService` - Lógica de autores
- `BookService` - Lógica de libros
- `UserService` - Lógica de usuarios
- `LoanService` - Lógica de préstamos

**Responsabilidades:**
- Implementar règlas de negocio
- Validación de datos
- Transacciones
- Orquestación de repositorios

**Patrones:**
- Los servicios son stateless
- Usan inyección de dependencias
- Soportan múltiples clientes (web, mobile, API)

### 3. Data Access Layer (Repositories)

**Componentes:**
- `AuthorRepository` - Acceso a autores
- `BookRepository` - Acceso a libros
- `UserRepository` - Acceso a usuarios
- `LoanRepository` - Acceso a préstamos

**Responsabilidades:**
- Acceso a base de datos
- Queries personalizadas
- Gestión de transacciones

**Implementación:**
- Extienden `JpaRepository`
- Generan implementaciones automáticas
- Soporte para custom queries

## Data Model

### Entity Relationships

```
Author (1) ──────────── (N) Book
            author_id

User (1) ───────────── (N) Loan ───────────── (N) Book
         user_id            book_id
```

### Entity Diagram

```
┌──────────────┐
│   Authors    │
├──────────────┤
│ id (PK)      │
│ name         │
│ country      │
│ birth_year   │
└──────────────┘
      ▲
      │ 1:N
      │
┌──────────────┐
│    Books     │
├──────────────┤
│ id (PK)      │
│ title        │
│ isbn         │
│ author_id(FK)│◄──
│ pub_year     │
│ description  │
│ copies       │
│ avail_copies │
└──────────────┘

┌──────────────┐
│    Users     │
├──────────────┤
│ id (PK)      │
│ name         │
│ email        │
│ member_id    │
│ city         │
│ active       │
└──────────────┘
      ▲
      │ 1:N
      │
┌──────────────┐
│    Loans     │
├──────────────┤
│ id (PK)      │
│ book_id(FK)  │
│ user_id(FK)  │
│ loan_date    │
│ due_date     │
│ return_date  │
│ status       │
└──────────────┘
      ▲
      │ N:1
      │
      │ Book (N:1)
```

## Infrastructure Architecture

### Local Development

```
┌─────────────────────────┐
│   Docker Compose        │
├─────────────────────────┤
│                         │
│  ┌──────────────┐       │
│  │  Spring App  │       │
│  │  :8080       │       │
│  └──────┬───────┘       │
│         │               │
│  ┌──────▼───────┐       │
│  │  PostgreSQL  │       │
│  │  :5432       │       │
│  │  volume      │       │
│  └──────────────┘       │
│                         │
└─────────────────────────┘
         ▼
      Localhost
    http://localhost:8080
```

### CI/CD Pipeline

```
GitHub Repository
        │
        ├─ Git Push/PR
        │
        ▼
GitHub Actions Workflow
        │
        ├─ Setup (Java 17)
        │
        ├─ Code Quality (Checkstyle)
        │
        ├─ Unit Tests (*ServiceTest)
        │
        ├─ Integration Tests (*ControllerIT + PostgreSQL)
        │
        ├─ Build (Maven package)
        │
        ├─ Docker Build (dockerfile multi-stage)
        │
        └─ Acceptance Tests (docker-compose + curl)
        │
        ▼
   Artifacts Generated
   ├─ unit-test-results/
   ├─ integration-test-results/
   └─ app-jar/
```

## Technology Stack

### Backend
- **Framework:** Spring Boot 3.2
- **Language:** Java 17
- **Build Tool:** Maven 3.9
- **ORM:** Spring Data JPA/Hibernate
- **Validation:** Jakarta Bean Validation

### Database
- **Primary:** PostgreSQL 15
- **Testing:** H2 In-Memory
- **Migration:** Hibernate DDL

### Testing
- **Unit Tests:** JUnit 5 + Mockito
- **Integration Tests:** Spring Boot Test + MockMvc
- **Container Testing:** Testcontainers

### DevOps
- **Containerization:** Docker
- **Orchestration:** Docker Compose
- **CI/CD:** GitHub Actions
- **Code Quality:** Checkstyle 10.12
- **Version Control:** Git

### API & Serialization
- **Format:** JSON
- **Protocol:** REST over HTTP
- **CORS:** Configurable

## Request-Response Flow

### Example: GET /api/books/1

```
Client (HTTP GET)
    │
    ▼
BookController.getBookById(1)
    │
    ├─ Validate input (ID > 0)
    │
    ├─ Call BookService.getBookById(1)
    │   │
    │   ├─ Call BookRepository.findById(1)
    │   │   │
    │   │   └─ Query Database: SELECT * FROM books WHERE id = 1
    │   │       │
    │   │       └─ Return Optional<Book>
    │   │
    │   └─ Handle result
    │
    ├─ Build response (200 OK)
    │
    └─ Return JSON
        │
        ▼
Client (JSON response)
```

### Example: POST /api/books

```
Client (HTTP POST + JSON body)
    │
    ▼
BookController.createBook(book)
    │
    ├─ Validate input
    │   ├─ Title not empty
    │   ├─ ISBN not empty
    │   └─ Author exists
    │
    ├─ Call BookService.createBook(book)
    │   │
    │   ├─ Business logic validation
    │   │
    │   ├─ Call BookRepository.save(book)
    │   │   │
    │   │   └─ Execute INSERT into database
    │   │       │
    │   │       └─ Return saved book with ID
    │   │
    │   └─ Return Book entity
    │
    ├─ Build response (201 CREATED)
    │
    └─ Return created Book as JSON
        │
        ▼
Client (JSON response + Location header)
```

## Error Handling

Errors son manejados a través de `GlobalExceptionHandler`:

```java
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RuntimeException.class)
    → Returns 400 Bad Request
    
    @ExceptionHandler(Exception.class)
    → Returns 500 Internal Server Error
}
```

Response format:
```json
{
  "timestamp": "2024-02-18T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Entity not found"
}
```

## Testing Architecture

### Unit Tests (Service Layer)

```
AuthorServiceTest
├─ Tests con Mocks de Repository
├─ Valida lógica de negocio isolada
└─ No requiere BD real

Example: testGetAllAuthors()
```

### Integration Tests (Controller Layer)

```
AuthorControllerIT
├─ Usa H2 in-memory DB
├─ Tests full stack (Controller → Service → Repository)
├─ MockMvc para simular HTTP requests
└─ Valida respuestas JSON completas

Example: testGetAllAuthors()
  Mock HTTP GET /api/authors
  Assert status 200
  Assert JSON array
```

## Security Considerations

### Authentication & Authorization
- Actualmente: Sin autenticación (abierto)
- Future: Implementar JWT/OAuth2
- CORS: Configurable por entorno

### Input Validation
- Server-side validation en Controllers
- Jakarta Bean Validation annotations
- Custom validators en Services

### Database Security
- Contraseñas via environment variables
- No hardcoded credentials
- Use of connection pooling (HikariCP)

## Performance Considerations

### Database
- Índices en FK relationships
- Lazy loading con JPA
- Connection pooling: HikariCP

### Caching (Future)
- Redis para cache
- Cache de consultas frecuentes
- TTL configurable

### Scalability
- Stateless services
- Horizontal scaling ready
- Load balancing ready

## Deployment Architecture

### Local (Development)
```
docker-compose up
├─ Spring Boot App (8080)
└─ PostgreSQL (5432)
```

### Production
```
Load Balancer (443)
    │
    ├─ App Instance 1 (8080)
    ├─ App Instance 2 (8080)
    └─ App Instance N (8080)
         │
         └─ Database Pool (5432)
            └─ PostgreSQL Cluster
```

## Monitoring & Observability

### Logging
- Configuración via application.yml
- Levels: DEBUG, INFO, WARN, ERROR
- Structured logging ready

### Metrics
- Docker stats para container metrics
- Database query logging (disabled in production)
- Application metrics: Future (Micrometer + Prometheus)

## Future Architecture Improvements

1. **Microservices:** Separar servicios (Authors, Books, Users, Loans)
2. **Event-Driven:** Event sourcing para auditoría
3. **Caching:** Redis para performance
4. **GraphQL:** API alternativa a REST
5. **Search:** Elasticsearch para búsqueda avanzada
6. **Async:** Message queues (RabbitMQ/Kafka)
7. **Observability:** Tracing (Jaeger), Metrics (Prometheus), Logs (ELK)
8. **API Gateway:** Kong/NGINX para rate limiting, routing
9. **Service Mesh:** Istio para production

## Conclusion

La arquitectura de Biblioteca API está diseñada para ser:
- ✅ Simple e intuitiva para desarrollo
- ✅ Escalable para crecer
- ✅ Mantenible y testeable
- ✅ Production-ready
- ✅ Cloud/Container-native
