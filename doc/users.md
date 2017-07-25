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
      "_embedded" : {
        "users" : [ {
          "firstName" : "Oleg",
          "lastName" : "Shvets",
          "nickName" : "OlegShvets",
          "image" : null,
          "_links" : {
            "self" : {
              "href" : "http://localhost:8080/users/1"
            },
            "user" : {
              "href" : "http://localhost:8080/users/1"
            },
            "role" : {
              "href" : "http://localhost:8080/users/1/role"
            },
            "location" : {
              "href" : "http://localhost:8080/users/1/location"
            }
          }
        }, {
          "firstName" : "Dmytro",
          "lastName" : "Petin",
          "nickName" : "DmytroPetin",
          "image" : null,
          "_links" : {
            "self" : {
              "href" : "http://localhost:8080/users/2"
            },
            "user" : {
              "href" : "http://localhost:8080/users/2"
            },
            "role" : {
              "href" : "http://localhost:8080/users/2/role"
            },
            "location" : {
              "href" : "http://localhost:8080/users/2/location"
            }
          }
        }, {
          "firstName" : "Lucas",
          "lastName" : "Lukichich",
          "nickName" : "LukasLukichich",
          "image" : null,
          "_links" : {
            "self" : {
              "href" : "http://localhost:8080/users/3"
            },
            "user" : {
              "href" : "http://localhost:8080/users/3"
            },
            "role" : {
              "href" : "http://localhost:8080/users/3/role"
            },
            "location" : {
              "href" : "http://localhost:8080/users/3/location"
            }
          }
        }, {
          "firstName" : "Lev",
          "lastName" : "Bukhanets",
          "nickName" : "Myst1c",
          "image" : null,
          "_links" : {
            "self" : {
              "href" : "http://localhost:8080/users/4"
            },
            "user" : {
              "href" : "http://localhost:8080/users/4"
            },
            "role" : {
              "href" : "http://localhost:8080/users/4/role"
            },
            "location" : {
              "href" : "http://localhost:8080/users/4/location"
            }
          }
        }, {
          "firstName" : "Dmytro",
          "lastName" : "Kholod",
          "nickName" : "DimaKh",
          "image" : null,
          "_links" : {
            "self" : {
              "href" : "http://localhost:8080/users/5"
            },
            "user" : {
              "href" : "http://localhost:8080/users/5"
            },
            "role" : {
              "href" : "http://localhost:8080/users/5/role"
            },
            "location" : {
              "href" : "http://localhost:8080/users/5/location"
            }
          }
        } ]
      },
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users{?page,size,sort}",
          "templated" : true
        },
        "profile" : {
          "href" : "http://localhost:8080/profile/users"
        }
      },
      "page" : {
        "size" : 20,
        "totalElements" : 5,
        "totalPages" : 1,
        "number" : 0
      }
    }

<a name="all-users-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500670777765,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/users"
    }

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
**Required:** `id=[integer]`

Example: `id=2`

<a name="one-user-data-params"></a>
### Data Params :
None

<a name="one-user-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    {
      "_embedded" : {
        "users" : [ {
          "firstName" : "Dmytro",
          "lastName" : "Petin",
          "nickName" : "DmytroPetin",
          "image" : null,
          "_links" : {
            "self" : {
              "href" : "http://localhost:8080/users/2"
            },
            "user" : {
              "href" : "http://localhost:8080/users/2"
            },
            "role" : {
              "href" : "http://localhost:8080/users/2/role"
            },
            "location" : {
              "href" : "http://localhost:8080/users/2/location"
            }
          }
        } ]
      },
      "_links" : {
        "self" : {
          "href" : "http://localhost:8080/users{?page,size,sort}",
          "templated" : true
        },
        "profile" : {
          "href" : "http://localhost:8080/profile/users"
        }
      },
      "page" : {
        "size" : 20,
        "totalElements" : 1,
        "totalPages" : 1,
        "number" : 0
      }
    }

<a name="one-user-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500671142812,
        "status":401,
        "error":"Unauthorized",
        "message":"Unauthorized",
        "path":"/users"
    }

[Table of content](#table-of-content)