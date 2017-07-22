# API Documentation for End Point "/locations"

<a name="table-of-content"></a>
## Table of Content

- [Show all locations](#all-locations)
    - [URL](#all-locations-url)
    - [Method](#all-locations-method)
    - [URL Params](#all-locations-url-params)
    - [Data Params](#all-locations-data-params)
    - [Success Response](#all-locations-success-response)
    - [Error Response](#all-locations-error-response)

<a name="all-locations"></a>
## Show all locations

<a name="all-locations-url"></a>
### URL :
/locations

<a name="all-locations-method"></a>
### Method :
GET

<a name="all-locations-url-params"></a>
### URL Params :
None

<a name="all-locations-data-params"></a>
### Data Params :
None

<a name="all-locations-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:** 

    [
        {
            "id": 1,
            "name": "Dnipro"
        },
        {
            "id": 2,
            "name": "Sofia"
        }
    ]

<a name="all-locations-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500673384031,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/locations"
    }

[Table of content](#table-of-content)