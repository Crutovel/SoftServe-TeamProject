# API Documentation for End Point "/users"

## Table of Content

- [Show all users](#show-all-users)
    - [URL](#show-all-users-url)
    - [Method](#show-all-users-method)
    - [URL params](#show-all-users-url-params)
    - [Data params](#show-all-users-method-data-params)
    - [Success response](#show-all-users-method-success-response)
    - [Error response](#show-all-users-method-error-response)
- [Show one user](#show-one-user)
    - [URL](#show-one-user-url)
    - [Method](#show-one-user-method)
    - [URL params](#show-one-user-url-params)
    - [Data params](#show-one-user-data-params)
    - [Success response](#show-one-user-success-response)
    - [Error response](#show-one-user-error-response)


## <a name="show-all-users">Show all users</a>

<a name="show-all-users-url"></a>
### URL :
/users

<a name="show-all-users-method"></a>
### Method :
GET

<a name="show-all-users-url-params"></a>
### URL Params :
None

<a name="show-all-users-data-params"></a>
### Data Params :
None

<a name="show-all-success-response"></a>
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

<a name="show-all-users-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:** None

<a name="show-one-user"></a>
## Show one user

<a name="show-one-user-url"></a>
### URL :
/users

<a name="show-one-user-method"></a>
### Method : 
GET

### URL Params : <a name="show-one-user-url-params"></a>
**Required:** `id=[integer]`. Example: `id=2`

### Data Params : <a name="show-one-user-data-params"></a>
None

### Success Response: <a name="show-one-user-success-response"></a>
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

### Error Response : <a name="show-one-user-error-response"></a>
**Code:** 401 Unauthorized

**Content:** None