# MusicFestival
:ticket: :guitar: :ferris_wheel: :notes:
## Requirements:
#### The organizers of a music festival have several offices in the country where festival tickets are sold. At every office a soft system is used to sell tickets. The person in each office uses a desktop application with the following functionalities: 
<ol>
  <li> <b>Login.</b> After successful login, a new window opens showing all artists
  (the name, date and place of the performance, the number of seats available and the number of seats already sold).
  An artist can hold several shows. </li>
  <li> <b>Search.</b> After successfully logging in, the office person can search for artists performing in a
  certain day The application will show in another list/table all the artists who are performing that day, location, time
  start times and the number of places available. </li>
  <li><b>Purchase.</b> Employee may sell tickets for a specific performance. For sale is introduced
  buyer's name and number of seats desired. After the sale all employees from all offices see
  updated list of shows. If there are no more seats available at a show, the show will be
  shown in red.</li>
  <li><b>Logout.</b></li>
</ol>
<br>
 
## Repository structure :computer:
#### In this repository there are 8 branches:
<ol>
  <li><b>main</b> - this is the README.md file </li>
  <li><b>java-client-server</b> - client-server application in Java, made without the use of other helping technologies (only Gradle) </li>
  <li><b>c#-client-server</b> - client-server application in Java, made without the use of other helping technologies </li>
  <li><b>java-grpc</b> - server in java implemented using gRpc technology to define the service, communicates with the client written in the C# language </li>
  <li><b>c#-grpc</b> - client in c# implemented using gRpc technology, communicates with the server written in java language </li>
  <li><b>orm-java</b> - java-grpc app, where I used Hibernate to store objects from the Performance and Ticket classes in the database </li>
  <li><b>rest-java></b> - implementation of the REST services for the Performance entity (PerformanceController) and a ReactJS client. Starting the REST services is done                           through SpringBoot. Also, a client for the REST services written in Java is implemented.
  <li><b>broker-java</b> - java-client-server app, where the notification of clients is done using a message broker, namely ActiveMQ </li>
</ol>
   
#### I also used log4j to maintain a log file.

