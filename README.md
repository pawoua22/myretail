# My retail API
Target case study: Proof of concept for getting a product name and current price

## Getting Started

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
    docker compose up
    ```
2. Navigate to http://localhost:8080/swagger-ui.html

#### How to run the program in debug mode:
1. Comment out this section in docker-compose.yaml file where api is specified
   ```  
   api:
    image: casestudy-myretail
    ports:
      - 8080:8080
    links:
      - mongo```
2. Point spring profile to debug
3. Run below to spin up mongodb
   ```
    docker compose up
   ```
4. Using terminal or your choice of IDE, run in debug mode   
5. Navigate to http://localhost:8080/swagger-ui.html


## Author

Pawoua Vang
