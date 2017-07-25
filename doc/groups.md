# API Documentation for End Point "/groups"

<a name="table-of-content"></a>
## Table of Content

- [Show all groups](#all-groups)
    - [URL](#all-groups-url)
    - [Method](#all-groups-method)
    - [URL Params](#all-groups-url-params)
    - [Data Params](#all-groups-data-params)
    - [Success Response](#all-groups-success-response)
    - [Error Response](#all-groups-error-response)
- [Show the groups, that belong to the currently authenticated teacher](#teacher-groups)
    - [URL](#teacher-groups-url)
    - [Method](#teacher-groups-method)
    - [URL Params](#teacher-groups-url-params)
    - [Data Params](#teacher-groups-data-params)
    - [Success Response](#teacher-groups-success-response)
    - [Error Response](#teacher-groups-error-response)
- [Show the groups, that belong to the location of the currently authenticated user](#current-user-location-groups)
    - [URL](#current-user-location-groups-url)
    - [Method](#current-user-location-groups-method)
    - [URL Params](#current-user-location-groups-url-params)
    - [Data Params](#current-user-location-groups-data-params)
    - [Success Response](#current-user-location-groups-success-response)
    - [Error Response](#current-user-location-groups-error-response)
- [Show the groups, with filter](#filter-groups)
    - [URL](#filter-groups-url)
    - [Method](#filter-groups-method)
    - [URL Params](#filter-groups-url-params)
    - [Data Params](#filter-groups-data-params)
    - [Success Response](#filter-groups-success-response)
    - [Error Response](#filter-groups-error-response)
- [Add group](#add-group)
    - [URL](#add-group-url)
    - [Method](#add-group-method)
    - [URL Params](#add-group-url-params)
    - [Data Params](#add-group-data-params)
    - [Data Type](#add-group-data-type)
    - [Success Response](#add-group-success-response)
    - [Error Response](#add-group-error-response)
- [Delete group](#delete-group)
    - [URL](#delete-group-url)
    - [Method](#delete-group-method)
    - [URL Params](#delete-group-url-params)
    - [Data Params](#delete-group-data-params)
    - [Data Type](#delete-group-data-type)
    - [Success Response](#delete-group-success-response)
    - [Error Response](#delete-group-error-response)

<a name="all-groups"></a>
## Show all groups

<a name="all-groups-url"></a>
### URL :
/groups

<a name="all-groups-method"></a>
### Method :
GET

<a name="all-groups-url-paramsd"></a>
### URL Params :
None

<a name="all-groups-data-params"></a>
### Data Params :
None

<a name="all-groups-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:** 

    [
        {
            "id": 1,
            "name": "DP-115",
            "teachers":
            [
                {
                    "id": 1,
                    "firstName": "Oleg",
                    "lastName": "Shvets",
                    "role":
                    {
                        "id": 3,
                        "name": "teacher",
                        "roleCategory":
                        {
                            "id": 2,
                            "name":" itacademy"
                        }
                    },
                    "nickName": "OlegShvets",
                    "image": null,
                    "location":
                    {
                        "id": 1,
                        "name": "Dnipro"
                    }
                }
            ],
            "location":
            {
                "id": 1,
                "name": "Dnipro"
            },
            "startDate": 
            {
                "year": 2017,
                "month": "APRIL",
                "era": "CE",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate":
            {
                "year": 2017,
                "month": "AUGUST",
                "era": "CE",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status":
            {
                "id": 4,
                "name": "in-process",
                "statusCategory":
                {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization":
            {
                "id": 7,
                "name": "JAVA"
            },
            "experts":
            [
                "Sergey"
            ],
            "budgetOwner": "SOFTSERVE"
        }
    ]

<a name="all-groups-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500673874234,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/groups/"
    }

[Table of content](#table-of-content)

<a name="teacher-groups"></a>
## Show the groups, that belong to the currently authenticated teacher

<a name="teacher-groups-url"></a>
### URL :
/groups/my

<a name="teacher-groups-method"></a>
### Method :
GET

<a name="teacher-groups-url-params"></a>
### URL Params :
None

<a name="teacher-groups-data-params"></a>
### Data Params :
None

<a name="teacher-groups-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    [
        {
            "id": 1,
            "name": "DP-115",
            "teachers":
            [
                {
                    "id": 1,
                    "firstName": "Oleg",
                    "lastName": "Shvets",
                    "role":
                    {
                        "id": 3,
                        "name": "teacher",
                        "roleCategory":
                        {
                            "id": 2,
                            "name":" itacademy"
                        }
                    },
                    "nickName": "OlegShvets",
                    "image": null,
                    "location":
                    {
                        "id": 1,
                        "name": "Dnipro"
                    }
                }
            ],
            "location":
            {
                "id": 1,
                "name": "Dnipro"
            },
            "startDate": 
            {
                "year": 2017,
                "month": "APRIL",
                "era": "CE",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate":
            {
                "year": 2017,
                "month": "AUGUST",
                "era": "CE",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status":
            {
                "id": 4,
                "name": "in-process",
                "statusCategory":
                {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization":
            {
                "id": 7,
                "name": "JAVA"
            },
            "experts":
            [
                "Sergey"
            ],
            "budgetOwner": "SOFTSERVE"
        }
    ]

<a name="teacher-groups-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized
 
**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/groups/my"
    }

**Code:** 404 Not Found

**Content:**

    {
        "message": "Not Found"
    }

[Table of content](#table-of-content)

<a name="current-user-location-groups"></a>
## Show the groups, that belong to the location of the currently authenticated user

<a name="current-user-location-groups-url"></a>
### URL :
/groups/mylocation

<a name="current-user-location-groups-method"></a>
### Method :
GET

<a name="current-user-location-groups-url-params"></a>
### URL Params :
None

<a name="current-user-location-groups-data-params"></a>
### Data Params :
None

<a name="current-user-location-groups-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    [
        {
            "id": 1,
            "name": "DP-115",
            "teachers":
            [
                {
                    "id": 1,
                    "firstName": "Oleg",
                    "lastName": "Shvets",
                    "role":
                    {
                        "id": 3,
                        "name": "teacher",
                        "roleCategory":
                        {
                            "id": 2,
                            "name":" itacademy"
                        }
                    },
                    "nickName": "OlegShvets",
                    "image": null,
                    "location":
                    {
                        "id": 1,
                        "name": "Dnipro"
                    }
                }
            ],
            "location":
            {
                "id": 1,
                "name": "Dnipro"
            },
            "startDate": 
            {
                "year": 2017,
                "month": "APRIL",
                "era": "CE",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate":
            {
                "year": 2017,
                "month": "AUGUST",
                "era": "CE",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status":
            {
                "id": 4,
                "name": "in-process",
                "statusCategory":
                {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization":
            {
                "id": 7,
                "name": "JAVA"
            },
            "experts":
            [
                "Sergey"
            ],
            "budgetOwner": "SOFTSERVE"
        }
    ]

<a name="current-user-location-groups-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/groups/mylocation"
    }

**Code:** 404 Not Found   

**Content:**

    {
        "message":"Not Found"
    }

[Table of content](#table-of-content)

<a name="filter-groups"></a>
## Show the groups, with filter

<a name="filter-groups-url"></a>
### URL :
/groups/filter

<a name="filter-groups-method"></a>
### Method :
GET

<a name="filter-groups-url-params"></a>
### URL Params :
**Required:** `location=[integer]`
 
Example: `location=1`

<a name="filter-groups-data-params"></a>
### Data Params :
None

<a name="filter-groups-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    [
        {
            "id": 1,
            "name": "DP-115",
            "teachers":
            [
                {
                    "id": 1,
                    "firstName": "Oleg",
                    "lastName": "Shvets",
                    "role":
                    {
                        "id": 3,
                        "name": "teacher",
                        "roleCategory":
                        {
                            "id": 2,
                            "name":" itacademy"
                        }
                    },
                    "nickName": "OlegShvets",
                    "image": null,
                    "location":
                    {
                        "id": 1,
                        "name": "Dnipro"
                    }
                }
            ],
            "location":
            {
                "id": 1,
                "name": "Dnipro"
            },
            "startDate": 
            {
                "year": 2017,
                "month": "APRIL",
                "era": "CE",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate":
            {
                "year": 2017,
                "month": "AUGUST",
                "era": "CE",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology":
                {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status":
            {
                "id": 4,
                "name": "in-process",
                "statusCategory":
                {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization":
            {
                "id": 7,
                "name": "JAVA"
            },
            "experts":
            [
                "Sergey"
            ],
            "budgetOwner": "SOFTSERVE"
        }
    ]

<a name="filter-groups-error-response"></a>
### Error Response :
**Code:** 404 Not Found

**Content:**

    {
        "message": "Not Found"
    }

**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/groups/filter"
    }

[Table of content](#table-of-content)

<a name="add-group"></a>
## Add group

<a name="add-group-url"></a>
### URL :
/groups/

<a name="add-group-method"></a>
### Method :
POST

<a name="add-group-url-params"></a>
### URL Params :
None

<a name="add-group-data-params"></a>
### Data Params :
**Required:**

`name=[string]`

`expert=[array of string]`

`location=[integer]`

`specialization=[integer]`

`budgetOwner=[string]`

`startDate=[date]`

`finishDate=[date]`

`teachers=[array of integer]`

Example:

`name=DP-115q`

`expert=sergey, andrey`

`location=1`

`specialization=1`

`budgetOwner=SOFTSERVE`

`startDate=05/10/2015`

`finishDate=06/11/2016`

`teachers=1,2`

<a name="add-group-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:** None

<a name="add-group-error-response"></a>
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

**Code:** 403 Forbidden

**Content:**

    {
        "message": "Access Denied"
    }
    
[Table of content](#table-of-content)

<a name="delete-group"></a>
## Delete group

<a name="delete-group-url"></a>
### URL :
/groups/{id}

<a name="delete-group-method"></a>
### Method :
DELETE

<a name="delete-group-url-params"></a>
### URL Params :
None

<a name="delete-group-data-params"></a>
### Data Params :
None

<a name="delete-group-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:** None

<a name="delete-group-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/groups/1"
    }

**Code:** 404 Not Found

**Content:**

    {
        "message": "Not Found"
    }

**Code:** 403 Forbidden

**Content:**

    {
        "message": "Access Denied"
    }
    
[Table of content](#table-of-content)