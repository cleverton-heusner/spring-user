# Index
- [Spring User](#spring-user)
- [Prerequisites](#prerequisites)
- [Usage](#usage)
- [Testing](#testing)

## Spring User
> Application designed for managing users.

## Prerequisites
- Java `22`
- Windows `11`

## Usage
1. At the root of the project, run the command ```./mvnw spring-boot:run```
2. From here, you can access the documentation at http://localhost:8080/doc.html;
3. Authenticate with the API via the **/login/authentication** endpoint. The username is **Fox** and the password is **123**;
4. Obtain the token generated in the response and save it somewhere;
5. In the top right corner of the documentation, you will see a button labeled **Authorize** with a lock icon to the right. Click on it;
6. In the dialog that appears, provide the token generated earlier, preceded by **"Bearer "**. Then, click on **Authorize** and close the dialog;
7. You now have free access to the users API, as your token will be sent along with the requests to these endpoints.

## Testing
- Execute the command ```./mvnw clean verify```. The coverage report will be available at ```target/site/jacoco/index.html```.