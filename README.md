# IT-Academy Project

The current project is to be an online IT-Academy organizational app, where all the students and teachers will be able to plan, organize and schedule their work effectively.

## Development

### How to read data from db

To read from the database, use special read-only user ```user_ro``` with the password ```12345qwerty```
 
### How to run the project locally
#### Pre-requisites
1. Install ```java```
1. Install ```maven```
1. Install ```mysql-server```
1. Configure your db:
   1. Run [init.sql](https://github.com/Crutovel/SoftServe-TeamProject/blob/master/src/main/resources/init.sql)

#### Build and run the project
1. Build the project with maven: ```mvn clean install```
1. Run the jar from target: ```java -jar target/team-project-dp115-*.jar```
1. Application is accessible at http://localhost:8080/

#### Authorization
For authorization you must send POST HTTP request at http://localhost:8080/login with credentials of some user in body. Credentials: username - OlegShvets, password - ghd22df. Choose type form-data and set credentials into key and value.

## Environment setup

### Code style in Idea

#### Google code style

In order to be able to auto-format the code using Google code style conventions in Idea, proceed as follows:
1. Download a file: https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
1. Open in your Idea: Settings/Editor/Code style, and near the "Scheme" option click "Manage...",
then choose "Import/Intellij Idea Code Style XML", and afterwards choose the previously downloaded file
"intellij-java-google-style.xml"
1. Choose "Scheme GoogleStyle", click OK

#### CheckStyle plugin

In case we decide to use a plug-in "CheckStyle" to check our code style:
1. Install the plug-in in the Idea: "Settings/Plugins/Browse repositories...",
choose an option "CheckStyle-IDEA", install and restart your Idea
1. Download a file: https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml
1. And finally, in the Idea go to: Settings/Other Settings/Checkstyle/Configuration File,
add the file "google_checks.xml" and choose it to be a configuration file.

##Documentation

The documentation is available [here](doc/README.md).

To view the automatically generated REST API documentation after the application starts,
you need to go at http://localhost:8080/swagger-ui.html in browser.