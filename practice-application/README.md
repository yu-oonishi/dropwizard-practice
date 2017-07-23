# Dropwizard practice-application

## Running The Application

How to start the application.

1. Run `mvn package` to build your application
1. Setup the h2 database with `java -jar target/practice-application.jar db migrate config.yml`
1. Start application with `java -jar target/practice-application.jar server config.yml`
1. To check that your application is running enter url `http://localhost:8080`

## Health Check

To see your applications health enter url `http://localhost:8081/healthcheck`
