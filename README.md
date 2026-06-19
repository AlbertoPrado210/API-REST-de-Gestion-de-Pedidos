
# DESARROLLO DE EXAMEN: ALBERTO PRADO AGURTO
# API REST de Gestión de Pedidos

API REST para gestionar clientes, productos y pedidos de una tienda.

## Tecnologías usadas

- Java 21
- Spring Boot 3.3.0
- Maven
- PostgreSQL
- Spring Data JPA / Hibernate
- Spring Validation
- Lombok
- JUnit 5
- Mockito

## Configuración de base de datos

Crear la base de datos antes de ejecutar la aplicación:

```sql
CREATE DATABASE db_pedidos;
```

Credenciales configuradas en `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/db_pedidos
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
```

## Instrucciones para ejecutar

```
mvn clean install
mvn spring-boot:run
```

La aplicación corre en: `http://localhost:8080`

## Endpoints disponibles

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| POST | /api/clientes | Registrar un cliente |
| GET | /api/clientes/{id} | Buscar cliente por ID |
| POST | /api/productos | Registrar un producto |
| GET | /api/productos | Listar todos los productos |
| POST | /api/pedidos | Crear un pedido |
| GET | /api/pedidos/{id} | Buscar pedido por ID |
| GET | /api/pedidos/cliente/{clienteId} | Listar pedidos de un cliente |

## Ejemplos de request JSON

**Crear cliente**
```json
POST /api/clientes
{
  "nombre": "Juan",
  "apellido": "Pérez",
  "dni": "12345678",
  "correo": "juan.perez@gmail.com"
}
```

**Crear producto**
```json
POST /api/productos
{
  "nombre": "Teclado mecánico",
  "descripcion": "Teclado RGB",
  "precio": 150.00,
  "stock": 20
}
```

**Crear pedido**
```json
POST /api/pedidos
{
  "clienteId": 1,
  "items": [
    {
      "productoId": 1,
      "cantidad": 2
    },
    {
      "productoId": 2,
      "cantidad": 1
    }
  ]
}
```

## Pruebas unitarias

```
mvn test
```
