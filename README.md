# SpringBoot REST MySQL Backend


*Reference:* https://spring.io/guides/tutorials/bookmarks/


## Clone the project and build it
Run the command below in your terminal to clone the project:
```
$ git clone ...
```
Go inside project folder:
```
$ cd ...
```
Build the project:
```
$ mvn clean package -DskipTests
```

## Launch the application and interact with it
From the project root folder, run the command below to launch the application:
```
$ mvn clean spring-boot:run
```
A successful output log will be:
```console
2019-01-11 14:08:19.985  INFO 4064 --- [           main] o.s.b.w.embedded.tomcat.TomcatWebServer  : Tomcat started on port(s): 4000 (http) with context path ''
2019-01-11 14:08:19.989  INFO 4064 --- [           main] c.m.s.SpringRestMysqlApplication         : Started SpringRestMysqlApplication in 8.592 seconds (JVM running for 20.038)
```
### 3.1 Requests
run the application and visit the
[Swagger documentation](http://localhost:4000/swagger-ui-custom.html) to see all possible requests. </br>
url: /swagger-ui-custom.html


