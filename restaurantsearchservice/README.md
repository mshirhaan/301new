### Restaurant Search Service
This is search microservice which is a part Restaurants application. This service provides provision to search restaurants by name, cuisine, location, budget, rating, by item in the menu of restaurant or by restaurant ID.

This application is written using Spring boot and JPA. The database used is MySQL.

The api documentation is done using swagger, whose configuration can be found in: **com.eatza.orderingservice.config.SwaggerConfiguration**

The APIs are secured using JWT token, whose configuration can be found in:
**com.eatza.orderingservice.config.JwtFilter**

Since Consumer service for restaurants is not in scope. One mock user is hardcoded in property files and JWT is generated at login if credentials match that user.(Default credentials hardcoded are username: user and password:password)

### Prerequisites and setup:


* Java 8
* Maven


### How to run
Build the application with `mvn clean install`. By default `local` profile is activated, to activate any other profile pass spring.profiles.active as run time variable while running jar. For instance, if you want to run jar in dev profile, run it using :
`java -jar -Dspring.profiles.active=dev < jar-name > `


### Code structure

All the rest controllers are stored in `com.eatza.restaurantsearch.controller`  package which will respond to any request coming to REST endpoints.

All the services are stored in `com.eatza.restaurantsearch.service`  package which will be contacted by controllers and will manipulcate the data as required and will contact JPA layer directly.

All the Models are stored in 
`com.eatza.restaurantsearch.model` package which are mapped directly to db schemas using JPA.

All the data transfer objects are stored in 
`com.eatza.restaurantsearch.dto` package which accepts request and provide response in particular structure.

All the configuration related stuff such as swagger config, jwt config and rest template config are stored in 
`com.eatza.restaurantsearch.config` package

Exceptions are handled using `controlleradvice` and are kept in `com.eatza.restaurantsearch.exception` package

All the dao classes and relations are stored in `com.eatza.restaurantsearch.repository` package


### REST APIs 

* You can directly send the api requests through swagger which is integrated with this spring boot application
  Access the link http://localhost:8080/swagger-ui.html after running the application.

_Some information about the rest APIs_

### Login Request

* Login with username - user and password - password hardcoded in application properties to get JWT token which has to be sent as header with all other API requests.

Login api is present in jwt-authentication-controller, you can test it by passing username and password in post request body as follows:

```
curl -X POST "http://localhost:8080/login" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"password\": \"password\", \"username\": \"user\"}"
```

It will return you back a JWT token. Append Bearer in front of that token and pass it as `authorization header` in every request. For example if this request gives you back `abc` pass authorization header as `bearer abc` in every request.

### addItemsToRestaurantMenu and addRestaurant APIs

These APIs were kept in this service for ease of adding the data, and this can be removed since only search APIs should be present.

### getItemsByRestaurantId in restaurant-controller

This api requires path variable `restaurantid` to be passed along with authorization bearer token as header and it will return back menu items in given restaurant if found in DB.

This API also required pagenumber and pagesize as request params for pagination, if you pass pagenumber as 1 and pagesize as 10. it is going to show you 1st 10 results starting from 1st page.

```
curl -X GET "http://localhost:8080/restaurant/items/<restaurantid>?pagenumber=<pagenumber>&pagesize=<pagesize>" -H "accept: */*" -H "authorization: <Bearer token>"
```

### getAllRestaurants in restaurant-controller

This api requires authorization bearer token as header and it will return back all the restaurants present in DB (paginated)

This API also required pagenumber and pagesize as request params for pagination, if you pass pagenumber as 1 and pagesize as 10. it is going to show you 1st 10 results starting from 1st page.

```
curl -X GET "http://localhost:8080/restaurants?pagenumber=1&pagesize=10" -H "accept: */*" -H "authorization: <Bearer token>"
```

### getRestaurantsByBudget in restaurant-controller

This api requires path variable `budget` to be passed along with authorization bearer token as header and it will return back restaurant with given budget or lesser budget ( Since a customer will be interested in good restaurant in given budget or maybe lesser budget) if present in DB.

This API also requires pagenumber and pagesize as request params for pagination, if you pass pagenumber as 1 and pagesize as 10. it is going to show you 1st 10 results starting from 1st page.

```
curl -X GET "http://localhost:8080/restaurants/budget/< total budget >?pagenumber=1&pagesize=10" -H "accept: */*" -H "authorization: <Bearer Token>"
```

### getRestaurantsByName in restaurant-controller

This api requires path variable `name` to be passed along with authorization bearer token as header and it will return back restaurant with given name or name containing given strings, For example if you pass `jp` as given name in path variable, it will also return the restaurants named `jp nagar`

This API also requires pagenumber and pagesize as request params for pagination, if you pass pagenumber as 1 and pagesize as 10. it is going to show you 1st 10 results starting from 1st page.

```
curl -X GET "http://localhost:8080/restaurants/name/< restaurant name >?pagenumber=1&pagesize=10" -H "accept: */*" -H "authorization: <Bearer Token>"
```

### getRestaurantsByRating in restaurant-controller

This api requires path variable `budget` to be passed along with authorization bearer token as header and it will return back restaurant with given rating or higher rating ( Since a customer will be interested in good restaurant with the rating provided or above) if present in DB.

This API also required pagenumber and pagesize as request params for pagination, if you pass pagenumber as 1 and pagesize as 10. it is going to show you 1st 10 results starting from 1st page.

```
curl -X GET "http://localhost:8080/restaurants/rating/< restaurant rating >?pagenumber=1&pagesize=10" -H "accept: */*" -H "authorization: <Bearer Token>"
```


### getRestaurantsByLocationCuisine in restaurant-controller

This api will take into input path variables - `location` and `cuisine`, This API was created since a user might be interested in eating lets say `thai in jp nagar` So he should get a list of good restaurants for given cuisine in reuired location.

This API will first search for restaurants containing both cuisine and location in db. If no results are found, this API will take into account restaurants which are in the given location or are thai restaurants. Please look into the service layer for more details.

This API also required pagenumber and pagesize as request params for pagination, if you pass pagenumber as 1 and pagesize as 10. it is going to show you 1st 10 results starting from 1st page.

```
curl -X GET "http://localhost:8080/restaurants/location/< location >/cuisine/< cuisine >?pagenumber=1&pagesize=10" -H "accept: */*" -H "authorization: <bearer token>"
```



### getRestaurantsByLocationName in restaurant-controller

This api will take into input path variables - `location` and `name`, This API was created since a user might be interested in going to a restaurant lets say `Dominos in jp nagar` So he should get a list of good restaurants for given name in reuired location.

This API will first search for restaurants containing both name and location in db. If no results are found, this API will take into account restaurants which are in the given location or are named accordingly. Please look into the service layer for more details.

This API also required pagenumber and pagesize as request params for pagination, if you pass pagenumber as 1 and pagesize as 10. it is going to show you 1st 10 results starting from 1st page.

```
curl -X GET "http://localhost:8080/restaurants/name/<restaurant name>/location/< location >?pagenumber=1&pagesize=10" -H "accept: */*" -H "authorization: <bearer token>"
```

### getItemById in menu-item-controller

This api requires path variable `id` to be passed along with authorization bearer token as header and it will return back menu-item with given ID if found in DB.

```
curl -X GET "http://localhost:8080/item/id/<id>" -H "accept: */*" -H "authorization: <Bearer token>"
```
