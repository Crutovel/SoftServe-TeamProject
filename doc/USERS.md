# API Documentation for /Users End Point

## Show all users

**URL :** /users

**Method :** GET

**URL Params :** -

**Data Params :** -

**Success Response:** Code: 200, Content: 

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

**Error Response :** Code: 401 Unauthorized

## Show one user

**URL :** /users

**Method :** GET

**URL Params :** Required: `id=[integer]`, example: `id=2`

**Data Params :** -

**Success Response:** Code: 200, Content:

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

**Error Response :** Code: 401 Unauthorized, 404 Not Found