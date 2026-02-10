# Clientes y Cuentas Microservicio

Microservicio para gestionar clientes y sus cuentas bancarias, implementado con **Spring Boot**, **H2 Database** y documentado con **OpenAPI/Swagger**. 

## 📋 Índice

- [Tecnologías](#-tecnologías)
- [Instalación y Ejecución](#-instalación-y-ejecución)
- [Base de Datos](#-base-de-datos)
- [Documentación](#-documentación)
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


## 🧪 Testing
Para ejecutar los tests, usa el siguiente comando:
   ```bash
   ./mvnw test
   ```

## Posibles Mejoras
- Utilizar BigDecimal para manejar montos monetarios con mayor precisión.
- Implementar validaciones más robustas para los datos de entrada.

