Here are my answers to the questions and the code for the ski lesson application.
<details>
    <summary>Endpoints, and responses </summary>


GET localhost:7070/api/skilesson
```
[
  {
    "id": 1,
    "startTime": "08:00",
    "endTime": "10:30",
    "startPosition": "47.1287, 10.2641",
    "name": "Advanced Alpine Skiing",
    "price": 30.0,
    "level": "ADVANCED"
  },
  {
    "id": 2,
    "startTime": "09:30",
    "endTime": "12:00",
    "startPosition": "47.4465, 12.3929",
    "name": "Intermediate Slope Training",
    "price": 35.0,
    "level": "INTERMEDIATE"
  },
  {
    "id": 3,
    "startTime": "10:00",
    "endTime": "12:30",
    "startPosition": "47.0122, 10.2920",
    "name": "Beginner Snowboarding",
    "price": 40.0,
    "level": "BEGINNER"
  },
  {
    "id": 4,
    "startTime": "11:00",
    "endTime": "13:30",
    "startPosition": "46.9696, 11.0106",
    "name": "Advanced Freestyle Skiing",
    "price": 25.0,
    "level": "ADVANCED"
  },
  {
    "id": 5,
    "startTime": "13:00",
    "endTime": "15:30",
    "startPosition": "47.3925, 12.6412",
    "name": "Beginner Skiing Basics",
    "price": 45.0,
    "level": "BEGINNER"
  },
  {
    "id": 6,
    "startTime": "12:00",
    "endTime": "14:30",
    "startPosition": "47.3256, 12.7945",
    "name": "Intermediate Cross-Country",
    "price": 50.0,
    "level": "INTERMEDIATE"
  },
  {
    "id": 7,
    "startTime": "14:00",
    "endTime": "16:30",
    "startPosition": "47.2510, 13.5567",
    "name": "Advanced Mogul Skiing",
    "price": 55.0,
    "level": "ADVANCED"
  },
  {
    "id": 8,
    "startTime": "16:00",
    "endTime": "18:30",
    "startPosition": "47.3930, 13.6893",
    "name": "Beginner Slope Navigation",
    "price": 20.0,
    "level": "BEGINNER"
  },
  {
    "id": 9,
    "startTime": "15:00",
    "endTime": "17:30",
    "startPosition": "47.1167, 13.1333",
    "name": "Intermediate Snowshoeing",
    "price": 60.0,
    "level": "INTERMEDIATE"
  },
  {
    "id": 10,
    "startTime": "19:00",
    "endTime": "21:30",
    "startPosition": "47.2087, 10.1419",
    "name": "Beginner Snowshoe Hike",
    "price": 75.0,
    "level": "BEGINNER"
  },
  {
    "id": 11,
    "startTime": "17:00",
    "endTime": "19:30",
    "startPosition": "47.3306, 11.1876",
    "name": "Advanced Backcountry Skiing",
    "price": 70.0,
    "level": "ADVANCED"
  },
  {
    "id": 12,
    "startTime": "18:00",
    "endTime": "20:30",
    "startPosition": "47.2137, 11.0231",
    "name": "Intermediate Ski Touring",
    "price": 65.0,
    "level": "INTERMEDIATE"
  }
]
```


###
GET localhost:7070/api/skilesson/13
````
{
  "id": 12,
  "startTime": "18:00",
  "endTime": "20:30",
  "startPosition": "47.2137, 11.0231",
  "name": "Intermediate Ski Touring",
  "price": 65.0,
  "level": "INTERMEDIATE",
  "instructor": {
    "firstName": "Peter",
    "lastName": "Parker",
    "email": "PP@example.com",
    "phone": "+444444444",
    "yearsOfExp": 6
  }
}
````
###
POST localhost:7070/api/skilesson
Content-Type: application/json

{
"startTime": "09:00",
"endTime": "12:00",
"startPosition": "47.3455, 12.7965",
"name": "Beginner Avalanche Survival",
"price": 45.50,
"level": "BEGINNER"
}
````
{
  "id": 13,
  "startTime": "09:00",
  "endTime": "12:00",
  "startPosition": "47.3455, 12.7965",
  "name": "Beginner Avalanche Survival",
  "price": 45.50,
  "level": "BEGINNER"
}
````
###
PUT localhost:7070/api/skilesson/13/instructor/1


###
PATCH localhost:7070/api/skilesson/13
Content-Type: application/json

{
  "startTime": "13:00",
  "endTime": "16:00"
}
````
{
  "id": 13,
  "startTime": "13:00",
  "endTime": "16:00",
  "startPosition": "47.3455, 12.7965",
  "name": "Beginner Avalanche Survival",
  "price": 45.5,
  "level": "BEGINNER",
  "instructor": {
    "firstName": "Clark",
    "lastName": "Kent",
    "email": "CK@example.com",
    "phone": "+111111111",
    "yearsOfExp": 5
  }
}
````

###
GET localhost:7070/api/skilesson/level?level=BEGINNER
````
[
  {
    "id": 3,
    "startTime": "10:00",
    "endTime": "12:30",
    "startPosition": "47.0122, 10.2920",
    "name": "Beginner Snowboarding",
    "price": 40.0,
    "level": "BEGINNER",
    "instructor": {
      "firstName": "Clark",
      "lastName": "Kent",
      "email": "CK@example.com",
      "phone": "+111111111",
      "yearsOfExp": 5
    }
  },
  {
    "id": 5,
    "startTime": "13:00",
    "endTime": "15:30",
    "startPosition": "47.3925, 12.6412",
    "name": "Beginner Skiing Basics",
    "price": 45.0,
    "level": "BEGINNER",
    "instructor": {
      "firstName": "Bruce",
      "lastName": "Wayne",
      "email": "BW@example.com",
      "phone": "+222222222",
      "yearsOfExp": 7
    }
  },
  {
    "id": 8,
    "startTime": "16:00",
    "endTime": "18:30",
    "startPosition": "47.3930, 13.6893",
    "name": "Beginner Slope Navigation",
    "price": 20.0,
    "level": "BEGINNER",
    "instructor": {
      "firstName": "Tony",
      "lastName": "Stark",
      "email": "TS@example.com",
      "phone": "+333333333",
      "yearsOfExp": 4
    }
  },
  {
    "id": 10,
    "startTime": "19:00",
    "endTime": "21:30",
    "startPosition": "47.2087, 10.1419",
    "name": "Beginner Snowshoe Hike",
    "price": 75.0,
    "level": "BEGINNER",
    "instructor": {
      "firstName": "Peter",
      "lastName": "Parker",
      "email": "PP@example.com",
      "phone": "+444444444",
      "yearsOfExp": 6
    }
  },
  {
    "id": 13,
    "startTime": "13:00",
    "endTime": "16:00",
    "startPosition": "47.3455, 12.7965",
    "name": "Beginner Avalanche Survival",
    "price": 45.5,
    "level": "BEGINNER",
    "instructor": {
      "firstName": "Clark",
      "lastName": "Kent",
      "email": "CK@example.com",
      "phone": "+111111111",
      "yearsOfExp": 5
    }
  }
]
````

###
GET localhost:7070/api/skilesson/instructor/1/overview
````
[
  {
    "id": 1,
    "startTime": "08:00",
    "endTime": "10:30",
    "startPosition": "47.1287, 10.2641",
    "name": "Advanced Alpine Skiing",
    "price": 30.0,
    "level": "ADVANCED",
    "instructor": {
      "firstName": "Clark",
      "lastName": "Kent",
      "email": "CK@example.com",
      "phone": "+111111111",
      "yearsOfExp": 5
    }
  },
  {
    "id": 2,
    "startTime": "09:30",
    "endTime": "12:00",
    "startPosition": "47.4465, 12.3929",
    "name": "Intermediate Slope Training",
    "price": 35.0,
    "level": "INTERMEDIATE",
    "instructor": {
      "firstName": "Clark",
      "lastName": "Kent",
      "email": "CK@example.com",
      "phone": "+111111111",
      "yearsOfExp": 5
    }
  },
  {
    "id": 3,
    "startTime": "10:00",
    "endTime": "12:30",
    "startPosition": "47.0122, 10.2920",
    "name": "Beginner Snowboarding",
    "price": 40.0,
    "level": "BEGINNER",
    "instructor": {
      "firstName": "Clark",
      "lastName": "Kent",
      "email": "CK@example.com",
      "phone": "+111111111",
      "yearsOfExp": 5
    }
  },
  {
    "id": 13,
    "startTime": "13:00",
    "endTime": "16:00",
    "startPosition": "47.3455, 12.7965",
    "name": "Beginner Avalanche Survival",
    "price": 45.5,
    "level": "BEGINNER",
    "instructor": {
      "firstName": "Clark",
      "lastName": "Kent",
      "email": "CK@example.com",
      "phone": "+111111111",
      "yearsOfExp": 5
    }
  }
]
````

###
GET localhost:7070/api/skilesson/stats
````
[
  {
    "totalTimeMinutes": 630,
    "instructor id": 1,
    "total Price": 150.5,
    "instructor name": "Clark Kent"
  },
  {
    "totalTimeMinutes": 450,
    "instructor id": 2,
    "total Price": 120.0,
    "instructor name": "Bruce Wayne"
  },
  {
    "totalTimeMinutes": 450,
    "instructor id": 3,
    "total Price": 135.0,
    "instructor name": "Tony Stark"
  },
  {
    "totalTimeMinutes": 450,
    "instructor id": 4,
    "total Price": 210.0,
    "instructor name": "Peter Parker"
  }
]
````

###
DELETE localhost:7070/api/skilesson/13
````
<Response body is empty>
````
###
POST localhost:7070/api/skilesson/populate
```
<Response body is empty>
```
</details>

#### Theoretical question answers
335. : You recommend using PUT instead of Post, because PUT will replace the entire resource at the specified URI, while POST will create a new resource. In this case, since you are updating an existing ski lesson, PUT is the more appropriate choice.

#### Security
When my test would start to fail because of the Unauthorized response, i would have to login with an appropriate role (admin would be easier for testing purposes) and then once logged in, i should be able to access the endpoints. The login endpoint is localhost:7070/api/auth/login. The response will contain a JWT token, which should be used in the Authorization header for subsequent requests.


#### Database
<details>
    <summary>Database setup</summary>
Remember to add a config.properties file in the resources directory. The config.properties file should contain the following properties:

````
DB_NAME=
DB_USERNAME=
DB_PASSWORD=
SECRET_KEY=
ISSUER=
TOKEN_EXPIRE_TIME=1800000
````

The DB_NAME, DB_USERNAME, DB_PASSWORD, ISSUER, and TOKEN_EXPIRE_TIME properties should be filled in with the appropriate values. The SECRET_KEY property should be a minimum of 32 characters long.

</details>