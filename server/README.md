#### Commands to start docker-compose with server and DB: 
```console
./mvnw package -Dmaven.test.skip
docker-compose -p "Petter" up
```
**-Dmaven.test.skip** argument is optional. It allows skip tests in build procedure.

#### Swagger documentation:
<localhost:8080/swagger-ui/index.html>