# Biblioteca API

## Descripción del Proyecto

Biblioteca API es una aplicación REST headless para la gestión de una biblioteca. La aplicación implementa un sistema completo de CI/CD usando GitHub Actions, Docker y Maven. 

### Características

- **Gestión de Autores**: CRUD de autores de libros
- **Gestión de Libros**: CRUD de libros con control de disponibilidad
- **Gestión de Usuarios**: CRUD de miembros de la biblioteca
- **Gestión de Préstamos**: Control de préstamos de libros con seguimiento de fechas
- **API REST**: Endpoints JSON para todas las operaciones

## Estructura del Proyecto

```
biblioteca-api/
├── src/
│   ├── main/
│   │   ├── java/com/biblioteca/
│   │   │   ├── BibliotecaApplication.java      # Clase principal
│   │   │   ├── controller/                     # Controladores REST
│   │   │   ├── service/                        # Lógica de negocio
│   │   │   ├── repository/                     # Acceso a datos
│   │   │   └── model/                          # Entidades JPA
│   │   └── resources/
│   │       └── application.yml                 # Configuración
│   └── test/
│       ├── java/com/biblioteca/
│       │   ├── service/                        # Tests unitarios
│       │   └── controller/                     # Tests de integración
│       └── resources/
│           └── application-test.yml            # Config de tests
├── docker/                                      # Archivos Docker
├── docker-compose.yml                          # Orquestación de servicios
├── Dockerfile                                  # Imagen de la app
├── pom.xml                                     # Configuración Maven
├── checkstyle.xml                              # Reglas de estilo
└── .github/workflows/ci-cd.yml                 # Pipeline CI/CD

```

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación
- **Spring Boot 3.2**: Framework web
- **Spring Data JPA**: Acceso a datos
- **PostgreSQL**: Base de datos
- **Maven**: Gestión de proyectos
- **JUnit 5**: Framework de testing
- **Docker**: Contenedorización
- **Docker Compose**: Orquestación
- **GitHub Actions**: CI/CD
- **Checkstyle**: Linting de código

## Base de Datos

### Tablas

1. **authors**: Autores de libros
   - id (PK)
   - name
   - country
   - birth_year

2. **books**: Libros de la biblioteca
   - id (PK)
   - title
   - isbn
   - author_id (FK)
   - publication_year
   - description
   - copies
   - available_copies

3. **users**: Miembros de la biblioteca
   - id (PK)
   - name
   - email (UNIQUE)
   - member_id (UNIQUE)
   - city
   - active

4. **loans**: Préstamos de libros
   - id (PK)
   - book_id (FK)
   - user_id (FK)
   - loan_date
   - due_date
   - return_date
   - status

## Configuración de Variables de Entorno

### Usando archivo `.env`

La aplicación utiliza un archivo `.env` para gestionar variables de entorno de forma segura. NO comitees este archivo al repositorio (está en `.gitignore`).

1. Copia el archivo de ejemplo:
```bash
cp .env.example .env
```

2. Edita `.env` con tus valores (especialmente para credenciales en producción):
```
POSTGRES_DB=biblioteca
POSTGRES_USER=postgres
POSTGRES_PASSWORD=tu_contraseña_segura
POSTGRES_PORT=5432
SPRING_DATASOURCE_URL=jdbc:postgresql://database:5432/biblioteca
APP_PORT=8080
```

3. Ejecuta Docker Compose:
```bash
docker-compose up -d
```

Docker Compose cargará automáticamente las variables desde `.env`.

## Cambios Realizados (Feedback del Profesor)

### ✅ 1. Gestión de Variables de Entorno
- **Creado**: Archivo `.env` para variables sensibles
- **Creado**: Archivo `.env.example` como plantilla
- **Actualizado**: `docker-compose.yml` ahora referencia variables desde `.env`
- Las credenciales ya no están en texto plano en el código

### ✅ 2. Tests para Entidades
- **Creado**: `AuthorTest.java` - Tests para entidad Author con JUnit fixtures
- **Creado**: `BookTest.java` - Tests para entidad Book con relaciones
- **Creado**: `UserTest.java` - Tests para entidad User
- **Creado**: `LoanTest.java` - Tests para entidad Loan (la más compleja)
- Todos los tests usan fixtures de JUnit en lugar de solo confiar en `@BeforeEach`

### ✅ 3. Optimización del Workflow CI/CD
- **Removido**: Job `setup` redundante que no hacía nada útil
- **Combinado**: Unit tests y Entity tests en un solo job `test`
- **Optimizado**: Eliminadas configuraciones de Java repetidas
- **Mejora**: El workflow ahora es más eficiente sin dependencias innecesarias
- Los jobs `code-quality` e `integration-tests` se ejecutan en paralelo

### ✅ 4. Automatización de Fixtures
- **Actualizado**: `docker-compose.yml` ahora monta `fixtures.sql` en `/docker-entrypoint-initdb.d/`
- Los fixtures se cargan automáticamente cuando se inicia PostgreSQL
- En GitHub Actions se ejecuta `psql` para crear la BD antes de tests

## Estructura del Workflow Optimizado

```
test (Entity & Unit Tests)      code-quality      integration-tests
        ↓                            ↓                      ↓
        └────────────┬───────────────┴───────────────────┘
                     ↓
                   build
                     ↓
                docker-build
                     ↓
            acceptance-tests
                     ↓
              build-status
```

## Instalación y Ejecución

### Requisitos Previos

- Docker y Docker Compose
- Maven 3.6+
- Java 17+
- Git

### Opción 1: Ejecutar con Docker Compose

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/biblioteca-api.git
cd biblioteca-api

# Construir e iniciar los servicios
docker-compose up --build

# La aplicación estará disponible en http://localhost:8080
```

### Opción 2: Ejecutar localmente con Maven

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/biblioteca-api.git
cd biblioteca-api

# Construir el proyecto
mvn clean package

# Ejecutar la aplicación
# Nota: Requiere PostgreSQL ejecutándose localmente
mvn spring-boot:run
```

## Cargar Datos de Prueba (Fixtures)

La aplicación crea automáticamente la estructura de la base de datos. Para cargar datos de prueba, ejecute los siguientes comandos curl después de que la aplicación esté ejecutándose:

```bash
# Crear autores
curl -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gabriel García Márquez",
    "country": "Colombia",
    "birthYear": 1927
  }'

curl -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jorge Luis Borges",
    "country": "Argentina",
    "birthYear": 1899
  }'

# Crear libros (use los IDs de autores retornados)
curl -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "One Hundred Years of Solitude",
    "isbn": "978-0060883287",
    "author": {"id": 1},
    "publicationYear": 1967,
    "copies": 5,
    "availableCopies": 3
  }'

# Crear usuarios
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "memberId": "M001",
    "city": "Barcelona",
    "active": true
  }'

# Crear préstamos
curl -X POST http://localhost:8080/api/loans \
  -H "Content-Type: application/json" \
  -d '{
    "book": {"id": 1},
    "user": {"id": 1},
    "loanDate": "2024-01-15",
    "dueDate": "2024-01-29",
    "status": "ACTIVE"
  }'
```

## Ejecutar Tests

### Tests Unitarios

```bash
# Ejecutar todos los tests unitarios
mvn test -Dtest=*ServiceTest

# Ejecutar tests de un servicio específico
mvn test -Dtest=AuthorServiceTest
```

### Tests de Integración

```bash
# Ejecutar todos los tests de integración
mvn test -Dtest=*ControllerIT

# Requiere PostgreSQL ejecutándose
```

### Todos los Tests

```bash
# Ejecutar todos los tests
mvn test

# Con reporte de cobertura
mvn test jacoco:report
```

## Endpoints de la API

### Autores
- `GET /api/authors` - Obtener todos los autores
- `GET /api/authors/{id}` - Obtener autor por ID
- `POST /api/authors` - Crear nuevo autor
- `PUT /api/authors/{id}` - Actualizar autor
- `DELETE /api/authors/{id}` - Eliminar autor

### Libros
- `GET /api/books` - Obtener todos los libros
- `GET /api/books/{id}` - Obtener libro por ID
- `GET /api/books/available` - Obtener libros disponibles
- `POST /api/books` - Crear nuevo libro
- `PUT /api/books/{id}` - Actualizar libro
- `DELETE /api/books/{id}` - Eliminar libro

### Usuarios
- `GET /api/users` - Obtener todos los usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `POST /api/users` - Crear nuevo usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario

### Préstamos
- `GET /api/loans` - Obtener todos los préstamos
- `GET /api/loans/{id}` - Obtener préstamo por ID
- `GET /api/loans/user/{userId}` - Obtener préstamos del usuario
- `POST /api/loans` - Crear nuevo préstamo
- `PUT /api/loans/{id}` - Actualizar préstamo
- `DELETE /api/loans/{id}` - Eliminar préstamo

## Verificación del Funcionamiento

### Usar curl

```bash
# Verificar que la aplicación está funcionando
curl http://localhost:8080/api/authors

# Debe retornar un JSON vacío [] o con los datos cargados
```

### Usar Postman

Se proporciona una colección de Postman exportada. Para importarla:

1. Abrir Postman
2. Click en "Import"
3. Seleccionar el archivo `Biblioteca-API.postman_collection.json`
4. Las requests estarán organizadas por recurso

## Tests

### Tests Unitarios (8 tests)
- **AuthorServiceTest**: 7 tests
  - `testGetAllAuthors`
  - `testGetAuthorById`
  - `testCreateAuthor`
  - `testUpdateAuthor`
  - `testDeleteAuthor`
  - `testUpdateAuthorNotFound`
  
- **BookServiceTest**: 7 tests
  - `testGetAllBooks`
  - `testGetBookById`
  - `testCreateBook`
  - `testUpdateBook`
  - `testDeleteBook`
  - `testGetAvailableBooks`
  - `testUpdateBookNotFound`

- **UserServiceTest**: 6 tests
  - `testGetAllUsers`
  - `testGetUserById`
  - `testCreateUser`
  - `testUpdateUser`
  - `testDeleteUser`
  - `testUpdateUserNotFound`

- **LoanServiceTest**: 7 tests
  - `testGetAllLoans`
  - `testGetLoanById`
  - `testCreateLoan`
  - `testUpdateLoan`
  - `testDeleteLoan`
  - `testGetLoansByUserId`
  - `testUpdateLoanNotFound`

**Total Unitarios: 27 tests**

### Tests de Integración (18 tests)
- **AuthorControllerIT**: 6 tests
  - `testGetAllAuthors`
  - `testGetAuthorById`
  - `testCreateAuthor`
  - `testUpdateAuthor`
  - `testDeleteAuthor`
  - `testGetNonExistentAuthor`

- **BookControllerIT**: 6 tests
  - `testGetAllBooks`
  - `testGetBookById`
  - `testCreateBook`
  - `testUpdateBook`
  - `testGetAvailableBooks`
  - `testDeleteBook`

- **UserControllerIT**: 5 tests
  - `testGetAllUsers`
  - `testGetUserById`
  - `testCreateUser`
  - `testUpdateUser`
  - `testDeleteUser`
  - `testGetNonExistentUser`

- **LoanControllerIT**: 6 tests
  - `testGetAllLoans`
  - `testGetLoanById`
  - `testGetLoansByUserId`
  - `testCreateLoan`
  - `testUpdateLoan`
  - `testDeleteLoan`

**Total Integración: 23 tests**

**TOTAL: 50 tests**

## Pipeline de CI/CD

### Descripción del Workflow (`ci-cd.yml`)

El workflow se ejecuta automáticamente en cada push a las ramas `main` o `develop`, y en cada pull request.

#### Pasos del Pipeline:

1. **Setup** (`setup`)
   - Configura el entorno Java 17
   - Descarga dependencias de Maven

2. **Code Quality** (`code-quality`)
   - Ejecuta Checkstyle para validar formato del código
   - Verifica la correctitud del código

3. **Unit Tests** (`unit-tests`)
   - Ejecuta todos los tests unitarios
   - Carga los resultados como artifacts

4. **Integration Tests** (`integration-tests`)
   - Inicia un servicio PostgreSQL
   - Ejecuta todos los tests de integración
   - Carga los resultados como artifacts

5. **Build** (`build`)
   - Construye el JAR ejecutable
   - Carga el artifact

6. **Docker Build** (`docker-build`)
   - Construye la imagen Docker
   - Utiliza multi-stage build para optimizar

7. **Acceptance Tests** (`acceptance-tests`)
   - Inicia los servicios con docker-compose
   - Verifica que los endpoints responding
   - Ejecuta tests de aceptación

8. **Build Status** (`build-status`)
   - Reporta el estado final del pipeline

### Desencadenadores

- **Push** a `main` o `develop`
- **Pull Requests** a `main` o `develop`

### Artifacts Generados

- `unit-test-results`: Reportes de tests unitarios
- `integration-test-results`: Reportes de tests integración
- `app-jar`: JAR compilado de la aplicación

## Configuración de Linting (Checkstyle)

El archivo `checkstyle.xml` define las reglas de estilo del código:

- Máximo 120 caracteres por línea
- Máximo 2000 líneas por archivo
- Máximo 100 líneas por método
- Máximo 8 parámetros por método
- Llaves requeridas en estructuras de control
- Formato correcto de llaves

## Mantenimiento

### Limpiar contenedores

```bash
docker-compose down
docker system prune -a
```

### Obtener logs

```bash
# Logs de la aplicación
docker-compose logs app

# Logs de PostgreSQL
docker-compose logs database

# Seguir logs en tiempo real
docker-compose logs -f
```

## Contribuciones

1. Fork el repositorio
2. Create una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit los cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo licencia MIT.

## Autor

Desarrollado como parte del proyecto intermodular de DAM.

## Contacto

Para más información, contactar a través de GitHub Issues.
