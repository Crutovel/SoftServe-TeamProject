# API Documentation for End Point /groups

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
            "teacher": {
                "id": 1,
                "firstName": "Oleg",
                "lastName": "Shvets",
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
                    "dayOfMonth": 1,
                    "dayOfWeek": "SUNDAY",
                    "era": "CE",
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
                        "value": "olshvets@gmail.com",
                        "primary": true
                    }
                ],
                "phones": [
                    {
                        "id": 1,
                        "value": "+380-96-456-22-74"
                    }
                ],
                "contactLinks": [
                    {
                        "id": 1,
                        "value": "https://linkedin.com/OlegShvets"
                    }
                ]
            },
            "location": {
                "id": 1,
                "name": "Dnipro",
                "country": {
                    "id": 1,
                    "name": "Ukraine"
                }
            },
            "startDate": {
                "year": 2017,
                "month": "APRIL",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "era": "CE",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate": {
                "year": 2017,
                "month": "AUGUST",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "era": "CE",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status": {
                "id": 4,
                "name": "in-process",
                "statusCategory": {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization": {
                "id": 7,
                "name": "JAVA"
            }
        },
        {
            "id": 2,
            "name": "SF-115",
            "teacher": {
                "id": 3,
                "firstName": "Lucas",
                "lastName": "Lukichich",
                "role": {
                    "id": 4,
                    "name": "coordinator",
                    "roleCategory": {
                        "id": 2,
                        "name": "itacademy"
                    }
                },
                "dateOfBirth": {
                    "year": 2017,
                    "month": "MARCH",
                    "dayOfMonth": 3,
                    "dayOfWeek": "FRIDAY",
                    "era": "CE",
                    "dayOfYear": 62,
                    "leapYear": false,
                    "monthValue": 3,
                    "chronology": {
                        "id": "ISO",
                        "calendarType": "iso8601"
                    }
                },
                "nickName": "LukasLukichich",
                "selfInfo": "coordinator in Sofia",
                "image": null,
                "location": {
                    "id": 2,
                    "name": "Sofia",
                    "country": {
                        "id": 2,
                        "name": "Hungary"
                    }
                },
                "emails": [
                    {
                        "id": 4,
                        "value": "lucalukis@gmail.com",
                        "primary": true
                    }
                ],
                "phones": [
                    {
                        "id": 3,
                        "value": "+344-04-252-66-32"
                    }
                ],
                "contactLinks": []
            },
            "location": {
                "id": 2,
                "name": "Sofia",
                "country": {
                    "id": 2,
                    "name": "Hungary"
                }
            },
            "startDate": {
                "year": 2017,
                "month": "APRIL",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "era": "CE",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate": {
                "year": 2017,
                "month": "AUGUST",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "era": "CE",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status": {
                "id": 4,
                "name": "in-process",
                "statusCategory": {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization": {
                "id": 2,
                "name": "js_core"
            }
        }
    ]

<a name="all-groups-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:** None

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
            "teacher": {
                "id": 1,
                "firstName": "Oleg",
                "lastName": "Shvets",
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
                    "dayOfMonth": 1,
                    "dayOfWeek": "SUNDAY",
                    "era": "CE",
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
                        "value": "olshvets@gmail.com",
                        "primary": true
                    }
                ],
                "phones": [
                    {
                        "id": 1,
                        "value": "+380-96-456-22-74"
                    }
                ],
                "contactLinks": [
                    {
                        "id": 1,
                        "value": "https://linkedin.com/OlegShvets"
                    }
                ]
            },
            "location": {
                "id": 1,
                "name": "Dnipro",
                "country": {
                    "id": 1,
                    "name": "Ukraine"
                }
            },
            "startDate": {
                "year": 2017,
                "month": "APRIL",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "era": "CE",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate": {
                "year": 2017,
                "month": "AUGUST",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "era": "CE",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status": {
                "id": 4,
                "name": "in-process",
                "statusCategory": {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization": {
                "id": 7,
                "name": "JAVA"
            }
        }
    ]

<a name="teacher-groups-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized, 404 Not Found
 
**Content:** None

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
            "id": 2,
            "name": "SF-115",
            "teacher": {
                "id": 3,
                "firstName": "Lucas",
                "lastName": "Lukichich",
                "role": {
                    "id": 4,
                    "name": "coordinator",
                    "roleCategory": {
                        "id": 2,
                        "name": "itacademy"
                    }
                },
                "dateOfBirth": {
                    "year": 2017,
                    "month": "MARCH",
                    "dayOfMonth": 3,
                    "dayOfWeek": "FRIDAY",
                    "era": "CE",
                    "dayOfYear": 62,
                    "leapYear": false,
                    "monthValue": 3,
                    "chronology": {
                        "id": "ISO",
                        "calendarType": "iso8601"
                    }
                },
                "nickName": "LukasLukichich",
                "selfInfo": "coordinator in Sofia",
                "image": null,
                "location": {
                    "id": 2,
                    "name": "Sofia",
                    "country": {
                        "id": 2,
                        "name": "Hungary"
                    }
                },
                "emails": [
                    {
                        "id": 4,
                        "value": "lucalukis@gmail.com",
                        "primary": true
                    }
                ],
                "phones": [
                    {
                        "id": 3,
                        "value": "+344-04-252-66-32"
                    }
                ],
                "contactLinks": []
            },
            "location": {
                "id": 2,
                "name": "Sofia",
                "country": {
                    "id": 2,
                    "name": "Hungary"
                }
            },
            "startDate": {
                "year": 2017,
                "month": "APRIL",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "era": "CE",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate": {
                "year": 2017,
                "month": "AUGUST",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "era": "CE",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status": {
                "id": 4,
                "name": "in-process",
                "statusCategory": {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization": {
                "id": 2,
                "name": "js_core"
            }
        }
    ]

<a name="current-user-location-groups-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized, 404 Not Found

**Content:** None

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
**Required:**

`location=[integer]`
 
Example:
 
`location=2`

<a name="filter-groups-data-params"></a>
### Data Params :
None

<a name="filter-groups-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    [
        {
            "id": 2,
            "name": "SF-115",
            "teacher": {
                "id": 3,
                "firstName": "Lucas",
                "lastName": "Lukichich",
                "role": {
                    "id": 4,
                    "name": "coordinator",
                    "roleCategory": {
                        "id": 2,
                        "name": "itacademy"
                    }
                },
                "dateOfBirth": {
                    "year": 2017,
                    "month": "MARCH",
                    "dayOfMonth": 3,
                    "dayOfWeek": "FRIDAY",
                    "era": "CE",
                    "dayOfYear": 62,
                    "leapYear": false,
                    "monthValue": 3,
                    "chronology": {
                        "id": "ISO",
                        "calendarType": "iso8601"
                    }
                },
                "nickName": "LukasLukichich",
                "selfInfo": "coordinator in Sofia",
                "image": null,
                "location": {
                    "id": 2,
                    "name": "Sofia",
                    "country": {
                        "id": 2,
                        "name": "Hungary"
                    }
                },
                "emails": [
                    {
                        "id": 4,
                        "value": "lucalukis@gmail.com",
                        "primary": true
                    }
                ],
                "phones": [
                    {
                        "id": 3,
                        "value": "+344-04-252-66-32"
                    }
                ],
                "contactLinks": []
            },
            "location": {
                "id": 2,
                "name": "Sofia",
                "country": {
                    "id": 2,
                    "name": "Hungary"
                }
            },
            "startDate": {
                "year": 2017,
                "month": "APRIL",
                "dayOfMonth": 29,
                "dayOfWeek": "SATURDAY",
                "era": "CE",
                "dayOfYear": 119,
                "leapYear": false,
                "monthValue": 4,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "finishDate": {
                "year": 2017,
                "month": "AUGUST",
                "dayOfMonth": 18,
                "dayOfWeek": "FRIDAY",
                "era": "CE",
                "dayOfYear": 230,
                "leapYear": false,
                "monthValue": 8,
                "chronology": {
                    "id": "ISO",
                    "calendarType": "iso8601"
                }
            },
            "status": {
                "id": 4,
                "name": "in-process",
                "statusCategory": {
                    "id": 2,
                    "name": "current"
                }
            },
            "specialization": {
                "id": 2,
                "name": "js_core"
            }
        }
    ]

<a name="filter-groups-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized, 404 Not Found

**Content:** None

[Table of content](#table-of-content)