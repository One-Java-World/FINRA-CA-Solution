
	SIMPLE SPRING BOOT REST CONTROLLER FOR UPLOADING A FILE AND META DATA
===========================================================================================

- The API contains the requested upload end-point: http://localhost:8080/uploadDocument
- Accepts File object and meta-data json
- Saves the meta-data to embbeded H2 database with Spring data JPA, and saves the file to local file system using the generated UUID.
- Get file is not implemented as it's not requested


- The service is very simple without security or unit testing
- The security can easily be implemented using "statesless spring security with JWT"


== Compile:
mvn clean install          -OR-
mvn clean install -DskipTest

== Run:
java -jar solution-0.0.1-SNAPSHOT.jar          -OR-
$ java -Xdebug -Xrunjdwp:server=y,transport=dt_socket,address=40013,suspend=n -jar target/*.jar



== The app should be running on:http://localhost:8080/

e.g.
http://localhost:8080/getDocumentByuuidSysName?uuid=1Id-23-xxx9,sysName=unity