# Clientes y Cuentas Microservicio

Microservicio para gestionar clientes y sus cuentas bancarias, implementado con **Spring Boot**, **H2 Database** y documentado con **OpenAPI/Swagger**. 

## 📋 Índice

- [Tecnologías](#-tecnologías)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Base de Datos](#-base-de-datos)
- [Documentación](#-documentación)
- [Endpoints](#-endpoints)
- [Testing](#-testing)

## 🚀 Tecnologías

- **Java 21**
- **Spring Boot 3.5**
- **Spring Web / Spring Data JPA**
- **H2 Database**
- **JUnit 5** + **Spring Boot Test** + **Mockito**
- **Springdoc OpenAPI**
- **Maven**

## 📦 Instalación y Ejecución

1. Clona el repositorio:
   ```bash
    git clone https://github.com/albertoGUEZ/clientes-cuentas-microservicios.git
    cd clientes-cuentas-microservicios
    ```
   
2. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

## 🗄️ Base de Datos
H2 Database
   - H2 Console: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
   - JDBC URL: `jdbc:h2:mem:clientes-cuentas-db`
   - User Name: `sa`
   - Password: (dejar vacío)

## 📚 Documentación
OpenAPI/Swagger
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- OpenAPI JSON: [http://localhost:8080/api-docs](http://localhost:8080/api-docs)

## 🔗 Endpoints
Clientes

| Método | Endpoint                                     | Descripción                                           |
| ------ | -------------------------------------------- | ----------------------------------------------------- |
| GET    | `/clientes`                                  | Obtener todos los clientes                            |
| GET    | `/clientes/mayores-de-edad`                  | Obtener clientes mayores de 18 años                   |
| GET    | `/clientes/con-cuenta-superior-a/{cantidad}` | Obtener clientes con saldo total mayor a `{cantidad}` |
| GET    | `/clientes/{dni}`                            | Obtener cliente por DNI                               |

Ejemplos peticiones:
```bash
# Obtener todos los clientes
curl -X GET http://localhost:8080/clientes

# Obtener clientes mayores de 18 años
curl -X GET http://localhost:8080/clientes/mayores-de-edad

# Obtener clientes con saldo total > 1000
curl -X GET http://localhost:8080/clientes/con-cuenta-superior-a/1000

# Obtener cliente por DNI
curl -X GET http://localhost:8080/clientes/11111111A
```

CuentasBancarias

| Método | Endpoint        | Descripción                                 |
| ------ | --------------- | ------------------------------------------- |
| POST   | `/cuentas`      | Crear una nueva cuenta bancaria             |
| PUT    | `/cuentas/{id}` | Actualizar el saldo de una cuenta existente |

Ejemplos peticiones:
```bash
# Crear una nueva cuenta
curl -X POST http://localhost:8080/cuentas \
-H "Content-Type: application/json" \
-d '{
  "dniCliente": "11111111A",
  "tipoCuenta": "NORMAL",
  "total": 1500
}'

# Actualizar saldo de una cuenta
curl -X PUT http://localhost:8080/cuentas/1 \
-H "Content-Type: application/json" \
-d '{
  "total": 2000
}'

```

## 🧪 Testing
Para ejecutar los tests, usa el siguiente comando:
   ```bash
   ./mvnw test
   ```

## Posibles Mejoras
- Utilizar BigDecimal para manejar montos monetarios con mayor precisión.
- Implementar validaciones más robustas para los datos de entrada.