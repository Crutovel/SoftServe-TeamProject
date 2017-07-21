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
        "id": 1,
        "firstName": "name",
        "lastName": "LastName",
        "role": {
            "id": 3,
            "name": "teacher",
            "roleCategory": {
                "id": 2,
                "name": "itacademy"
            }
        },
        "dateOfBirth": {
            "year": 2017,
            "month": "JANUARY",
            "era": "CE",
            "dayOfMonth": 1,
            "dayOfWeek": "SUNDAY",
            "dayOfYear": 1,
            "leapYear": false,
            "monthValue": 1,
            "chronology": {
                "id": "ISO",
                "calendarType": "iso8601"
            }
        },
        "nickName": "UserNickName",
        "selfInfo": "teacher in softServe. Dnipro",
        "image": imageInBase64EncodingString,
        "location": {
            "id": 1,
            "name": "Dnipro",
            "country": {
                "id": 1,
                "name": "Ukraine"
            }
        },
        "emails": [
            {
                "id": 1,
                "value": "user@gmail.com",
                "primary": true
            }
        ],
        "phones": [
            {
                "id": 1,
                "value": "+380-12-456-22-74"
            }
        ],
        "contactLinks": [
            {
                "id": 1,
                "value": "https://linkedin.com/User"
            }
        ]
    }

<a name="userprofile-field-image"></a>
### Field "image" :
Represent byte array of image as string encoded using the Base64 encoding scheme. 

<a name="userprofile-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**None

[Table of content](#table-of-content)