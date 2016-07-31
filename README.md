# order-service

Few Assumptions made

1.Each Item ordered by customer can fit into one carton
2.Slot duration is for 2 hours. Only the van with all filled cartons will departure.
3.All the departure vans will be available for service at the start time of the next Slot. That means all the departed vans will come back before the end time of the current slot 

Why this assumption made?
Don't know the van behavior completely.
 
 Based on the above assumptions the current api service is available
 
 Installation Setup
 Install Maven
 
 To Run tests Locally
 mvn test 
 
 To run api from commandLine
 First create a fat jar using
 mvn -P fatjar clean package
 
 To run the service
 java -server  -cp target/order-1.0-SNAPSHOT.jar OrderService
 
  
