# Mobile Charging service

# Tools
Java 11  
Springboot  
POSTGRES database


# Objective:
To create a high performance engine in java springboot that can handle a large number of requests with good efficiency and certain rate. Also the
engine should maintain its availability 24/7
# System Overview:
Our engine will fetch requests from database table, send the request to HTTP interface then receive the response and archive
these responses into a history table.
The requests will be Sender ID, Receiver ID, Transfer Amount and the HTTP interface will be a Servlet that will return a status
request (Success, Failed or Rejected).
All Requests must have a Source ID (1, 2 and 3) as channel id. Each Source has a priority and the engine will serve requests
according to Source priority
