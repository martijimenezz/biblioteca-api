# Contributing to Biblioteca API

Gracias por interesarte en contribuir a Biblioteca API. Este documento proporciona pautas y directrices para contribuir al proyecto.

## Código de Conducta

Se espera que todos los contribuidores cumplan con nuestro Código de Conducta:
- Ser respetuoso y considerado
- Aceptar críticas constructivas
- Enfocarse en lo que es mejor para la comunidad

## Cómo Contribuir

### Reportar Bugs

Antes de crear un reporte de bug, comprueba el historial de problemas ya que tu problema podría ya estar reportado. Si encuentras un bug nuevo:

1. Usa un título descriptivo
2. Describe los pasos exactos para reproducir el problema
3. Proporciona ejemplos específicos para demostrar los pasos
4. Describe el comportamiento observado y lo que esperabas
5. Incluye screenshots o videos si es posible
6. Incluye tu sistema operativo y versión de Docker

### Sugerir Mejoras

Las sugerencias de mejora incluyen nuevas características o pequeñas mejoras a funcionalidad existente. Al crear una sugerencia:

1. Usa un título descriptivo
2. Proporciona una descripción detallada de la mejora
3. Proporciona ejemplos específicos para demostrar los pasos
4. Explica por qué esta mejora sería útil

### Pull Requests

- Sigue los estilos de código del proyecto (JStyle via Checkstyle)
- Escribe buenas descripciones en los commits
- Termina en todos tus commits con una línea vacía
- Incluye pruebas apropiadas
- Asegúrate de que los tests pasen
- Actualiza la documentación según sea necesario

## Processo de Desarrollo

### 1. Fork el repositorio

```bash
git clone https://github.com/tu-usuario/biblioteca-api.git
cd biblioteca-api
```

### 2. Crear una rama para tu feature

```bash
git checkout -b feature/nombre-descriptivo
```

Usa estos prefijos de rama:
- `feature/` para nuevas características
- `bugfix/` para correcciones de bugs
- `docs/` para cambios de documentación
- `test/` para agregar/mejorar tests

### 3. Instalar dependencias

```bash
mvn install
```

### 4. Realizar cambios

- Escribe código limpio y bien documentado
- Sigue la estructura del proyecto
- Asegúrate de cumplir con Checkstyle

### 5. Ejecutar tests

```bash
# Tests unitarios
mvn test -Dtest=*ServiceTest

# Tests de integración
mvn test -Dtest=*ControllerIT

# Todos los tests
mvn test
```

### 6. Ejecutar verificaciones de código

```bash
mvn checkstyle:check
```

### 7. Commit de cambios

```bash
git add .
git commit -m "feat: descripción clara de los cambios"
```

Usa estos prefijos en commits:
- `feat:` para nuevas características
- `fix:` para correcciones de bugs
- `docs:` para cambios de documentación
- `style:` para cambios de formato
- `refactor:` para refactorización de código
- `test:` para agregar/modificar tests
- `chore:` para tareas de mantenimiento

### 8. Push a tu rama

```bash
git push origin feature/nombre-descriptivo
```

### 9. Crear Pull Request

En GitHub, crea un PR con una descripción clara:

```markdown
## Descripción
Descripción clara de los cambios realizados.

## Tipo de Cambio
- [ ] Bug fix
- [ ] Nueva característica
- [ ] Cambio breaking
- [ ] Documentación

## Cambios
- Primer cambio
- Segundo cambio

## Testing
Describe cómo probaste los cambios:
- [ ] Tests unitarios
- [ ] Tests de integración
- [ ] Pruebas manuales

## Checklist
- [ ] Mi código sigue las pautas de estilo del proyecto
- [ ] Ejecuté Checkstyle y no hay errores
- [ ] He realizado una auto-revisión de mi propio código
- [ ] He comentado mi código, particularmente en áreas complejas
- [ ] He realizado cambios correspondientes a la documentación
- [ ] Mis cambios no generan nuevas advertencias
- [ ] Agregué tests que prueban que mi fix o feature funciona
- [ ] Tests unitarios e integración pasan localmente
```

## Guías de Estilo

### Java

- Usa Java 17+
- Sigue Google Java Style Guide
- Máximo 120 caracteres por línea
- Use PascalCase para nombres de clase
- Use camelCase para nombres de método/variable
- Use UPPER_SNAKE_CASE para constantes

### SQL

- Use mayúsculas para palabras clave SQL
- Indentar bloques de código
- Agregar comentarios para queries complejas

### Commits

```
<tipo>(<escopo>): <subject>

<body>

<footer>
```

Ejemplo:
```
feat(book): agregar búsqueda por ISBN

Implementar endpoint GET /api/books/search?isbn=X
para búsqueda rápida de libros por número ISBN.

Closes #123
```

## Testing

### Escribir Tests

- Cubre al menos el 80% del código
- Una prueba por método de negocio
- Nombres descriptivos: `testShouldReturnAllAuthorsWhenCallingGetAll()`
- Usa Given-When-Then pattern

```java
@Test
@DisplayName("Should return all authors")
void testGetAllAuthors() {
    // Given
    List<Author> expectedAuthors = new ArrayList<>();
    when(authorRepository.findAll()).thenReturn(expectedAuthors);
    
    // When
    List<Author> result = authorService.getAllAuthors();
    
    // Then
    assertEquals(expectedAuthors, result);
    verify(authorRepository, times(1)).findAll();
}
```

## Building & Testing

```bash
# Build
mvn clean package

# Run all tests
mvn test

# Run specific test
mvn test -Dtest=AuthorServiceTest

# Check code style
mvn checkstyle:check

# Generate code coverage
mvn jacoco:report
```

## Documentación

- Actualizar README.md si agregas nuevas features
- Agregar JavaDoc a métodos públicos
- Documentar cambios significativos en CHANGELOG.md

## Licencia

Al contribuir, aceptas que tus contribuciones serán licenciadas bajo la Licencia MIT.

## Preguntas?

- Crear una issue con la etiqueta `question`
- Participar en pull request existentes
- Contactar con los maintainers

¡Gracias por contribuir!
