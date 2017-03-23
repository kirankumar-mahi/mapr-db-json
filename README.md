# maprdb json

This is spring boot restful webservice project to demo CRUD oprations on MaprDB Json. Its gradle project.

Just create a jar by building the gradle and run 

java -jar retail.jar

To Test the MaprDB J son CRUD by using Curl

1. curl "http://localhost:8080/users"

2. curl -H "Content-Type: application/json" -X POST -d '{"_id":"002","first_name":"kiran","last_name":"kumar","age":36}' "http://localhost:8080/createuser/"
  
5. curl -H "Content-Type: application/json" -X PUT -d '{"_id":"002","first_name":"John","last_name":"mavatoor","age":40}' "http://localhost:8080/updateuser/002"

4. curl -X DELETE "http://localhost:8080/deleteuser/002"

To Supply custom application.properties, place this application.properties at the desired location and run

java -jar retail.jar --spring.config.location=/absolutepath/application.properties

Happy Learning


