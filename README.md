# Java Bootcamp

## Start application

1. Build with maven (Java 11, Maven 3.6.x)  
   `mvn clean install`
2. Run Spring Boot application  
   with VM options `-DfirebaseKey="[...]"`

## Documentation

See [docs/README.md](docs/README.md) for more information.

## Test application

1. With Swagger   
   http://localhost:8080/swagger.html
2. With Curl
   `curl 'http://localhost:8080/firebase/getAllPaths' -H 'Cookie: JSESSIONID=[...]'`