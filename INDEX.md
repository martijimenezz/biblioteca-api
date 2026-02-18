# Documentation Index - Biblioteca API

## 📚 Guías Principales

### Para Empezar
- **[QUICK_START.md](QUICK_START.md)** - Guía de 5 minutos para comenzar
  - Opción 1: Docker Compose
  - Opción 2: Local con Maven
  - Opción 3: Con Makefile
  - Ejemplo completo

### Documentación Completa
- **[README.md](README.md)** - Documentación principal del proyecto
  - Descripción del proyecto
  - Estructura
  - Tecnologías utilizadas
  - Base de datos
  - Instalación completa
  - Endpoints de API
  - Tests realizados
  - Pipeline CI/CD
  - Mantenimiento

### Arquitectura y Diseño
- **[ARCHITECTURE.md](ARCHITECTURE.md)** - Documentación técnica profunda
  - Capas de la aplicación
  - Modelos de datos
  - Flujo de requests
  - Manejo de errores
  - Consideraciones de seguridad
  - Performance
  - Escalabilidad

## 🚀 Despliegue y DevOps

### Despliegue
- **[DEPLOYMENT.md](DEPLOYMENT.md)** - Guía de despliegue
  - Despliegue local
  - Despliegue en servidor remoto
  - Configuración de producción
  - SSL/TLS
  - Backups
  - Monitoreo
  - Troubleshooting
  - Seguridad

### Configuración
- **.github/workflows/ci-cd.yml** - Pipeline de GitHub Actions
- **docker-compose.yml** - Configuración de servicios
- **Dockerfile** - Imagen de la aplicación
- **pom.xml** - Configuración de Maven
- **checkstyle.xml** - Reglas de estilo de código
- **application.yml** - Configuración de Spring Boot

## 👥 Colaboración

### Para Contribuidores
- **[CONTRIBUTING.md](CONTRIBUTING.md)** - Guía de contribuciones
  - Cómo reportar bugs
  - Cómo sugerir mejoras
  - Proceso de desarrollo
  - Guías de estilo
  - Cómo escribir tests
  - Pull requests

### Información del Proyecto
- **[CHANGELOG.md](CHANGELOG.md)** - Historial de cambios y versiones
- **[STATUS.md](STATUS.md)** - Estado actual del proyecto

## 🛠️ Herramientas y Utilidades

### Scripts
- **build.sh** - Script de build para Linux/Mac
- **build.bat** - Script de build para Windows
- **Makefile** - Comandos make para desarrollo
- **fixtures.sql** - Datos de prueba para la BD

### API Testing
- **Biblioteca-API.postman_collection.json** - Colección de Postman

### Configuración
- **.env.example** - Ejemplo de variables de entorno
- **.gitignore** - Archivos ignorados por Git

## 📋 Estructura del Proyecto

```
biblioteca-api/
├── QUICK_START.md              # ← COMIENZA AQUÍ
├── README.md                   # Documentación principal
├── ARCHITECTURE.md             # Arquitectura técnica
├── DEPLOYMENT.md               # Despliegue
├── CONTRIBUTING.md             # Contribuciones
├── CHANGELOG.md                # Historial
├── STATUS.md                   # Estado del proyecto
│
├── docker-compose.yml          # Orquestación Docker
├── Dockerfile                  # Imagen de app
├── pom.xml                     # Maven
├── checkstyle.xml              # Linting rules
│
├── .github/workflows/
│   └── ci-cd.yml               # Pipeline CI/CD
│
├── src/main/java/com/biblioteca/
│   ├── BibliotecaApplication.java
│   ├── config/
│   ├── controller/             # 4 controllers
│   ├── service/                # 4 services
│   ├── repository/             # 4 repositories
│   ├── model/                  # 4 entities
│   └── exception/
│
├── src/test/java/com/biblioteca/
│   ├── service/                # 27 unit tests
│   └── controller/             # 23 integration tests
│
├── fixtures.sql                # Datos de prueba
├── build.sh / build.bat        # Scripts de build
└── Makefile                    # Comandos make
```

## 🔍 Buscar Información Específica

### "Quiero..."

#### Empezar rápido
→ Ver [QUICK_START.md](QUICK_START.md)

#### Entender la arquitectura
→ Ver [ARCHITECTURE.md](ARCHITECTURE.md)

#### Desplegar en producción
→ Ver [DEPLOYMENT.md](DEPLOYMENT.md)

#### Contribuir al proyecto
→ Ver [CONTRIBUTING.md](CONTRIBUTING.md)

#### Ver todos los endpoints
→ Ver [README.md](README.md#endpoints-de-la-api)

#### Cargar datos de prueba
→ Ver [README.md](README.md#cargar-datos-de-prueba-fixtures)

#### Ejecutar tests
→ Ver [README.md](README.md#ejecutar-tests)

#### Monitorear la aplicación
→ Ver [DEPLOYMENT.md](DEPLOYMENT.md#monitoreo)

#### Ver cambios y mejoras futuras
→ Ver [CHANGELOG.md](CHANGELOG.md)

#### Información sobre tests
→ Ver [README.md](README.md#tests)

#### Ver estado del proyecto
→ Ver [STATUS.md](STATUS.md)

## 📞 Contacto y Soporte

### GitHub Issues
Si encuentras problemas:
1. Busca en el [README.md - Troubleshooting](README.md#troubleshooting)
2. Revisa [DEPLOYMENT.md - Troubleshooting](DEPLOYMENT.md#troubleshooting)
3. Crea un nuevo issue en GitHub

### Licencia
El proyecto está bajo licencia MIT.

## 📊 Estadísticas del Proyecto

- **Archivos Documentación:** 7
- **Líneas de código Java:** ~2000
- **Tests:** 50 (27 unit, 23 integration)
- **Cobertura:** >80%
- **Clases:** 20+
- **Endpoints:** 20+
- **Base de datos:** 4 tablas

## 🎯 Puntos Clave

### Características Implementadas
✅ API REST JSON  
✅ CRUD completo  
✅ 50 tests automáticos  
✅ CI/CD con GitHub Actions  
✅ Docker & Docker Compose  
✅ PostgreSQL  
✅ Maven build  
✅ Checkstyle code quality  
✅ Documentación completa  
✅ Producción lista  

### Tecnologías
- Java 17
- Spring Boot 3.2
- PostgreSQL 15
- JUnit 5
- Docker
- GitHub Actions
- Maven

## 📝 Último Vistazo

**Para empezar en 5 minutos:** [QUICK_START.md](QUICK_START.md)

**Para entender todo:** [README.md](README.md)

**Para ver el código:** Ver carpeta `src/`

**Para deployar:** [DEPLOYMENT.md](DEPLOYMENT.md)

**Para contribuir:** [CONTRIBUTING.md](CONTRIBUTING.md)

---

**¡Gracias por usar Biblioteca API!** 🎉

Última actualización: 18 de Febrero de 2024
Versión: 1.0.0
