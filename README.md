
# Admin Dashboard for Spring Actuator 

A very simple frontEnd project for the implementation of an admin dashboard to see the http request traces registered by spring actuator in the backend rest services.

## Technical Stack

* Java 17
* Bootstrap 5
* Thymeleaf 

## Configuration

To change the application configuration modify the properties in the application.properties file: 

```bash
# NAME OF THE APPLICATION
spring.application.name=monitoring
# PORT USE BY THE APPLICATION
server.port=6080
# BACKEND REST SERVICES
spring.rest.actuator.url=http://localhost:8081/actuator/httpexchanges
# DEFAULT PAGE SIZE
spring.table.pagination=5
```
    
## Demo

The application will be available at http://localhost:6080/index

