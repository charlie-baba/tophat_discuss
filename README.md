[![Codacy Badge](https://app.codacy.com/project/badge/Grade/efeaf57c5b49494793da8ea36ea958c7)](https://www.codacy.com/gh/charlie-baba/tophat_discuss/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=charlie-baba/tophat_discuss&amp;utm_campaign=Badge_Grade)

# Discussion-API

The Discussion-API is an API for a professor/student to start a discussion and anyone can respond to 
a question with their comments. Students can also respond to each other’s comments.  
This article will walk you through the steps to set up and run the Discussion-API.

## Prerequisites

* Installed version of the JDK 17 (or higher) and Maven.
* Installed version of Git.
* Postgres server installed on your device.

### How To run Discussion-API

Follow each step to build and deploy the app
Running the app from and IDE like intellij or Eclipse

    - clone the repo from https://github.com/charlie-baba/tophat_discuss.git
    - add the project to intellij or any java IDE
    - make sure to resolve all meaven dependencies (already included in the project).
    - create a database in your postgres server name 'tophat_discuss'.
    - run the DiscussApiApplication class

    INFO: Initializing ProtocolHandler ["http://localhost:8080"] 
    - The default port is 8080, it can be modified from the application.properties file (key = "server.port") 

Alternatively,
To build your application simply run:

    $ mvn package
    $ java -jar target/dependency/booking-api.jar target/*.jar

That's it. Your application should start up on port 8080.

## Swagger

### Html Docs
The swagger endpoint is "http://localhost:8080/swagger-ui/index.html", assuming that your port is 8080.

### JSON Docs
The swagger endpoint is "http://localhost:8080/v2/api-docs", for the demo project


## Classes endpoints

The classes endpoints are listed below

Fetch All (both active and inactive) Classes

    GET | http://localhost:[8080]/api/classes/getAll/{page}

Fetch Active/Ongoing Classes

    GET | http://localhost:[8080]/api/classes/getActive/{page}

Create classes

    POST | http://localhost:[8080]/api/classes
     {
         "name": "Yoga",
         "startDate": "2021-05-17T13:53:46.242+00:00",
         "endDate": "2021-05-27T13:53:46.242+00:00",
         "capacity": 20
     }

Update classes

    PUT | http://localhost:[8080]/api/booking/3
     {
         "name": "Yoga",
         "startDate": "2021-05-17T13:53:46.242+00:00",
         "endDate": "2021-05-27T13:53:46.242+00:00",
         "capacity": 10
     }

Delete classes

    DELETE | http://localhost:[8080]/api/booking/1

## Booking endpoints

The booking endpoints are listed below

Fetch Bookings

    GET | http://localhost:[8080]/api/bookings/{page}

Create booking

    POST | http://localhost:[8080]/api/booking
    {
        "className": "Yoga",
        "firstName": "Obiefuna",
        "lastName": "Okonkwo",
        "email": ""
    }