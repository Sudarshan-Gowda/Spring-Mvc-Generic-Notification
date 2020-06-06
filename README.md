# Spring MVC Generic Notification 
  Generic Configuration for Triggering Email Notification to the User by Using Spring MVC.

## Running This Application Locally

You can then access Url here: http://localhost:8080/Spring-Mvc-Generic-Notification


## In case you find a bug/suggested improvement for Spring Restfull Webservices
Our issue tracker is available here: https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification/issues


## Working with this project in Spring Tool Suite or Eclipse

### prerequisites
The following items should be installed in your system:
* STS - 3.0 Plus
* Server - Apache tomcat
* Data Base - MySql 5

### Steps:

1) Download this Project and do maven import.
```
git clone https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification
```
2) To Import the Praject Using STS or Eclipse
```
File -> Import -> Maven -> Existing Maven project
```


## Looking for something in particular?

|Spring Boot Configuration | Class or Java property files  |
|--------------------------|---|
|The Service Class | [Notification Service](https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification/blob/master/src/main/java/com/star/sud/service/impl/NotificationServiceImpl.java) |
|SQL Scripts File | [scripts.sql](https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification/blob/master/src/main/resources/scripts.sql) |


## Steps to test the application:

1) Once the application is installed properly, Run the application by adding server or build and deploy to webapps directory in server

2) Once the application deployed successfully means hit the URL from your browser. <br>
(http://localhost:8080/Spring-Mvc-Generic-Notification).
<img src="https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification/blob/master/docs/pic1.png" width="100%"/>

3) Enter the details Name and valid user email id and hit the submit button to trigger the email as shown in Image 
<img src="https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification/blob/master/docs/pic2.png" width="100%"/>

4) Create a schema called NOTIFICATION in MYSQL Database and execute the scripts which is under the repository location [click here](https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification/blob/master/src/main/resources/scripts.sql).

5) Since this application is developed by considering to support any of the configuration like gmail, outlook or Zoho etc. The Configuration is maintained under the table called T_NTFN_DEVICE and modify the configuration as per your requirement and your account credentials and make sure the less secure app is enabled in case google account and double security disable for Zoho respectively. 

6) Then to test the application go to the landing page and and page will prompt with User name and Email to enter. If the entered email is valid means Email notification will be triggered to the user.
	
   
# Contributing

The [issue tracker](https://github.com/Sudarshan-Gowda/Spring-Mvc-Generic-Notification/issues) is the preferred channel for bug reports, features requests and submitting pull requests.

For pull requests, editor preferences are available in the [editor config](.editorconfig) for easy use in common text editors. 

