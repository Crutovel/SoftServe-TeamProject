# API Documentation for End Point "/students"

<a name="table-of-content"></a>
## Table of Content

- [Show students](#students)
    - [URL](#students-url)
    - [Method](#students-method)
    - [URL Params](#students-url-params)
    - [Data Params](#students-data-params)
    - [Success Response](#students-success-response)
    - [Error Response](#students-error-response)
- [Show student](#student)
    - [URL](#student-url)
    - [Method](#student-method)
    - [URL Params](#student-url-params)
    - [Data Params](#student-data-params)
    - [Success Response](#student-success-response)
    - [Error Response](#student-error-response)
- [Add students](#add-students)
    - [URL](#add-students-url)
    - [Method](#add-students-method)
    - [URL Params](#add-students-url-params)
    - [Data Params](#add-students-data-params)
    - [Data Type](#add-students-data-type)
    - [Success Response](#add-students-success-response)
    - [Error Response](#add-students-error-response) 
- [Edit student](#edit-student)
    - [URL](#edit-student-url)
    - [Method](#edit-student-method)
    - [URL Params](#edit-student-url-params)
    - [Data Params](#edit-student-data-params)
    - [Data Type](#edit-student-data-type)
    - [Success Response](#edit-student-success-response)
    - [Error Response](#edit-student-error-response)     
- [Edit students](#edit-students)
    - [URL](#edit-students-url)
    - [Method](#edit-students-method)
    - [URL Params](#edit-students-url-params)
    - [Data Params](#edit-students-data-params)
    - [Data Type](#edit-students-data-type)
    - [Success Response](#edit-students-success-response)
    - [Error Response](#edit-students-error-response) 
       
<a name="students"></a>
## Show students

<a name="students-url"></a>
### URL :
/students

<a name="students-method"></a>
### Method :
GET

<a name="students-url-params"></a>
### URL Params :
**Optional** `groupid=[integer]`

Example: `groupid=2`

<a name="students-data-params"></a>
### Data Params :
None

<a name="students-success-response"></a>
### Success Response:
**Code:** 200 OK

  **Content:**

    [
       {
        "studentId": 1,
        "firstName": "Lev",
        "lastName": "Bukhanets",
        "image": null,
        "links": [
            {
                "rel": "self",
                "href": "http://localhost:8080/students/1"
            },
            {
                "rel": "student",
                "href": "http://localhost:8080/students/1"
            },
            {
                "rel": "group",
                "href": "http://localhost:8080/groups/2"
            },
            {
                "rel": "englishLevel",
                "href": "http://localhost:8080/englishLevels/4"
            }
        ]
    },
    {
        "studentId": 9,
        "firstName": "Motoko",
        "lastName": "Kusanagi",
        "image": null,
        "links": [
            {
                   "rel": "self",
                   "href": "http://localhost:8080/students/9"
            },
            {
                   "rel": "student",
                   "href": "http://localhost:8080/students/9"
            },
            {
                   "rel": "group",
                   "href": "http://localhost:8080/groups/3"
            },
            {
                   "rel": "englishLevel",
                   "href": "http://localhost:8080/englishLevels/2"
            }
           ]
    }     
  ]
  
  **Content:** (with Optional parameter groupid=2)
  
    [
       {
          "studentId": 1,
          "firstName": "Lev",
          "lastName": "Bukhanets",
          "image": null,
          "links": [
              {
                  "rel": "self",
                  "href": "http://localhost:8080/students/1"
              },
              {
                  "rel": "student",
                  "href": "http://localhost:8080/students/1"
              },
              {
                  "rel": "group",
                  "href": "http://localhost:8080/groups/2"
              },
              {
                  "rel": "englishLevel",
                  "href": "http://localhost:8080/englishLevels/4"
              }
          ]
      },
      {
           "studentId": 2,
           "firstName": "Maksym",
           "lastName": "Kameniev",
           "image": null,
           "links": [
                 {
                     "rel": "self",
                     "href": "http://localhost:8080/students/2"
                 },
                 {
                     "rel": "student",
                     "href": "http://localhost:8080/students/2"
                 },
                 {
                     "rel": "group",
                     "href": "http://localhost:8080/groups/2"
                 },
                 {
                     "rel": "englishLevel",
                     "href": "http://localhost:8080/englishLevels/4"
                 }
             ]
          }     
    ]
  
<a name="students-error-response"></a>
### Error Response :

**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/students"
    }
    
[Table of content](#table-of-content)
    
<a name="student"></a>
## Show student

<a name="student-url"></a>
### URL :
/students/{id}

<a name="student-method"></a>
### Method :
GET

<a name="student-url-params"></a>
### URL Params :
None

<a name="student-data-params"></a>
### Data Params :
None

<a name="student-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    {
    "firstName": "Lev",
    "lastName": "Bukhanets",
    "image": null,
    "_links": {
        "self": {
            "href": "http://localhost:8080/students/1"
        },
        "student": {
            "href": "http://localhost:8080/students/1"
        },
        "group": {
            "href": "http://localhost:8080/students/1/group"
        },
        "englishLevel": {
            "href": "http://localhost:8080/students/1/englishLevel"
        }
    }
    }
    
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/students/1"
    }

**Code:** 404 Not Found

**Content:**

    {
        "message": "Not Found"
    }

**Code:** 403 Forbidden

**Content:**

    {
        "message": "Access Denied"
    }
    
[Table of content](#table-of-content)

<a name="add-students"></a>
## Add students

<a name="add-students-url"></a>
### URL :
/groups/{id}/students

<a name="add-students-method"></a>
### Method :
POST

<a name="add-students-url-params"></a>
### URL Params :
None

<a name="add-students-data-params"></a>
### Data Params :
**Required:**

     [
          {        
             "firstName": [String],
             "lastName": [String],
             "englishLevel": {
               "id": [Integer]
             },
             "testApprovedByExpert":{
               "id":[Integer]
             }
          }
     ]
     
**Optional:**

     [
          {        
             "image": [base64],
             "imageName": [String],
             "cv": [base64],
             "cvName": [String],
             "incomingTest": [Integer],
             "entryScore": [Double]
          }
     ]
     
Example:

    [
      {        
         "firstName": "Jim",
         "lastName": "Carrey",
         "englishLevel": {
           "id": 2
         },
         "testApprovedByExpert":{
           "id": 1
         }
      }
    ]

<a name="add-student-data-type"></a>
### Data type :
application/json

<a name="add-student-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    [
        {
            "studentId": 43,
            "firstName": "Jim",
            "lastName": "Carrey",
            "image": null,
            "imageName": null,
            "cv": null,
            "cvName": null,
            "incomingTest": null,
            "entryScore": null,
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/students/43"
                },
                {
                    "rel": "student",
                    "href": "http://localhost:8080/students/43"
                },
                {
                    "rel": "group",
                    "href": "http://localhost:8080/groups/1"
                },
                {
                    "rel": "englishLevel",
                    "href": "http://localhost:8080/englishLevels/2"
                }
                {
                    "rel": "expert",
                    "href": "http://localhost:8080/experts/1"
                }
            ]
        }
    ]

<a name="add-student-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/students"
    }

**Code:** 400 Bad Request

**Content:** 

     {
         "message": "[errors]"
     }


**Code:** 403 Forbidden

**Content:**

    {
        "message": "Access Denied: Coordinator can't add/edit students in the alien location."
    }

    {
        "message": "Access Denied" 
    }

[Table of content](#table-of-content)

<a name="edit-students"></a>
## Edit students

<a name="edit-students-url"></a>
### URL :
/students

<a name="edit-students-method"></a>
### Method :
PUT

<a name="edit-students-url-params"></a>
### URL Params :
None

<a name="edit-students-data-params"></a>
### Data Params :
**Required:**

     [
          {     
             "id": [Integer]   
          }
     ]
     
**Optional:**

     [
          {        
              "firstName": [String],
              "lastName": [String],
              "englishLevel": {
                "id": [Integer]
              },
              "testApprovedByExpert":{
                "id":[Integer]
              }
             "image": [base64],
             "imageName": [String],
             "cv": [base64],
             "cvName": [String],
             "incomingTest": [Integer],
             "entryScore": [Double]
          }
     ]
     
Example:

    [
      {     
         "id": 2,      
         "firstName": "Jim",
         "lastName": "Carrey",
         "englishLevel": {
           "id": 2
         },
         "testApprovedByExpert":{
           "id": 1
         }
      }
    ]

<a name="edit-students-data-type"></a>
### Data type :
application/json

<a name="edit-students-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    [
        {
            "studentId": 2,
            "firstName": "Jim",
            "lastName": "Carrey",
            "image": null,
            "imageName": null,
            "cv": null,
            "cvName": null,
            "incomingTest": 600,
            "entryScore": 4,
            "links": [
                {
                    "rel": "self",
                    "href": "http://localhost:8080/students/2"
                },
                {
                    "rel": "student",
                    "href": "http://localhost:8080/students/2"
                },
                {
                    "rel": "group",
                    "href": "http://localhost:8080/groups/1"
                },
                {
                    "rel": "englishLevel",
                    "href": "http://localhost:8080/englishLevels/2"
                },
                {
                    "rel": "expert",
                    "href": "http://localhost:8080/experts/1"
                }
                
            ]
        }
    ]

<a name="edit-students-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/students"
    }

**Code:** 400 Bad Request

**Content:** None

**Code:** 403 Forbidden

**Content:**

    {
        "message": "Access Denied: Coordinator can't add/edit students in the alien location."
    }

    {
        "message": "Access Denied 
    }

[Table of content](#table-of-content)

<a name="edit-student"></a>
## Edit student

<a name="edit-student-url"></a>
### URL :
/students/{id}

<a name="edit-student-method"></a>
### Method :
PUT

<a name="edit-student-url-params"></a>
### URL Params :
None

<a name="edit-student-data-params"></a>
### Data Params :
    
**Optional:**

     
      {        
           "firstName": [String],
           "lastName": [String],
           "englishLevel": {
             "id": [Integer]
           },
           "testApprovedByExpert":{
             "id":[Integer]
           }
           "image": [base64],
           "imageName": [String],
           "cv": [base64],
           "cvName": [String],
           "incomingTest": [Integer],
           "entryScore": [Double]
      }
     
     
Example:

     {      
           "firstName": "Jim",
           "lastName": "Carrey",
           "englishLevel": {
               "id": 2
           },
           "testApprovedByExpert":{
               "id": 1
           }
     }
    

<a name="edit-student-data-type"></a>
### Data type :
application/json

<a name="edit-student-success-response"></a>
### Success Response:
**Code:** 200 OK

**Content:**

    {
        "studentId": 2,
        "firstName": "Jim",
        "lastName": "Carrey",
        "image": null,
        "imageName": null,
        "cv": null,
        "cvName": null,
        "incomingTest": 600,
        "entryScore": 4,
          "links": [
          {
          "rel": "self",
          "href": "http://localhost:8080/students/2"
          },
          {
           "rel": "student",
           "href": "http://localhost:8080/students/2"
          },
          {
           "rel": "group",
           "href": "http://localhost:8080/groups/1"
          },
          {
          "rel": "englishLevel",
          "href": "http://localhost:8080/englishLevels/2"
          },
          {
          "rel": "expert",
          "href": "http://localhost:8080/experts/1"
          }
          ]
    }
    
        
    

<a name="edit-student-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized

**Content:**

    {
        "timestamp": 1500674037140,
        "status": 401,
        "error": "Unauthorized",
        "message": "Unauthorized",
        "path": "/students/2"
    }

**Code:** 400 Bad Request

**Content:** None

**Code:** 403 Forbidden

**Content:**

    {
        "message": "Access Denied: Coordinator can't add/edit students in the alien location."
    }

    {
        "message": "Access Denied 
    }
   