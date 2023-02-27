# Monitoring example
This project contains: TIG (telegraf, influxdb, grafana) stack which is monitoring Spring Boot app with elasticsearch and mongodb services.

# Build spring boot application .jar with maven
```
mvn clean package
```

# Start the stack with docker compose
```
docker-compose up
```
# REST API: 
POST http://127.0.0.1/jokes/elasticsearch
```
{
    "text": "Chuck Norris doesn't read books. He stares them down until he gets the information he wants"
}
```
Response:
```
{
    "id": "08a214c4-65d6-41d3-8d41-ba54cc87ca81",
    "text": "Chuck Norris doesn't read books. He stares them down until he gets the information he wants"
}
```
GET http://127.0.0.1/jokes/elasticsearch/08a214c4-65d6-41d3-8d41-ba54cc87ca81
```
{
    "id": "08a214c4-65d6-41d3-8d41-ba54cc87ca81",
    "text": "Chuck Norris doesn't read books. He stares them down until he gets the information he wants"
}
```

POST http://127.0.0.1/jokes/mongodb
```
{
    "text": "Chuck Norris doesn't read books. He stares them down until he gets the information he wants"
}
```
Response:
```
{
    "id": "08a214c4-65d6-41d3-8d41-ba54cc87ca81",
    "text": "Chuck Norris doesn't read books. He stares them down until he gets the information he wants"
}
```
GET http://127.0.0.1/jokes/mongodb/08a214c4-65d6-41d3-8d41-ba54cc87ca81
```
{
    "id": "08a214c4-65d6-41d3-8d41-ba54cc87ca81",
    "text": "Chuck Norris doesn't read books. He stares them down until he gets the information he wants"
}
```


# Run the Apache Benchmarking tool
```
ab -n 15000 -c 100 'http://localhost/jokes/mongodb/6874bdca-7484-4b93-af1b-6dee743b3de8'
```
Results:
```
This is ApacheBench, Version 2.3 <$Revision: 1901567 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking localhost (be patient)
Completed 1500 requests
Completed 3000 requests
Completed 4500 requests
Completed 6000 requests
Completed 7500 requests
Completed 9000 requests
Completed 10500 requests
Completed 12000 requests
Completed 13500 requests
Completed 15000 requests
Finished 15000 requests


Server Software:        nginx/1.23.3
Server Hostname:        localhost
Server Port:            80

Document Path:          /jokes/mongodb/6874bdca-7484-4b93-af1b-6dee743b3de8
Document Length:        146 bytes

Concurrency Level:      100
Time taken for tests:   42.216 seconds
Complete requests:      15000
Failed requests:        0
Total transferred:      4095000 bytes
HTML transferred:       2190000 bytes
Requests per second:    355.31 [#/sec] (mean)
Time per request:       281.442 [ms] (mean)
Time per request:       2.814 [ms] (mean, across all concurrent requests)
Transfer rate:          94.73 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    1   6.9      0     710
Processing:    18  280 155.9    247    1389
Waiting:       18  279 155.8    246    1389
Total:         25  281 155.7    247    1390

Percentage of the requests served within a certain time (ms)
  50%    247
  66%    302
  75%    347
  80%    380
  90%    477
  95%    570
  98%    716
  99%    838
 100%   1390 (longest request)
```

# Grafana Dashboards while benchmarking
## Spring Boot metrics
![Alt text](/images/spring-boot-app.png?raw=true "Spring Boot metrics")
## Mongo DB metrics
![Alt text](/images/mongodb.png?raw=true "Mongo DB metrics")
## NGINX metrics
![Alt text](/images/nginx.png?raw=true "NGINX metrics")


