# Quick Start Guide - Biblioteca API

## 5 Minutos para comenzar

### Opción 1: Con Docker Compose (Recomendado)

```bash
# 1. Clonar el repositorio
git clone https://github.com/tu-usuario/biblioteca-api.git
cd biblioteca-api

# 2. Iniciar servicios
docker-compose up --build

# 3. En otra terminal, cargar datos de prueba
curl -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{"name": "Gabriel García Márquez", "country": "Colombia", "birthYear": 1927}'

# 4. Verificar
curl http://localhost:8080/api/authors

# 5. Ver logs
docker-compose logs -f app
```

✅ La aplicación está disponible en `http://localhost:8080`

### Opción 2: Con Maven (Local)

```bash
# 1. Clonar
git clone https://github.com/tu-usuario/biblioteca-api.git
cd biblioteca-api

# 2. Construir
mvn clean package

# 3. Ejecutar tests
mvn test

# 4. Ejecutar
mvn spring-boot:run

# 5. Verificar
curl http://localhost:8080/api/authors
```

ℹ️ Requiere Java 17+ y PostgreSQL ejecutándose

### Opción 3: Con Makefile (Linux/Mac)

```bash
# Ver todos los comandos disponibles
make help

# Iniciar todo
make docker-compose

# Ejecutar tests
make test

# Detener servicios
make stop
```

## Usar Postman

1. Abrir Postman
2. Click en "Import"
3. Seleccionar `Biblioteca-API.postman_collection.json`
4. Ya tienes todas las requests lista para usar

## API Rápida

### Autores
```bash
GET    /api/authors              # Obtener todos
GET    /api/authors/1            # Obtener uno
POST   /api/authors              # Crear
PUT    /api/authors/1            # Actualizar
DELETE /api/authors/1            # Eliminar
```

### Libros
```bash
GET    /api/books                # Obtener todos
GET    /api/books/available      # Disponibles
POST   /api/books                # Crear
```

### Usuarios
```bash
GET    /api/users                # Obtener todos
POST   /api/users                # Crear
```

### Préstamos
```bash
GET    /api/loans                # Obtener todos
GET    /api/loans/user/1         # Préstamos del usuario 1
POST   /api/loans                # Crear préstamo
```

## Ejemplo Completo

```bash
# 1. Crear autor
AUTHOR_ID=$(curl -s -X POST http://localhost:8080/api/authors \
  -H "Content-Type: application/json" \
  -d '{"name": "Gabriel García Márquez", "country": "Colombia", "birthYear": 1927}' \
  | jq '.id')

# 2. Crear libro
BOOK_ID=$(curl -s -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d "{\"title\": \"One Hundred Years\", \"isbn\": \"123\", \"author\": {\"id\": $AUTHOR_ID}, \"copies\": 5, \"availableCopies\": 3}" \
  | jq '.id')

# 3. Crear usuario
USER_ID=$(curl -s -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John", "email": "john@mail.com", "memberId": "M1", "active": true}' \
  | jq '.id')

# 4. Crear préstamo
curl -s -X POST http://localhost:8080/api/loans \
  -H "Content-Type: application/json" \
  -d "{\"book\": {\"id\": $BOOK_ID}, \"user\": {\"id\": $USER_ID}, \"loanDate\": \"2024-01-15\", \"dueDate\": \"2024-01-29\", \"status\": \"ACTIVE\"}" \
  | jq .
```

## Troubleshooting

### Puerto 8080 ya en uso
```bash
# Encontrar proceso
lsof -i :8080

# Matar proceso
kill -9 <PID>
```

### PostgreSQL no conecta
```bash
# Ver logs
docker-compose logs database

# Reiniciar
docker-compose restart database
```

### Tests fallan
```bash
# Limpiar y reconstruir
mvn clean
mvn test

# Específico
mvn test -Dtest=AuthorServiceTest
```

### Data en PostgreSQL no persiste
Los volúmenes de Docker se crean automáticamente. Para limpiar:
```bash
docker volume rm biblioteca-api_postgres_data
```

## Comandos Útiles

```bash
# Logs en tiempo real
docker-compose logs -f

# Ejecutar en contenedor
docker-compose exec app bash

# Acceder a PostgreSQL
docker-compose exec database psql -U postgres -d biblioteca

# Crear backup
docker-compose exec database pg_dump -U postgres biblioteca > backup.sql

# Restaurar backup
docker-compose exec -T database psql -U postgres biblioteca < backup.sql

# Ver uso de recursos
docker stats

# Limpiar todo
docker-compose down -v
```

## URLs Importantes

- API Base: `http://localhost:8080`
- Docs (Future): `http://localhost:8080/api/docs`
- Database: `localhost:5432`

## Próximos Pasos

1. 📖 Leer [README.md](README.md) para documentación completa
2. 🏗️ Ver [ARCHITECTURE.md](ARCHITECTURE.md) para arquitectura
3. 🚀 Ver [DEPLOYMENT.md](DEPLOYMENT.md) para producción
4. 🤝 Ver [CONTRIBUTING.md](CONTRIBUTING.md) para contribuir
5. 📝 Ver [CHANGELOG.md](CHANGELOG.md) para versiones

## Support

¿Problemas? 
- Revisa la sección "Issues" en GitHub
- Crea un nuevo issue con detalles
- Consulta la documentación en README.md

¡Bienvenido a Biblioteca API! 🎉
