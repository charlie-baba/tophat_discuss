[![Codacy Badge](https://app.codacy.com/project/badge/Grade/efeaf57c5b49494793da8ea36ea958c7)](https://www.codacy.com/gh/charlie-baba/tophat_discuss/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=charlie-baba/tophat_discuss&amp;utm_campaign=Badge_Grade)

# Discussion-API

The Discussion-API is an API for a professor/student to start a discussion and anyone can respond to 
a question with their comments. Students can also respond to each other’s comments.  
This article will walk you through the steps to set up and run the Discussion-API.

## Prerequisites

* Installed version of the JDK 17 (or higher) and Maven.
* Installed version of Git.
* Postgres server installed on your device.

## How To run Discussion-API

Follow each step to build and deploy the app

### Database Configuration

You need to have postgres server already installed on your device.

    - Create a database with name "tophat_discuss"
    - Server name is "postgres"
    - Server passowrd is "password"
You can also change the properties in the _application.properties_ file.    
The database schema would be generated once you run the app.

Running the app from and IDE like intellij or Eclipse

    - clone the repo from https://github.com/charlie-baba/tophat_discuss.git
    - add the project to intellij or any java IDE
    - make sure to resolve all meaven dependencies (already included in the project).
    - run the DiscussApiApplication class

    INFO: Initializing ProtocolHandler ["http://localhost:8080"] 
    - The default port is 8080, it can be modified from the application.properties file (key = "server.port") 

Alternatively,
To build your application simply run:

    $ mvn package
    $ java -jar target/dependency/discuss-api.jar target/*.jar

That's it. Your application should start up on port 8080.

## URL structure and API schema

## User endpoints

The User endpoints are listed below

Create users

    POST | http://localhost:8080/api/v1/user
    Request
    {
        "firstName": "John",
        "lastName": "Doe",
        "username": "doe_001",
        "phoneNumber": "6471234747",
        "userType": "Professor"
    }

    Response
    {
        "id": 1,
        "dateCreated": "2022-12-19T04:19:45.233+00:00",
        "firstName": "John",
        "lastName": "Doe",
        "username": "doe_001",
        "phoneNumber": "6471234747",
        "userType": "Professor"
    }
Note: UserType can be either _"Professor"_ or _"Student"_ ONLY

Fetch All Users

    GET | http://localhost:8080/api/v1/user/getAll
    Response
    [
        {
            "id": 1,
            "dateCreated": "2022-12-18T22:40:40.120+00:00",
            "firstName": "Ted",
            "lastName": "Mosby",
            "username": "ted_06",
            "phoneNumber": "4162221234",
            "userType": "Student"
        },
        {
            "id": 2,
            "dateCreated": "2022-12-18T22:41:18.156+00:00",
            "firstName": "John",
            "lastName": "Doe",
            "username": "doe_001",
            "phoneNumber": "6471234747",
            "userType": "Professor"
        },
        {
            "id": 3,
            "dateCreated": "2022-12-18T22:45:42.167+00:00",
            "firstName": "Charles",
            "lastName": "Okonkwo",
            "username": "co_001",
            "phoneNumber": "348857302",
            "userType": "Student"
        }
    ]

Fetch specific User

    GET | http://localhost:8080/api/v1/user/1
    Response
    {
        "id": 1,
        "dateCreated": "2022-12-18T22:40:40.120+00:00",
        "firstName": "Ted",
        "lastName": "Mosby",
        "username": "ted_06",
        "phoneNumber": "4162221234",
        "userType": "Student"
    }

Delete user

    DELETE | http://localhost:8080/api/v1/user/{id}
    Response
    Http 200 OK

## Discussion endpoints

The discussion endpoints are listed below

Fetch All Discussions

    GET | http://localhost:8080/api/v1/discussion/getAll
    Response
    [
        {
            "id": 2,
            "question": "What are we doing today?",
            "authorId": 2,
            "dateCreated": "2022-12-18T22:42:38.646+00:00",
            "commentsCount": 0,
            "comments": []
        },
        {
            "id": 1,
            "question": "How is your day going so far?",
            "authorId": 1,
            "dateCreated": "2022-12-18T22:41:59.995+00:00",
            "commentsCount": 3,
            "comments": []
        }
    ]

Fetch specific Discussion

    GET | http://localhost:8080/api/v1/discussion/1
    Response
    {
        "id": 1,
        "question": "How is your day going so far?",
        "authorId": 1,
        "dateCreated": "2022-12-18T22:41:59.995+00:00",
        "commentsCount": 3,
        "comments": [
            {
                "id": 1,
                "comment": "it is going great, Thanks!",
                "authorId": 2,
                "discussionId": 1,
                "parentCommentId": null,
                "dateCreated": "2022-12-18T22:47:07.048+00:00"
            },
            {
                "id": 3,
                "comment": "Unfortunately, I had a bad day!",
                "authorId": 3,
                "discussionId": 1,
                "parentCommentId": null,
                "dateCreated": "2022-12-18T23:07:02.800+00:00"
            },
            {
                "id": 4,
                "comment": "Oh no! Sorry to hear that. What happened?",
                "authorId": 2,
                "discussionId": 1,
                "parentCommentId": 1,
                "dateCreated": "2022-12-18T23:11:41.114+00:00"
            }
        ]
    }

Create discussion

    POST | http://localhost:8080/api/v1/discussion
    Request
    {
        "question": "How is your day going so far?",
        "authorId": 1
    }

    Response
    {
        "id": 1,
        "question": "How is your day going so far?",
        "authorId": 1,
        "dateCreated": "2022-12-18T02:55:01.900+00:00",
        "commentsCount": 0,
        "comments": []
    }

Edit discussion

    PUT | http://localhost:8080/api/v1/discussion/1
    Request
    {
        "question": "How is your week going so far?",
        "authorId": 1
    }
    
    Response
    {
        "id": 1,
        "question": "How is your week going so far?",
        "authorId": 1,
        "dateCreated": "2022-12-18T02:55:01.900+00:00",
        "commentsCount": 0,
        "comments": []
    }

Delete discussion

    DELETE | http://localhost:8080/api/v1/discussion/1
    Response
    Http 200 OK

## Comment endpoints

The comment endpoints are listed below

Create comment

    POST | http://localhost:8080/api/v1/comment
    Request
    {
        "comment": "Oh no! Sorry to hear that. What happened?",
        "authorId": 2,
        "discussionId": 1,
        "parentCommentId": 1
    }

    Response
    {
        "id": 4,
        "comment": "Oh no! Sorry to hear that. What happened?",
        "authorId": 2,
        "discussionId": 1,
        "parentCommentId": 1,
        "dateCreated": "2022-12-18T23:11:41.114+00:00"
    }

Delete comment

    DELETE | http://localhost:8080/api/v1/comment/1
    Response
    Http 200 OK