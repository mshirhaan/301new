### Ordering Service
This is order management microservice which is a part Restaurants application. This service provides provision to place order for a customer, update the order, get order details by ID, or cancel the order.

This application is written using Spring boot and JPA. The database used is MySQL.

The api documentation is done using swagger, whose configuration can be found in: **com.eatza.orderingservice.config.SwaggerConfiguration**

The APIs are secured using JWT token, whose configuration can be found in:
**com.eatza.orderingservice.config.JwtFilter**

Since Consumer service for restaurants is not in scope. One mock user is hardcoded in property files and JWT is generated at login if credentials match that user. (Default credentials hardcoded are username: user and password:password)

### Prerequisites and setup:


* Java 8
* Maven


### How to run
Build the application with `mvn clean install`. By default `local` profile is activated, to activate any other profile pass spring.profiles.active as run time variable while running jar. For instance, if you want to run jar in dev profile, run it using :
`java -jar -Dspring.profiles.active=dev < jar-name > `


### Code structure

All the rest controllers are stored in `com.eatza.order.controller`  package which will respond to any request coming to REST endpoints.

All the services are stored in `com.eatza.order.service`  package which will be contacted by controllers and will manipulcate the data as required and will contact JPA layer directly.

All the Models are stored in 
`com.eatza.order.model` package which are mapped directly to db schemas using JPA.

All the data transfer objects are stored in 
`com.eatza.order.dto` package which accepts request and provide response in particular structure.

All the configuration related stuff such as swagger config, jwt config and rest template config are stored in 
`com.eatza.order.config` package

Exceptions are handled using `controlleradvice` and are kept in `com.eatza.order.exception` package

All the dao classes and relations are stored in `com.eatza.order.repository` package


### REST APIs 

* You can directly send the api requests through swagger which is integrated with this spring boot application
  Access the link http://localhost:8081/swagger-ui.html after running the application.

_Some information about the rest APIs_

### Login Request

* Login with username - user and password - password hardcoded in application properties to get JWT token which has to be sent as header with all other API requests.

Login api is present in jwt-authentication-controller, you can test it by passing username and password in post request body as follows:

```
curl -X POST "http://localhost:8080/login" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"password\": \"password\", \"username\": \"user\"}"
```

It will return you back a JWT token. Append Bearer in front of that token and pass it as `authorization header` in every request. For example if this request gives you back `abc` pass authorization header as `bearer abc` in every request.


### placeOrder in order-controller

This API requires customerId, items and restaurantId to be passed along with authorization bearer token as header and it will return order details if order is placed successfully.

Some business cases it is checking are:

1. Calling restaurant search service to get item details, if item is not found at all, throws exception.

2. If item not found in restaurantid provided, throws exception.

3. If Item quantity is passes as zero in requestBody, throwing exception.

4. If restaurant search service is down, hadling and throwing relevant exception.

The restaurant search service is called using Rest Templates.

```
curl -X POST "http://localhost:8081/order" -H "accept: */*" -H "authorization: <Bearer Token>" -H "Content-Type: application/json" -d "{ \"customerId\": <customer-d>, \"items\": [ { \"itemId\": <item-id>, \"quantity\": <item-quantity> } ], \"restaurantId\": <restaurant-id>}""
```

### cancelOrder in order-controller

This API requires authorization bearer token as header and path variable `orderid` as input. And it will cancel the order if found in DB.


```
curl -X PUT "http://localhost:8081/order/cancel/<order-id>" -H "accept: */*" -H "authorization: <Bearer token>"
```

### updateOrder in order-controller

This API is used to update any already placed order.

This API requires customerId, items, orderId to update and restaurantId to be passed along with authorization bearer token as header and it will return order details if order is updated successfully.

The business rules it will check are:

1. If given orderID is not present, throws exception

2. If restaurant ID present is different than the restaurant from which order was placed at first, throws exception since restaurants cannot be changed will updation.

3. If Item given is not present in restaurant menu, throws exception.

4. If quantity of item to update is zero or less, throws exception.

5. If restaurant search service is down, hadling and throwing relevant exception.

```
curl -X PUT "http://localhost:8081/order" -H "accept: */*" -H "authorization: <Bearer Token>" -H "Content-Type: application/json" -d "{ \"customerId\": <customer-id>, \"items\": [ { \"itemId\": <item-id>, \"quantity\": <item-quantity> } ], \"orderId\": <order-id>, \"restaurantId\": <restaurant-id>}"
```

### getOrderById in order-controller

This api requires path variable `id` to be passed along with authorization bearer token as header and it will return back order with given ID if found in DB.

```
curl -X GET "http://localhost:8081/order/<order-id>" -H "accept: */*" -H "authorization: <Bearer Token>"
```

### getOrderAmountByOrderId in order-controller

This api will give you order total for a particular order.

This api requires path variable `id` to be passed along with authorization bearer token as header and it will return back order total for given Order ID if found in DB.


```
curl -X GET "http://localhost:8081/order/value/<Order-id>" -H "accept: */*" -H "authorization: <Bearer Token>"
```









