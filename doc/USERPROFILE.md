# API Documentation for UserProfile End Point

## Retrieve information about current user after success login

**URL :** /user/profile

**Method :** GET

**URL Params :** -

**Data Params :** -

**Success Response:** Code: 200, Content: 

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

**Field "image" :** Represent byte array of image as string encoded using the Base64 encoding scheme. 

**Error Response :** Code: 401 Unauthorized

