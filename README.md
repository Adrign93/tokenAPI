# Token Generator Service

Este es un microservicio desarrollado en Java 17 con Spring Boot encargado de la generación y validación de tokens JWT (JSON Web Tokens).

## 📦 Instalación y Ejecución

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/tu-usuario/tokenGenerator.git
    ```

2.  **Compilar el proyecto:**
    ```bash
    ./mvnw clean install
    ```

3.  **Ejecutar la aplicación:**
    ```bash
    ./mvnw spring-boot:run
    ```

## endpoints Principales

*   `POST /api/token`: Genera un nuevo token JWT enviando las credenciales del usuario.
## Usuarios para probar
Los siguientes usuarios se han definido en la base de datos para poder realizar las pruebas:
### Usuario 1:
* `username:` Ana
* `password:` 123456
* `entity:` DELIVERY
### Usuario 2:
* `username:` Carlos
* `password:` 123456
* `entity:` RRHH

## Swagger 
* `Url:` http://localhost:8080/swagger-ui/index.html

### CURL de ejemplo
```bash
curl --location 'localhost:8080/api/v1/token' \
--header 'Content-Type: application/json' \
--data '{
    "username": "Ana",
    "password": "123456",
    "entity": "DELIVERY"
}'
```

## 📄 Licencia

Este proyecto está bajo la Licencia MIT.
