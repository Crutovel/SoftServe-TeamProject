# API Documentation for End Point "/user/profile"

<a name="table-of-content"></a>
## Table of Content

- [Retrieve information about current user after success login](#information-about-current-user)
    - [URL](#information-about-current-user-url)
    - [Method](#information-about-current-user-method)
    - [URL Params](#information-about-current-user-url-params)
    - [Data Params](#information-about-current-user-data-params)
    - [Success Response](#information-about-current-user-success-response)
    - [Field "image"](#information-about-current-user-field-image)
    - [Error Response](#information-about-current-user-error-response)

<a name="information-about-current-user"></a>
## Retrieve information about current user after success login

<a name="information-about-current-user-url"></a>
### URL :
/user/profile

<a name="information-about-current-user-method"></a>
### Method :
GET

<a name="information-about-current-user-url-params"></a>
### URL Params :
None

<a name="information-about-current-user-data-params"></a>
### Data Params :
None

<a name="information-about-current-user-success-response"></a>
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

<a name="information-about-current-user-field-image"></a>
### Field "image" :
Represent byte array of image as string encoded using the Base64 encoding scheme. 

<a name="information-about-current-user-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

[Table of content](#table-of-content)

