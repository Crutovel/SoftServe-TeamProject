# API Documentation for End Point "/user/profile"

<a name="table-of-content"></a>
## Table of Content

- [Retrieve information about current user after success login](#userprofile)
    - [URL](#userprofile-url)
    - [Method](#userprofile-method)
    - [URL Params](#userprofile-url-params)
    - [Data Params](#userprofile-data-params)
    - [Success Response](#userprofile-success-response)
    - [Field "image"](#userprofile-field-image)
    - [Error Response](#userprofile-error-response)

<a name="userprofile"></a>
## Retrieve information about current user after success login

<a name="userprofile-url"></a>
### URL :
/user/profile

<a name="userprofile-method"></a>
### Method :
GET

<a name="userprofile-url-params"></a>
### URL Params :
None

<a name="userprofile-data-params"></a>
### Data Params :
None

<a name="userprofile-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:** 

    {
        "id": 3,
        "firstName": "Lucas",
        "lastName": "Lukichich",
        "role": 
        {
            "id": 4,
            "name": "coordinator",
            "roleCategory": 
            {
                "id": 2,
                "name": "itacademy"
            }
        },
        "nickName": "LukasLukichich",
        "image": imageInBase64EncodingString,
        "location":
        {
            "id": 2,
            "name": "Sofia"
        }
    }

<a name="userprofile-field-image"></a>
### Field "image" :
Represent byte array of image as string encoded using the Base64 encoding scheme. 

<a name="userprofile-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500671438015,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/user/profile"
    }

[Table of content](#table-of-content)