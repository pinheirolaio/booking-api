# Booking API

### Rest API responsible for managing reservations
Use the API documentation available through Swagger to explore the operations and parameters, the Postman collection and Swagger itself can be used for testing.

#### API Main operations
- create a host
- create a booking for that host
- add guests to a booking
- create host blocks to avoid scheduling for a period of time
- manage guests, host, blocks and booking details
- retrieve data

### Build and run
`mvn clean package spring-boot:run`

### Swagger
- API documentation with details on usage is available at the address below.
http://localhost:8080/swagger-ui.html

### Postman collection 
- There is a Postman collection in the root of the project that 
can be imported into the tool for testing: booking-api.postman_collection.json


----

*Due to time problems, it was not possible to completely cover the application with unit tests.

Laio Pinheiro (laiopinheiro01@gmail.com)
