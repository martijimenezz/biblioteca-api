# Deployment Guide - Biblioteca API

## Introducción

Este documento describe cómo desplegar la aplicación Biblioteca API en diferentes ambientes.

## Ambientes Soportados

1. **Development**: Máquina local con Docker Compose
2. **Testing**: Entorno de pruebas con CI/CD
3. **Production**: Servidor remoto con Docker

## Despliegue Local (Desarrollo)

### Requisitos

- Docker Desktop 4.0+
- Docker Compose 2.0+
- Git

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/biblioteca-api.git
   cd biblioteca-api
   ```

2. **Iniciar los servicios**
   ```bash
   docker-compose up --build
   ```

3. **Verificar que la aplicación está corriendo**
   ```bash
   curl http://localhost:8080/api/authors
   ```

4. **Cargar datos de prueba (opcional)**
   ```bash
   docker-compose exec database psql -U postgres -d biblioteca -f /fixtures.sql
   ```

### Detener servicios

```bash
docker-compose down
```

## Despliegue en Servidor Remote

### Requisitos

- Servidor Linux (Ubuntu 20.04+)
- Docker Engine 20.0+
- Docker Compose 2.0+
- Git
- SSH access

### Pasos

1. **Conectarse al servidor**
   ```bash
   ssh usuario@servidor.com
   ```

2. **Clonar el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/biblioteca-api.git
   cd biblioteca-api
   ```

3. **Crear archivo .env para variables de producción**
   ```bash
   cat > .env << EOF
   POSTGRES_PASSWORD=produccion_password_seguro
   SPRING_DATASOURCE_PASSWORD=produccion_password_seguro
   ENVIRONMENT=production
   EOF
   ```

4. **Modificar docker-compose para producción**
   ```bash
   # Actualizar puertos y configuraciones si es necesario
   nano docker-compose.yml
   ```

5. **Iniciar servicios en background**
   ```bash
   docker-compose up -d
   ```

6. **Verificar estado**
   ```bash
   docker-compose ps
   docker-compose logs -f app
   ```

## Configuración de Producción

### Variables de Entorno

```yaml
# Configuración de Base de Datos
SPRING_DATASOURCE_URL: jdbc:postgresql://database:5432/biblioteca
SPRING_DATASOURCE_USERNAME: postgres
SPRING_DATASOURCE_PASSWORD: tu_password_seguro

# Configuración de JPA
SPRING_JPA_HIBERNATE_DDL_AUTO: validate

# Logging
LOGGING_LEVEL_ROOT: WARN
LOGGING_LEVEL_COM_BIBLIOTECA: INFO

# Servidor
SERVER_PORT: 8080
SERVER_SERVLET_CONTEXT_PATH: /
```

### SSL/TLS

Para habilitar HTTPS, configurar un proxy inverso como nginx:

```nginx
server {
    listen 443 ssl http2;
    server_name api.biblioteca.com;

    ssl_certificate /etc/letsencrypt/live/api.biblioteca.com/fullchain.pem;
    ssl_certificate_key /etc/letsencrypt/live/api.biblioteca.com/privkey.pem;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

### Backups de Base de Datos

```bash
# Script de backup automático
#!/bin/bash
docker-compose exec -T database pg_dump -U postgres biblioteca > backup_$(date +%Y%m%d_%H%M%S).sql
```

## Monitoreo

### Ver logs

```bash
# Logs de la aplicación
docker-compose logs app

# Logs en tiempo real
docker-compose logs -f app

# Logs de la base de datos
docker-compose logs -f database
```

### Métricas

```bash
# Ver uso de recurso de contenedores
docker stats
```

## Escalado

### Múltiples instancias de aplicación

```yaml
# docker-compose.yml
services:
  app1:
    build: .
    environment:
      - SERVER_PORT=8080
    ports:
      - "8080:8080"
    depends_on:
      - database

  app2:
    build: .
    environment:
      - SERVER_PORT=8081
    ports:
      - "8081:8080"
    depends_on:
      - database

  # Balanceador de carga
  nginx:
    image: nginx:latest
    ports:
      - "80:80"
    volumes:
      - ./nginx.conf:/etc/nginx/nginx.conf
    depends_on:
      - app1
      - app2
```

## Troubleshooting

### Conexión rechazada en PostgreSQL

```bash
# Verificar que el contenedor de base de datos está corriendo
docker-compose ps

# Ver logs de la base de datos
docker-compose logs database

# Reiniciar el contenedor
docker-compose restart database
```

### Puerto ya en uso

```bash
# Cambiar puerto en docker-compose.yml
# O matar el proceso en el puerto
lsof -i :8080
kill -9 <PID>
```

### Aplicación no responde

```bash
# Reiniciar la aplicación
docker-compose restart app

# Ver logs de error
docker-compose logs app --tail=100
```

## Actualizar la aplicación

```bash
# Detener servicios
docker-compose down

# Obtener cambios del repositorio
git pull origin main

# Reconstruir y reiniciar
docker-compose up --build -d
```

## Seguridad

### Contraseñas

- Cambiar contraseñas por defecto en variables de entorno
- Usar secretos de Docker

### Firewall

- Exponer solo puertos necesarios (80, 443)
- Restringir acceso a puerto de base de datos (5432)

### CORS

- Configurar CORS para dominios específicos
- Ver `CorsConfig.java`

## Performance

### Tuning de PostgreSQL

Agregar a `docker-compose.yml`:

```yaml
database:
  environment:
    - POSTGRES_INITDB_ARGS=-c max_connections=100 -c shared_buffers=256MB
```

### Connection pooling

Spring Boot utiliza HikariCP por defecto. Configurar en `application.yml`:

```yaml
spring:
  datasource:
    hikari:
      maximum-pool-size: 20
      minimum-idle: 5
      connection-timeout: 30000
```

## Respaldo y Recuperación

### Crear backup

```bash
docker-compose exec database pg_dump -U postgres biblioteca > backup.sql
```

### Restaurar backup

```bash
docker-compose exec -T database psql -U postgres biblioteca < backup.sql
```

## Rollback

```bash
# Detener versión actual
docker-compose down

# Checkout versión anterior
git checkout tags/v1.0.0

# Reconstruir y reiniciar
docker-compose up --build -d
```

## Checklist Pre-Producción

- [ ] Crear archivo `.env` con variables seguras
- [ ] Cambiar contraseñas por defecto
- [ ] Configurar certificados SSL/TLS
- [ ] Configurar backups automáticos
- [ ] Configurar monitoreo y alertas
- [ ] Pruebas de carga completas
- [ ] Plan de disaster recovery
- [ ] Documentación actualizada
- [ ] Permisos de usuarios correctos
- [ ] Firewall configurado
