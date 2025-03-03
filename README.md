# FDA Drugs API
Simple application for presenting FDA drugs API responses with CRUD endpoints.

## Requirements:
- Java 21+
- Maven
- Spring Boot

## Installation and configuration
1. Clone the repository via terminal to preffered location:
```shell

git clone https://github.com/OskarCh29/RestfulAPI.git
```
****
2. Configure the database connection and datasource by `application.yaml` file<br>
**Datasource:**<br>
   - **url: jdbc:mysql://localhost:3306/${DB_NAME}**
   - **username: ${DB_USERNAME}**
   - **password: ${DB_PASSWORD}**
<br>
   - If you are using mySQL database with the same localhost port all you need is to enter
   database information including:<br>Database name, username and password.<br>  
   You can simply add all information in the file or set them up via environmental variables.  
   In the Drug Record class you can modify Table/Column names.<br>

****
3. After configuration application could be started with your IDE or with command:<br>
```shell

mvn spring-boot:run
```
**Be aware that for running app with command line you should be in the app directory**

## Usage
To get drug information from FDA Api database use basic endpoint:  
`localhost:**{YourPort}**/drug/search`  
Required field is manufacturer name and optional fields are:
- Brand name
- Limit - Limits the responses on the pages (Basic value is 1)
- Page - Showing the requested page (Basic value is 1)  

Example of the query:
`http://localhost:8080/drug/search?manufacturer=Pfizer&name=ZARONTIN&limit=1`

Where:
- Requested manufacturer:
- Optional brand name: ZARONIT
- Limit: 1  

Please be informed that after providing requested fields add `&` to query next param

### Documentation
Documentation for endpoints of the api provided on the Swagger.
`http://localhost:8080/swagger-ui/index.html`

## Testing
Just enter the following command in your app directory via terminal.
```shell

mvn test
```
### About tests
For testing external API wireMock was used to simulate the offline response.  
Service tests (API service) were tested together with controller to verify webClient, wireMock and service working together  
Testing rest controller basic mock for service layer was used and for service classes integration tests with H2 embedded database
