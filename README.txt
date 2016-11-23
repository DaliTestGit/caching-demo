This is a simple demo application to accompany my tutorial on caching with Spring and Guava

 Installation:
 mvn clean install

 Then start the server as follows:
 cd target
 java -jar caching-demo-1.0-SNAPSHOT.jar

 This will start a tomcat server on port 8080 and intialize an in-memory H2 database with a number of postal codes for testing (see import.sql)

 Invoke the REST API from your browser (or other client) as follows

 The temperature API takes a valid Dutch postcode (4 digits, 2 letters)

 http://localhost:8080/temperature/1000AA

 The shares API takes a valid share name as listed in nl.jsprengers.caching.Shares
 http://localhost:8080/share/FACEBOOK

link to author blog: https://blog.codecentric.de/en/2016/03/caching-de-luxe-spring-guava/

================================

To generate cobertura coverage report:
 mvn corbetura:corbeture

