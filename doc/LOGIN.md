# API Documentation for End Point "/login"

<a name="table-of-content"></a>
## Table of Content

- [Retrieve information about current user after success login](#first-subtitle)
    - [URL](#url)
    - [Method](#method)
    - [URL Params](#url-params)
    - [Data Params](#data-params)
    - [Data Type](#data-type)
    - [Success Response](#success-response)
    - [Error Response](#error-response)

<a name="first-subtitle"></a>
## Login

<a name="url"></a>
### URL :
/login

<a name="method"></a>
### Method :
POST

<a name="url-params"></a>
### URL Params :
None

<a name="data-params"></a>
### Data Params :
**Required:**

`username=[String], password=[String]`

Example:

`username=OlegShvets, password=ghd22df`

<a name="data-type"></a>
### Data type :
form-data

<a name="success-response"></a>
### Success Response :
**Code:** 200 OK

**Content:**

<a name="error-response"></a>
### Error Response :
**Code:** 401 Unauthorized, 405 Method Not Allowed

**Content:**

[Table of content](#table-of-content)