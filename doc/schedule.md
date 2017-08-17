# API Documentation for End Point "/schedule"

<a name="table-of-content"></a>
## Table of Content

- [Show events](#schedule)
    - [URL](#schedule-url)
    - [Method](#schedule-method)
    - [URL Params](#schedule-url-params)
    - [Data Params](#schedule-data-params)
    - [Success Response](#schedule-success-response)
    - [Error Response](#schedule-error-response)
- [Show event](#schedule)
    - [URL](#schedule-url)
    - [Method](#schedule-method)
    - [URL Params](#schedule-url-params)
    - [Data Params](#schedule-data-params)
    - [Success Response](#schedule-success-response)
    - [Error Response](#schedule-error-response)
- [Add event](#add-schedule)
    - [URL](#add-schedule-url)
    - [Method](#add-schedule-method)
    - [URL Params](#add-schedule-url-params)
    - [Data Params](#add-schedule-data-params)
    - [Data Type](#add-schedule-data-type)
    - [Success Response](#add-schedule-success-response)
    - [Error Response](#add-schedule-error-response)

<a name="schedule"></a>
## Show schedule

<a name="schedule-url"></a>
### URL :
/events

<a name="schedule-method"></a>
### Method :
GET

<a name="schedule-url-params"></a>
### URL Params :
**Optional** `groupid=[integer], start=[iso-date], end=[iso-date]`

Example: `groupid=2, start=2017-01-01, end=2018-01-01`

<a name="schedule-data-params"></a>
### Data Params :
None

<a name="schedule-success-response"></a>
### Success Response:
**Code:** 200 OK

  **Content:**
```
  [
      {
          "eventId": 1,
          "dateTime": "2017-07-24 18:00",
          "duration": 120,
          "links": [
              {
                  "rel": "self",
                  "href": "http://localhost:8080/events/1"
              },
              {
                  "rel": "event",
                  "href": "http://localhost:8080/events/1"
              },
              {
                  "rel": "group",
                  "href": "http://localhost:8080/groups/1"
              },
              {
                  "rel": "eventType",
                  "href": "http://localhost:8080/eventTypes/2"
              },
              {
                  "rel": "room",
                  "href": "http://localhost:8080/rooms/1"
              }
          ]
      },
      {
          "eventId": 2,
          "dateTime": "2017-07-25 18:00",
          "duration": 120,
          "links": [
              {
                  "rel": "self",
                  "href": "http://localhost:8080/events/2"
              },
              {
                  "rel": "event",
                  "href": "http://localhost:8080/events/2"
              },
              {
                  "rel": "group",
                  "href": "http://localhost:8080/groups/1"
              },
              {
                  "rel": "eventType",
                  "href": "http://localhost:8080/eventTypes/1"
              },
              {
                  "rel": "room",
                  "href": "http://localhost:8080/rooms/1"
              }
          ]
      } etc
    ]
```

  **Content:** (with Optional parameter groupid=2)

    [
        {
            "eventId": 3,
            "dateTime": "2017-07-24 18:00",
            "duration": 120,
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/events/3"
                },
                {
                    "rel": "event",
                    "href": "http://localhost:8080/events/3"
                },
                {
                    "rel": "group",
                    "href": "http://localhost:8080/groups/2"
                },
                {
                    "rel": "eventType",
                    "href": "http://localhost:8080/eventTypes/2"
                },
                {
                    "rel": "room",
                    "href": "http://localhost:8080/rooms/1"
                }
            ]
        },
        {
            "eventId": 4,
            "dateTime": "2017-07-25 18:00",
            "duration": 120,
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/events/4"
                },
                {
                    "rel": "event",
                    "href": "http://localhost:8080/events/4"
                },
                {
                    "rel": "group",
                    "href": "http://localhost:8080/groups/2"
                },
                {
                    "rel": "eventType",
                    "href": "http://localhost:8080/eventTypes/1"
                },
                {
                    "rel": "room",
                    "href": "http://localhost:8080/rooms/1"
                }
            ]
        }
    ]

<a name="schedule-error-response"></a>
### Error Response :

**Code:** 400 Bad Request

**Content:**

    {
        "message": ""Bad request"
    }

**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/schedule"
    }

[Table of content](#table-of-content)

<a name="schedule"></a>
## Show event

<a name="schedule-url"></a>
### URL :
/schedule/{id}

<a name="schedule-method"></a>
### Method :
GET

<a name="schedule-url-params"></a>
### URL Params :
None

<a name="schedule-data-params"></a>
### Data Params :
None

<a name="schedule-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    {
        "eventId": 1,
        "dateTime": "2017-07-24 18:00",
        "duration": 120,
        "_links": {
            "self": {
                "href": "http://localhost:8080/events/1"
            },
            "event": {
                "href": "http://localhost:8080/events/1"
            },
            "group": {
                "href": "http://localhost:8080/groups/1"
            },
            "eventType": {
                "href": "http://localhost:8080/eventTypes/2"
            },
            "room": {
                "href": "http://localhost:8080/rooms/1"
            }
        }
    }

### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/schedule/1"
    }

**Code:** 404 Not Found

**Content:**

    {
        "message": "Not Found"
    }

[Table of content](#table-of-content)

<a name="add-schedule"></a>
## Add event

<a name="add-schedule-url"></a>
### URL :
events/

<a name="add-schedule-method"></a>
### Method :
POST

<a name="add-schedule-url-params"></a>
### URL Params :
None

<a name="add-schedule-data-params"></a>
### Data Params :
**Required:**

     [
            "room":{
                "id":integer
            },
            "eventType":{
                "id":integer
            },
            "duration":integer [minutes],
            "dateTime":"yyyy-MM-dd hh:mm"
         }
     ]

Example:

    [
    	{
    		"groups":{
    			"id":1
    		},
    		"room":{
    			"id":1
    		},
    		"eventType":{
    			"id":1
    		},
    		"duration":120,
    		"dateTime":"2017-09-14 15:30"
    	}
    ]

<a name="add-schedule-data-type"></a>
### Data type :
application/json

<a name="add-schedule-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    {
        "succeed": [
            {
                "eventId": 13,
                "dateTime": "2017-09-14 15:30",
                "duration": 120,
                "links": [
                    {
                        "rel": "self",
                        "href": "http://localhost:8080/events/13"
                    },
                    {
                        "rel": "event",
                        "href": "http://localhost:8080/events/13"
                    },
                    {
                        "rel": "group",
                        "href": "http://localhost:8080/groups/1"
                    },
                    {
                        "rel": "eventType",
                        "href": "http://localhost:8080/eventTypes/1"
                    },
                    {
                        "rel": "room",
                        "href": "http://localhost:8080/rooms/1"
                    }
                ]
            }
        ],
        "invalid": {}
    }

<a name="add-schedule-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/groups"
    }

**Code:** 400 Bad Request

**Content:** None


[Table of content](#table-of-content)
