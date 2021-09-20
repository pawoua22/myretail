# My retail API
Target case study: Proof of concept for getting a product name and current price

## Getting Started

* Mocked price data is pre-loaded into NoSQL data store upon app start up with product id's:
13860428, 54456119, 13264003. Product 12954218 should still return a result but with no price until
updated.

### Dependencies

* Docker
* Java 11

### Installing

* Docker https://docs.docker.com/get-docker/
* Java https://www.oracle.com/java/technologies/downloads/#java11
    * If you're on Windows and installing Java for the first time, refer to this article for a complete set up https://javatutorial.net/set-java-home-windows-10
### Executing program
#### How to run the program locally:
1. Run below to spin up both mongodb and api service
   ```
    ./run
    ```
2. Navigate to http://localhost:8080/swagger-ui.html for api doc or to hit the two available endpoints run
```
curl -X 'GET' 'http://ec2-18-217-184-177.us-east-2.compute.amazonaws.com:8080/api/v1/product/<id>' -H 'accept: application/json'
```
```
url -X 'PUT'  'http://ec2-18-217-184-177.us-east-2.compute.amazonaws.com:8080/api/v1/product/<id>'  -H 'accept: application/json' -H 'Content-Type: application/json' -d '5.59'
```

#### How to run the program in debug mode or just the api service in IDE
1. Comment out this section in docker-compose.yaml file where api is specified
   ```  
   api:
    image: casestudy-myretail
    ports:
      - 8080:8080
    links:
      - mongo
   ```
2. Point spring profile to debug
3. Run below to spin up mongodb
   ```
    docker compose up 
   ```
   You can include `--remove-orphans` if you've already ran script `./run` previously
4. Using terminal or your choice of IDE, run in debug mode
5. Navigate to http://localhost:8080/swagger-ui.html

#### How to run unit tests
1. Comment out this section in docker-compose.yaml file where api is specified
   ```  
   api:
    image: casestudy-myretail
    ports:
      - 8080:8080
    links:
      - mongo
   ```
2. Point spring profile to test
3. Run below to spin up mongodb
   ```
    docker compose up 
   ```
   You can include `--remove-orphans` if you've already ran script `./run` previously
4. Run test suite


## Author

Pawoua Vang
