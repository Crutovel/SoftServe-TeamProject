# IT-Academy Project

The current project is to be an online IT-Academy organisational app, where all the students and teachers will be able to plan, organize and schedule their work effectively.

## Task 1(Code style in Idea)

### Google code style

In order to be able to auto-format the code using Google code style conventions in Idea, proceed as follows:
1)Download a file: https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
2)Open in your Idea: Settings/Editor/Code style, and near the "Scheme" option click "Manage...",
then choose "Import/Intellij Idea Code Style XML", and afterwards choose the previously downloaded file
"intellij-java-google-style.xml"
3)Choose "Scheme GoogleStyle", click OK

### CheckStyle plugin

In case we decide to use a plug-in "CheckStyle" to check our code style:
1)Install the plug-in in the Idea: "Settings/Plugins/Browse repositories...",
choose an option "CheckStyle-IDEA", install and restart your Idea
2)Download a file: https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/google_checks.xml
3)And finally, in the Idea go to: Settings/Other Settings/Checkstyle/Configuration File,
add the file "google_checks.xml" and choose it to be a configuration file.