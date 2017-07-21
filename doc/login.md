# API Documentation for End Point "/login"

<a name="table-of-content"></a>
## Table of Content

- [Login](#login)
    - [URL](#login-url)
    - [Method](#login-method)
    - [URL Params](#login-url-params)
    - [Data Params](#login-data-params)
    - [Data Type](#login-data-type)
    - [Success Response](#login-success-response)
    - [Error Response](#login-error-response)

<a name="login"></a>
## Login

<a name="login-url"></a>
### URL :
/login

<a name="login-method"></a>
### Method :
POST

<a name="login-url-params"></a>
### URL Params :
None

<a name="login-data-params"></a>
### Data Params :
**Required:**

`username=[String], password=[String]`

Example:

`username=OlegShvets, password=ghd22df`

<a name="login-data-type"></a>
### Data type :
form-data

<a name="login-success-response"></a>
### Success Response :
**Code:** 200 OK

**Content:**

<a name="login-error-response"></a>
### Error Response :
**Code:** 401 Unauthorized, 405 Method Not Allowed

**Content:**None

[Table of content](#table-of-content)