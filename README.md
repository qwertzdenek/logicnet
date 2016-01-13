# Deployment
You will need Java 8, maven and probably MySQL (tested on MariaDB). You will find TomEE configuration in src/main/tomee/conf.
Create database, user and import schema *database.sql*. Now you have to create image data directory and set it
in src/main/webapp/WEB-INF/web.xml.

now you can unleash maven:
$ mvn package tomee:run

Application listens on http://[::1]:8080.
