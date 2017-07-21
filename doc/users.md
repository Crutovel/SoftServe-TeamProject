# API Documentation for End Point "/users"

<a name="table-of-content"></a>
## Table of Content

- [Show all users](#all-users)
    - [URL](#all-users-url)
    - [Method](#all-users-method)
    - [URL params](#all-users-url-params)
    - [Data params](#all-users-data-params)
    - [Success response](#all-users-success-response)
    - [Error response](#all-users-error-response)
- [Show one user](#one-user)
    - [URL](#one-user-url)
    - [Method](#one-user-method)
    - [URL params](#one-user-url-params)
    - [Data params](#one-user-data-params)
    - [Success response](#one-user-success-response)
    - [Error response](#one-user-error-response)

<a name="all-users"></a>
## Show all users

<a name="all-users-url"></a>
### URL :
/users

<a name="all-users-method"></a>
### Method :
GET

<a name="all-users-url-params"></a>
### URL Params :
None

<a name="all-users-data-params"></a>
### Data Params :
None

<a name="all-users-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:** 

    {
        "_embedded": {
            "users": [
                {
                    "firstName": "Oleg",
                    "lastName": "Shcets",
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
                    "nickName": "OlegShvets",
                    "selfInfo": "teacher in softServe. Dnipro",
                    "image": null,
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/users/1"
                        },
                        "user": {
                            "href": "http://localhost:8080/users/1"
                        },
                        "phones": {
                            "href": "http://localhost:8080/users/1/phones"
                        },
                        "role": {
                            "href": "http://localhost:8080/users/1/role"
                        },
                        "location": {
                            "href": "http://localhost:8080/users/1/location"
                        },
                        "emails": {
                            "href": "http://localhost:8080/users/1/emails"
                        },
                        "contactLinks": {
                            "href": "http://localhost:8080/users/1/contactLinks"
                        }
                    }
                },
                {
                    "firstName": "Dmytro",
                    "lastName": "Petin",
                    "dateOfBirth": {
                        "year": 2017,
                        "month": "FEBRUARY",
                        "era": "CE",
                        "dayOfMonth": 2,
                        "dayOfWeek": "THURSDAY",
                        "dayOfYear": 33,
                        "leapYear": false,
                        "monthValue": 2,
                        "chronology": {
                            "id": "ISO",
                            "calendarType": "iso8601"
                        }
                    },
                    "nickName": "DmytroPetin",
                    "selfInfo": "coordinator in Dnipro",
                    "image": null,
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/users/2"
                        },
                        "user": {
                            "href": "http://localhost:8080/users/2"
                        },
                        "phones": {
                            "href": "http://localhost:8080/users/2/phones"
                        },
                        "role": {
                            "href": "http://localhost:8080/users/2/role"
                        },
                        "location": {
                            "href": "http://localhost:8080/users/2/location"
                        },
                        "emails": {
                            "href": "http://localhost:8080/users/2/emails"
                        },
                        "contactLinks": {
                            "href": "http://localhost:8080/users/2/contactLinks"
                        }
                    }
                }           
            ]
        },
        "_links": {
            "self": {
                "href": "http://localhost:8080/users{?page,size,sort}",
                "templated": true
            },
            "profile": {
                "href": "http://localhost:8080/profile/users"
            }
        },
        "page": {
            "size": 20,
            "totalElements": 5,
            "totalPages": 1,
            "number": 0
        }
    }

<a name="all-users-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:** None

[Table of content](#table-of-content)

<a name="one-user"></a>
## Show one user

<a name="one-user-url"></a>
### URL :
/users

<a name="one-user-method"></a>
### Method : 
GET

<a name="one-user-url-params"></a>
### URL Params :
**Required:**

`id=[integer]`

Example:

`id=2`

<a name="one-user-data-params"></a>
### Data Params :
None

<a name="one-user-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    {
        "_embedded": {
            "users": [
                {
                    "firstName": "Dmytro",
                    "lastName": "Petin",
                    "dateOfBirth": {
                        "year": 2017,
                        "month": "FEBRUARY",
                        "era": "CE",
                        "dayOfMonth": 2,
                        "dayOfWeek": "THURSDAY",
                        "dayOfYear": 33,
                        "leapYear": false,
                        "monthValue": 2,
                        "chronology": {
                            "id": "ISO",
                            "calendarType": "iso8601"
                        }
                    },
                    "nickName": "DmytroPetin",
                    "selfInfo": "coordinator in Dnipro",
                    "image": null,
                    "_links": {
                        "self": {
                            "href": "http://localhost:8080/users/2"
                        },
                        "user": {
                            "href": "http://localhost:8080/users/2"
                        },
                        "phones": {
                            "href": "http://localhost:8080/users/2/phones"
                        },
                        "role": {
                            "href": "http://localhost:8080/users/2/role"
                        },
                        "location": {
                            "href": "http://localhost:8080/users/2/location"
                        },
                        "emails": {
                            "href": "http://localhost:8080/users/2/emails"
                        },
                        "contactLinks": {
                            "href": "http://localhost:8080/users/2/contactLinks"
                        }
                    }
                }
            ]
        },
        "_links": {
            "self": {
                "href": "http://localhost:8080/users{?page,size,sort}",
                "templated": true
            },
            "profile": {
                "href": "http://localhost:8080/profile/users"
            }
        },
        "page": {
            "size": 20,
            "totalElements": 1,
            "totalPages": 1,
            "number": 0
        }
    }

<a name="one-user-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:** None

[Table of content](#table-of-content)