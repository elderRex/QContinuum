ReadMe


Programming Environment :

IDE : STS Tools : https://spring.io/tools/sts/all 
Server : Tomcat
Jar dependencies (external java libraries) are included in the jardir folder, but might have to be added to the buildpath when running locally.
Build Tool : Maven

Languages/Tools Used :

Java - Backend controllers, and Hibernate entities are written in Java
ORM : Hibernate (HQL) query language via a JDBC connection
Authentication : Spring Security
Spring MVC/Beans/Autowiring : Although we're not using too much of the MVC component of Spring, we can render items directly to a JSP, which we use primarily for user information.
							  We rely on Beans to manage our classes, and autowired dependencies.
AngularJS : The frontend is entirely AngularJS, which we use to render the results of GET/POST requests to the server, and for CRUD in general.
JSoup : The parser is built using JSoup HTML parsing tool (Java).
There is a class missing for sending email and for password authentication, which we can't include on a public repository because it relies on a SendGrid key that must remain private.

If the application needs to be executed, please contact fyodorominakov@gmail.com, and he'll send you the non-public file.

