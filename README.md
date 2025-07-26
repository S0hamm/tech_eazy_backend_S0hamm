# Delivery Service API (generating pr)

This repository contains the backend implementation of a Delivery Service API built with Spring Boot. It provides functionalities for managing delivery orders and parcels, including user authentication via JWT, file uploads for bulk order processing, and filtering capabilities.

The project is designed to be easily set up, built, and run, making it suitable for both manual testing and automated evaluation.

---

## Table of Contents

1.  [Project Overview](#1-project-overview)
2.  [Key Features](#2-key-features)
3.  [Technology Stack](#3-technology-stack)
4.  [Prerequisites](#4-prerequisites)
5.  [Getting Started](#5-getting-started)
    * [Cloning the Repository](#cloning-the-repository)
    * [Building the Project](#building-the-project)
    * [Running the Application](#running-the-application)
6.  [For Automated Evaluation](#6-for-automated-evaluation)
7.  [Database Access (H2 Console)](#7-database-access-h2-console)
8.  [API Usage with Postman](#8-api-usage-with-postman)
    * [Importing the Postman Collection](#importing-the-postman-collection)
    * [Understanding Collection Variables](#understanding-collection-variables)
    * [API Endpoints Walkthrough](#api-endpoints-walkthrough)
        * [8.1. Authentication: Login User](#81-authentication-login-user)
        * [8.2. Delivery Orders & Parcels: Create Parcel (In-Memory)](#82-delivery-orders--parcels-create-parcel-in-memory)
        * [8.3. Delivery Orders & Parcels: Upload Delivery Order (CSV)](#83-delivery-orders--parcels-upload-delivery-order-csv)
        * [8.4. Delivery Orders & Parcels: Get Delivery Orders for Today](#84-delivery-orders--parcels-get-delivery-orders-for-today)
        * [8.5. Delivery Orders & Parcels: Get Filtered Delivery Orders](#85-delivery-orders--parcels-get-filtered-delivery-orders)
        * [8.6. File Download: Download Uploaded File](#86-file-download-download-uploaded-file)
9.  [Initial Data](#9-initial-data)
10. [Project Structure](#10-project-structure)
11. [Important Notes & Considerations](#11-important-notes--considerations)
12. [Troubleshooting](#12-troubleshooting)

---

## 1. Project Overview

This project implements a robust backend API for a delivery service. It allows for the management of delivery orders and their associated parcels. Key functionalities include secure user authentication using JSON Web Tokens (JWT), bulk order processing via CSV file uploads, and flexible retrieval of delivery data with filtering and pagination.

## 2. Key Features

* **User Authentication:** Secure login using JWTs.
* **Delivery Order Management:**
    * Retrieve all delivery orders scheduled for the current date.
    * Filter delivery orders by vendor name and/or specific date.
* **Parcel Management:**
    * Upload bulk parcel details via CSV files, creating associated delivery orders.
    * "In-memory" parcel creation (for demonstration, not persisted).
* **Data Persistence:** Uses an in-memory H2 database for development, with automatic schema generation.
* **File Storage:** Stores uploaded CSV files locally.
* **Pagination:** All data retrieval endpoints support pagination for efficient data handling.

## 3. Technology Stack

* **Backend:** Java 17+
* **Framework:** Spring Boot 3.x
* **Database:** H2 (In-memory for development)
* **ORM:** Spring Data JPA / Hibernate
* **Security:** Spring Security (JWT-based authentication)
* **Dependency Management:** Maven
* **JSON Web Tokens:** JJWT Library
* **CSV Parsing:** OpenCSV Library
* **Utility:** Lombok

## 4. Prerequisites

Before you begin, ensure you have the following software installed on your system:

* **Java Development Kit (JDK) 17 or higher:**
    * Verify by running: `java -version` and `javac -version`
* **Apache Maven 3.6.0 or higher:**
    * Verify by running: `mvn -v`
* **Postman (or Insomnia/curl):** For manual API testing.
    * Download from: [https://www.postman.com/downloads/](https://www.postman.com/downloads/)
* **An IDE (Integrated Development Environment):** (Optional for automated runs, but recommended for development)
    * **IntelliJ IDEA (Recommended):** Ensure you have the Lombok Plugin installed and "Enable annotation processing" checked in `Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors`.
    * **Eclipse:** Ensure Lombok is properly installed and annotation processing is enabled.

## 5. Getting Started

Follow these steps to get the Delivery Service API up and running on your local machine.

### Cloning the Repository

First, clone the project repository to your local machine:

```bash
git clone <https://github.com/S0hamm/tech_eazy_backend_S0hamm.git>
cd deliveryservice

Building the Project
Navigate to the root directory of the project (where pom.xml is located) and build the application using Maven. This step compiles the code and downloads all required dependencies.

mvn clean install
Markdown

# Delivery Service API

This repository contains the backend implementation of a Delivery Service API built with Spring Boot. It provides functionalities for managing delivery orders and parcels, including user authentication via JWT, file uploads for bulk order processing, and filtering capabilities.

The project is designed to be easily set up, built, and run, making it suitable for both manual testing and automated evaluation.

---

## Table of Contents

1.  [Project Overview](#1-project-overview)
2.  [Key Features](#2-key-features)
3.  [Technology Stack](#3-technology-stack)
4.  [Prerequisites](#4-prerequisites)
5.  [Getting Started](#5-getting-started)
    * [Cloning the Repository](#cloning-the-repository)
    * [Building the Project](#building-the-project)
    * [Running the Application](#running-the-application)
6.  [For Automated Evaluation](#6-for-automated-evaluation)
7.  [Database Access (H2 Console)](#7-database-access-h2-console)
8.  [API Usage with Postman](#8-api-usage-with-postman)
    * [Importing the Postman Collection](#importing-the-postman-collection)
    * [Understanding Collection Variables](#understanding-collection-variables)
    * [API Endpoints Walkthrough](#api-endpoints-walkthrough)
        * [8.1. Authentication: Login User](#81-authentication-login-user)
        * [8.2. Delivery Orders & Parcels: Create Parcel (In-Memory)](#82-delivery-orders--parcels-create-parcel-in-memory)
        * [8.3. Delivery Orders & Parcels: Upload Delivery Order (CSV)](#83-delivery-orders--parcels-upload-delivery-order-csv)
        * [8.4. Delivery Orders & Parcels: Get Delivery Orders for Today](#84-delivery-orders--parcels-get-delivery-orders-for-today)
        * [8.5. Delivery Orders & Parcels: Get Filtered Delivery Orders](#85-delivery-orders--parcels-get-filtered-delivery-orders)
        * [8.6. File Download: Download Uploaded File](#86-file-download-download-uploaded-file)
9.  [Initial Data](#9-initial-data)
10. [Project Structure](#10-project-structure)
11. [Important Notes & Considerations](#11-important-notes--considerations)
12. [Troubleshooting](#12-troubleshooting)

---

## 1. Project Overview

This project implements a robust backend API for a delivery service. It allows for the management of delivery orders and their associated parcels. Key functionalities include secure user authentication using JSON Web Tokens (JWT), bulk order processing via CSV file uploads, and flexible retrieval of delivery data with filtering and pagination.

## 2. Key Features

* **User Authentication:** Secure login using JWTs.
* **Delivery Order Management:**
    * Retrieve all delivery orders scheduled for the current date.
    * Filter delivery orders by vendor name and/or specific date.
* **Parcel Management:**
    * Upload bulk parcel details via CSV files, creating associated delivery orders.
    * "In-memory" parcel creation (for demonstration, not persisted).
* **Data Persistence:** Uses an in-memory H2 database for development, with automatic schema generation.
* **File Storage:** Stores uploaded CSV files locally.
* **Pagination:** All data retrieval endpoints support pagination for efficient data handling.

## 3. Technology Stack

* **Backend:** Java 17+
* **Framework:** Spring Boot 3.x
* **Database:** H2 (In-memory for development)
* **ORM:** Spring Data JPA / Hibernate
* **Security:** Spring Security (JWT-based authentication)
* **Dependency Management:** Maven
* **JSON Web Tokens:** JJWT Library
* **CSV Parsing:** OpenCSV Library
* **Utility:** Lombok

## 4. Prerequisites

Before you begin, ensure you have the following software installed on your system:

* **Java Development Kit (JDK) 17 or higher:**
    * Verify by running: `java -version` and `javac -version`
* **Apache Maven 3.6.0 or higher:**
    * Verify by running: `mvn -v`
* **Postman (or Insomnia/curl):** For manual API testing.
    * Download from: [https://www.postman.com/downloads/](https://www.postman.com/downloads/)
* **An IDE (Integrated Development Environment):** (Optional for automated runs, but recommended for development)
    * **IntelliJ IDEA (Recommended):** Ensure you have the Lombok Plugin installed and "Enable annotation processing" checked in `Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors`.
    * **Eclipse:** Ensure Lombok is properly installed and annotation processing is enabled.

## 5. Getting Started

Follow these steps to get the Delivery Service API up and running on your local machine.

### Cloning the Repository

First, clone the project repository to your local machine:

```bash
git clone <repository-url>
cd deliveryservice
(Replace <repository-url> with the actual URL of your Git repository.)

Building the Project
Navigate to the root directory of the project (where pom.xml is located) and build the application using Maven. This step compiles the code and downloads all required dependencies.

Bash

mvn clean install
Expected Output: You should see BUILD SUCCESS in your console.

Running the Application
The application can be run using Maven from the command line, which is the preferred method for automated environments.

From Terminal (Recommended for Automation)
Open your terminal or command prompt.

Navigate to the root directory of the project (deliveryservice).

Execute the following Maven command:
mvn spring-boot:run

Expected Application Readiness:
The console will display extensive logs during startup. The application is fully initialized and ready to accept API requests when you see a message similar to:
Started DeliveryServiceApplication in X.XXX seconds (process running for Y.YYY)
This confirms the application is successfully running on http://localhost:8080.

From IDE (For Development)
1. Open the deliveryservice project in your IDE (e.g., IntelliJ IDEA).

2. Navigate to src/main/java/com/example/deliveryservice/DeliveryServiceApplication.java.

3. Locate the main method (public static void main(String[] args)).

4. Click the green "Run" button (or play icon) next to the main method.

6. For Automated Evaluation
This section outlines key aspects relevant for automated testing and evaluation of the application.

  .Startup Command: The application can be reliably started using mvn spring-boot:run from the     project root.

  .Application Readiness: The application is fully operational and ready to receive HTTP           requests once the "Started DeliveryServiceApplication..." log message appears in the console.

  .Default Port: The application listens on http://localhost:8080.

  .In-Memory Database: An in-memory H2 database is used. It is automatically initialized with      the necessary schema and pre-populated data (users, vendors) upon every application start.      No external database setup or migration is required.

  .Initial Data: Refer to the Initial Data section for pre-configured user credentials and         vendor information that can be used for automated login and subsequent API calls.

  .No Manual Intervention: The application starts without requiring any manual input or            interaction.

  .API Endpoints: All required API endpoints are exposed and can be accessed via standard HTTP     requests. Refer to the API Usage with Postman section for endpoint details, which can be        translated into automated test scripts (e.g., using curl or a testing framework).

7. Database Access (H2 Console)
The application uses an in-memory H2 database for development. All data will be lost when the application restarts.

You can access the H2 database console to view and manage data:

 1. Ensure the application is running.

 2. Open your web browser and go to: http://localhost:8080/h2-console

 3. On the H2 Console login page, use the following credentials:

   . JDBC URL: jdbc:h2:mem:deliverydb

   . User Name: sa

   . Password: (leave blank)

 4. Click "Connect".

 5. You should see the database schema with tables like USERS, VENDORS, DELIVERY_ORDERS, and        PARCELS. You can run SQL queries (e.g., SELECT * FROM USERS;) to inspect the data.

8. API Usage with Postman
A Postman Collection has been provided to help you easily test all the API endpoints manually.

Importing the Postman Collection
 1. Exported Collection Location: The Postman Collection JSON file (Delivery Service                API.postman_collection.json) is located in the resources/ directory at the root of this         project.

 2. Import into Postman:

    . Open Postman.

    . Click on the "Import" button (usually at the top left).

    . Select "File" and then browse to the resources/ folder in your cloned project.

    . Select Delivery Service API.postman_collection.json and click "Open".

    . The collection named Delivery Service API will appear in your "Collections" sidebar.

Understanding Collection Variables
The Postman Collection uses variables to simplify testing:

 . baseUrl: Set to http://localhost:8080. This allows you to easily change the base URL if your    application runs on a different port or host.

 . jwtToken: This variable will be automatically populated after a successful login. It stores     the JWT token for use in subsequent authenticated requests.

API Endpoints Walkthrough
Follow these steps in Postman to test each endpoint. Ensure your Spring Boot application is running before proceeding.

8.1. Authentication: Login User
This request obtains a JWT token, which is required for accessing most other API endpoints.

 . Folder: Authentication

 . Request Name: Login User

 . Method: POST

 . URL: {{baseUrl}}/api/auth/login

 . Headers:

    . Content-Type: application/json

 . Body (raw, JSON):

 {
    "username": "user",
    "password": "password"
 }
(You can also use admin/admin or myntra_vendor/vendorpass)

 . Post-response Script (Tests tab): This request has a script in its "Tests" tab that             automatically extracts the token from the response and saves it to the jwtToken collection      variable.

 . Action: Click "Send".

 . Expected Response: HTTP 200 OK with a JSON body containing the token. Verify the token is       saved by checking the jwtToken collection variable.

8.2. Delivery Orders & Parcels: Create Parcel (In-Memory)
This endpoint demonstrates creating a parcel object in memory; it is not persisted to the database.

Folder: Delivery Orders & Parcels

Request Name: Create Parcel (In-Memory)

Method: POST

URL: {{baseUrl}}/api/delivery-orders/parcels/in-memory

Headers:

Authorization: Bearer {{jwtToken}}

Content-Type: application/json

Body (raw, JSON):

{
    "trackingNumber": "INMEM001",
    "recipientName": "Jane Doe",
    "recipientAddress": "42 Wallaby Way, Sydney",
    "pincode": "200001",
    "weight": 0.75
}
Action: Click "Send".

Expected Response: HTTP 200 OK with a JSON body representing the parcel, where id will be null.

8.4. Delivery Orders & Parcels: Get Delivery Orders for Today
Retrieves all delivery orders scheduled for the current date.

Folder: Delivery Orders & Parcels

Request Name: Get Delivery Orders for Today

Method: GET

URL: {{baseUrl}}/api/delivery-orders/today?page=0&size=10 (Adjust page and size for pagination)

Headers:

Authorization: Bearer {{jwtToken}}

Action: Click "Send".

Expected Response: HTTP 200 OK with a paginated JSON list of DeliveryOrderResponse objects for today's date.

8.5. Delivery Orders & Parcels: Get Filtered Delivery Orders
Retrieves delivery orders based on specified vendor name and/or date.

Folder: Delivery Orders & Parcels

Request Name: Get Filtered Delivery Orders

Method: GET

URL: {{baseUrl}}/api/delivery-orders?vendorName=Myntra&date=2025-07-26&page=0&size=10

Query Parameters:

vendorName: (Optional) Filter by vendor name (e.g., Myntra, Flipkart).

date: (Optional) Filter by specific delivery date in YYYY-MM-DD format.

page: (Optional) Page number (default 0).

size: (Optional) Number of items per page (default 20, but recommended to specify).

Headers:

Authorization: Bearer {{jwtToken}}

Action: Click "Send".

Expected Response: HTTP 200 OK with a paginated JSON list of DeliveryOrderResponse objects matching the filters.

8.6. File Download: Download Uploaded File
This endpoint allows downloading the original CSV file that was uploaded. The link is provided in the originalFileDownloadLink field of the Upload Delivery Order response.

Folder: Delivery Orders & Parcels (or create a new "File Download" folder)

Request Name: Download Uploaded File

Method: GET

URL: Copy the originalFileDownloadLink from a successful Upload Delivery Order response (e.g., {{baseUrl}}/api/files/a2b3c4d5-e6f7-8901-2345-67890abcdef0_my_parcels.csv).

Headers:

Authorization: Bearer {{jwtToken}}

Action: Click "Send".

Expected Response: The file will be downloaded (or displayed in Postman's response body if it's text-based).

9. Initial Data
Upon application startup, the DataLoader.java class (located in src/main/java/com/example/deliveryservice/config/) automatically populates the H2 database with the following initial data:

Users:

Username: user | Password: password | Roles: USER

Username: admin | Password: admin | Roles: ADMIN, USER

Username: myntra_vendor | Password: vendorpass | Roles: VENDOR_USER

Vendors:

Name: Myntra | Subscription Type: PRIME

Name: Flipkart | Subscription Type: NORMAL

These users and vendors are available immediately for testing.

10. Project Structure
The project follows a standard Spring Boot Maven layout:

deliveryservice/
├── pom.xml                                  # Maven Project Configuration
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/deliveryservice/ # Base package
│   │   │       ├── DeliveryServiceApplication.java # Main application entry point
│   │   │       ├── config/                  # Spring configurations (Security, DataLoader)
│   │   │       ├── controller/              # REST API endpoints
│   │   │       ├── dto/                     # Data Transfer Objects
│   │   │       ├── enums/                   # Enumerations
│   │   │       ├── model/                   # JPA Entities (Database Models)
│   │   │       ├── repository/              # Spring Data JPA Repositories
│   │   │       ├── security/                # JWT-related components
│   │   │       └── service/                 # Business Logic Services
│   │   └── resources/
│   │       └── application.properties       # Application configuration (H2, JWT, file storage)
│   └── test/                                # Unit and Integration Tests (currently empty or disabled)
└── resources/                               # Project-level resources (e.g., Postman Collection)
    └── Delivery Service API.postman_collection.json

11. Important Notes & Considerations
In-Memory Database: The H2 database is in-memory (jdbc:h2:mem:deliverydb). This means all data will be lost every time the application is stopped and restarted. For persistent data, you would switch to a file-based H2 database or an external database (e.g., PostgreSQL, MySQL).

JWT Secret: The jwt.secret in application.properties is hardcoded for demonstration. In a production environment, this secret must be a highly secure, randomly generated string stored securely (e.g., environment variables, secret management service), and never committed directly to source control.

File Storage: Uploaded files are stored locally in a ./uploads directory relative to where the application is run. For production, this should be configured to use cloud storage (e.g., AWS S3, Google Cloud Storage).

Security: While basic JWT authentication is implemented, a production application would require more comprehensive security measures, including input validation, rate limiting, and robust error handling.

Error Handling: Basic exception handling is in place. For production, consider global exception handlers with more detailed, user-friendly error messages.

Logging: Basic logging is configured. For production, integrate a robust logging framework like Logback with proper log levels and rotation.

12. Troubleshooting
"Failed to load ApplicationContext" / "UnsatisfiedDependencyException":

Check application.properties: Ensure there are no comments on the same line as property values (e.g., jwt.expiration=86400000#comment is wrong). Comments must be on a new line.

Check final keywords: Ensure all injected fields in classes using @RequiredArgsConstructor (like DataLoader, ApplicationConfig, JwtAuthenticationFilter, JwtService) are declared as private final.

IDE Annotation Processing: If mvn clean install succeeds but your IDE shows errors, ensure "Enable annotation processing" is checked in your IDE settings (IntelliJ: Settings -> Build, Execution, Deployment -> Compiler -> Annotation Processors). Rebuild/Clean project and restart IDE.

"Access to localhost denied" for H2 Console:

Ensure spring.h2.console.enabled=true in application.properties.

In SecurityConfig.java, ensure requestMatchers("/h2-console/**").permitAll() and http.headers(headers -> headers.frameOptions(frameOptions -> headers.frameOptions().disable())); are present.

401 Unauthorized:

Make sure you have successfully run the Login User request in Postman and that the jwtToken collection variable is populated.

Verify the Authorization header in your requests is Bearer {{jwtToken}}.

Check if your token has expired (default is 24 hours). If so, perform a new login.

400 Bad Request:

Check the JSON body or form-data parameters you are sending. Ensure they match the expected DTO structure and data types.

Look at the application logs for validation errors or specific reasons for the bad request.

500 Internal Server Error:

This indicates an unhandled exception on the backend. Check your Spring Boot application's console for the full stack trace. It will provide details on where the error occurred.
