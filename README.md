# user-info-ms

Steps to Run the API

Step 1: Import this maven project into eclipse / intelliJ and compile the source codes.

	Java 1.8 required ( min Java 1.8)

Step 2: Run the Java class "UserInfoApplication.java" Or Run as springBoot application

	From Command Prompt: java UserInfoApplication

Step 3: API Runs on 8080 Port, you can use SOAP UI to test the API's.

SOAP UI collections are provided in root folder as "UpworkAssignment.postman_collection.json"

Import these collections into Postman UI u can see all 4 CRUD Operations with samples

Step 4: H2 Database Console URL
	http://localhost:8080/console
	
	API URL's
	http://localhost:8080/api/user/{email}  - GET
	http://localhost:8080/api/user - POST
	http://localhost:8080/api/user - PUT
	http://localhost:8080/api/user/{email} - DELETE
		
