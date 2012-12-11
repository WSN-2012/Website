# Website 

## General
### About
This is a project made by (or partially by) WSN-Team 2012 in the course CSD, which is part of the [Technology Transfer Alliance](http://ttaportal.org/).
### Purpose
One of the project's goal is to build a website. The website is displaying data in a friendly way and handles user authentication and management. For more information look in the documentation available in the TTA portal.
Another project's goal is to reply to http requests by contacting the database and sending a JSON object. The requests may query for gateway list, sensor list and data list. For more information look in the documentation in the TTA portal.
### Description
Website contains a page for display bundlepayload data, filled with our systems WSN data. Additionally another website's aim is to manage user authentication. User authentication can be used by future groups which would like to implement a privacy policy for displaying data from different gateways. Website consists of login, registration, account settings pages for user management. There is also a contact us page in order for users to contact the system administrators. Lastly, there is a server configuration page for changing the configuration file of the server available for future implementation. WSN-Team 2012 project website is uploaded here: [Website](https://github.com/WSN-2012/Website).

## Install & Upload
A server is needed in order to be able to upload and run the website on it. In our project we used apache tomcat 7 but any server version can be used. You can download [Tomcat 7](http://tomcat.apache.org/download-70.cgi) and you can follow the [instructions](http://tomcat.apache.org/tomcat-7.0-doc/setup.html) for installation guidelines. To upload a the project in your server you have to do the following:
* create a war file using standard Java jar tool by typing in the command line:
  * `cd <project path>/Website` (go to the project directory)
  * `jar cf ../Website.war *` (create the war file. **Notice:** include all the appropriate librarys for example gson, database and mail jar)
* enter the servers manager/html page
* deploy the war file in the website and your website is up and running in your server

In this project we used postreSQL vesrion 9.1 to create our database schema. Any database can be used if appropriate changes are commited in the Database.java and SQLQueries.java files.
To download and install postgreSQL you can go to [PostgreSQL](http://www.postgresql.org/download/) and download the appropriate source. Then follow the [instructions](http://www.postgresql.org/docs/9.1/interactive/index.html). To load the database schema you have to do the following from command line:
* `psql postgres`(connect to database as postgres user)
* `CREATE DATABASE "DatabaseName";` (create database)
* `psql databasename < data_base_dump` (import DB from dump file *outside the psql command line*. The projects database dump file is stored in the database folder. **Notice:** database should be created before importing the dump file)