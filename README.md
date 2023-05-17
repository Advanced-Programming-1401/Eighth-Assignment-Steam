# Eighth-Assignment-Steam

<p align="center">
  <a href="https://store.steampowered.com">
    <img src="https://store.cloudflare.steamstatic.com/public/shared/images/header/logo_steam.svg?t=962016">
  </a>
</p>

## Introduction

In this assignment you are tasked with creating a Java application designed to handle the management and download of video game data, similar to Steam. Steam is an online platform that distributes video game in a digital format. The application you will develop is simpler in design, as it does not have any purchase or trade features.

<br>The app consists of separate server and client components, connected to each other using socket programming. The server component maintains a database to store information about video games and user accounts. The client side of the app allows clients to request a list of available video games, obtain detailed information about them, and download the corresponding game files using a download manager.

## Objectives
- Review the concepts of socket programming and database management.
- Create a local server to store information about video games and user accounts.
- Allow clients to create accounts, login, and logout.
- Use a hashing algorithm to implement secure password storage.
- Enable clients to download video game files (represented by PNG files for simplicity) from the server.

## Tasks
Ensure to Fork this repository and clone the fork to your local machine. Create a new Git branch before starting your work.

### 1. Design the application's client-server API
The client and server sides of the app should be able to communicate with each other in a predetermined manner. In order to achieve this, a series of Request-Response interactions between the client and the server can be implemented.

#### 1.1. Request
A Request must have these features:
- A Request is sent from the client to the server over the network. 
- You should create different types of Requests for different actions. 
- A client can Request for a list of available games, info about a specific game, or to download a game. Creating an account, logging in and logging out can also be considered Requests.
- It is up to you to design the Request's format. For example, A Request can be a JSON string which is easy to send on a socket.

#### 1.2. Response
A Response must have these features:
- A Response is sent from the client to the server over the network. 
- You should create different Response types corresponding to the received Request. 
- Attach appropriate data to the Response based on the Request. A Response might contain the data a user has requested, or it might just be boolean indicating the result of a previously sent Request (such as confirming a user's login attempt by returning a True boolean).
- It is up to you to design the Response's format. Similar to a Request, a Response can also be a JSON string.

Note that each Request received from the client must be answered with a Response from the server. 
    
### 2. **Design the application's architecture**
You must implement two main components: the the **Client** and **Server**. The server must be able to connect to a Database, and the client should have a download manager component. The client and server must be connected through the use of a socket connection.

#### 2.1. Client
The client component must provide a graphical or command-line interface for users to interact with the app. It should allow users to:
- Create accounts by providing a username, a password, and a date of birth (DOB)
- Login and logout securely (more info in [section 5](#5-use-a-hashing-algorithm-to-provide-security-for-user-accounts))
- Browse the available video game catalogue
- View each individual game's details
- Download video game files and manage these downloads 

 <br>The client must provide the following functionalities:

- Socket Connection: Establish a connection with the server over the local network using socket programming.
- Request Generator: Generate Requests based on the user's input.
- Response Handler: After sending a Request, wait to receive a Response from the server. Then provide the user with appropriate info based on the response type.
- Download Manager: If a user requests to download a game, the Download Manager ensures the file is downloaded successfully and stores it in the correct format in a specified directory.


#### 2.2. Server
The server component is responsible for handling client requests, managing the database, and sending video game files to the client. Before a server is ready to accept clients, it must connect to the database to access information about video games and user accounts:
- At the start of the first run of your server, it must read data from the files located in the `Resources` folder and import it to the database. This process in explained with more detail in [section 4](#4-import-the-necessary-data-from-the-Resources-folder)).
- Your program must run a query on the database according to the received Request.
- Every time a new account is created, the account credentials must be added to the database.
- If a user requests to download a video game, you must update the number of times that the game has been downloaded by that user.

<br>The server must provide the following functionalities:

- Socket Listener: Listen for incoming client connections and redirect requests to the appropriate handlers. Once a request has been fully handled, enter listening mode again.
- Request Handlers: Process client Requests and interact with the database to fetch the requested data. You may need to create multiple handlers for various Requests.
- Database Manager: Interact with the database system to perform CRUD (Create, Read, Update, Delete) operations. ([more info in section 3](#3-create-a-Database-to-store-the-app's-data-persistently))
- Response Generator: The final step in handling a Request is to send an appropriate Response to the client. Attach the needed data to the Response based on the Request.
- Logging: Try to log every major action the server performs (e.g. accepting a client, sending a file, etc.) to simplify the debugging process.

    
### 3. **Create a Database to store the app's data persistently**
The server's database plays a central role in storing essential data. You are allowed to use a SQL-based database or a NoSQL database (such as MongoDB). Remember to add the necessary `JDBC` (java database connectivity) dependency to your project.
<br>The database must contain the following data: 
<br>**Note that you are allowed to change the structure and number of tables but make sure to save all of the mentioned attributes somewhere**

#### 3.1. Games
This table stores information about video games, including their attributes.

| Column name | Data type | Description |
| ------------- | ------------- | ------------- |
| id  | text  |  A unique identifier for the game |
| title  | text  |  Title of the video game  |
| genre | text  |  Genre of the video game  |
| price | double precision  |  Current price of the video game  |
| release_year  | integer  |  The game's release year  |
| controller_support | boolean  |  This parameter is True if the game supports controllers  |
| reviews  | integer  |  A value from 0 to 100 indicating the game's average review score (higher is better) |
| size  | integer  |  Size of the game in kilobytes  |
| file_path | text  |  Path of the game file stored in the resources folder |


#### 3.2. Accounts
This table stores information about user accounts.

| Column name | Data type | Description |
| ------------- | ------------- | ------------- |
| id  | text  |  A unique identifier for the account |
| username  | text  |  Username of the account  |
| password | text  |  Hashed password of the account  |
| date_of_birth | date | The user's database |


#### 3.3. Downloads
This table stores information about user downloads.

| Column name | Data type | Description |
| ------------- | ------------- | ------------- |
| account_id  | text  |  A unique identifier for an existing account |
| game_id  | text  |  A unique identifier for an existing game |
| download_count | Integer  |  The number of times a user has downloaded a game  |



Regardless of how you implement the database, it must be able to answer questions such as:
- How many accounts have been created?
- What is the average price of the available video games?
- How many times in total has a game been downloaded?
- How many DISTINCT users have downloaded a game?


### 4. **Import the necessary data from the `Resources` folder**
- Alongside the server component, a `Resources` folder is provided that stores 10 TXT files (each storing a game's details) and 10 PNG files (each representing a game's data). The TXT files and the PNG files are paired together based on their names (which is always the game id).
- Before the server starts accepting requests from clients, it must first import all of the game data from TXT files and store them in the database. This is a one-time only process, once the data has been successfully imported to the database, the server doesn't need to import the data on subsequent runs.
- Ensure to store each PNG file's path in the `file_path` column of the video game table. Every time a user requests to download a specific video game, the server must send the respective PNG file to the client. The client then stores that file in the `Downloads` folder
- Each TXT file is structured like this:
    - gameid
    - title
    - genre
    - price
    - release_year
    - controller_support
    - reviews
    - size

    Here's an example of a TXT file:
    >   2050650
    >   <br>Resident Evil 4
    >   <br>Survival Horror
    >   <br>59.99
    >   <br>2023
    >   <br>True
    >   <br>97
    >   <br>618


### 5. **Use a hashing algorithm to provide security for user accounts**
- To ensure the security of user accounts, the app should implement password hashing. When a user creates an account or changes their password, apply a hashing algorithm (e.g. bcrypt) to transform the password into a secure, irreversible form. The hashed passwords must then be stored in the database. DO NOT store the plain password in the database.
- Note that when an existing user tries to login, the password they provide must be hashed again and compared to the hashed password that is stored in the database. Login is successful only if the two hashed values are equal.
- You can add the `JBCrypt` package to your dependencies and use the hash functions provided in the package.


### **6. Commit your changes and push your commits to your fork on Github. Provide a backup of the database you created in the `Database` folder. Create a pull request (assigned to your mentor) to merge your changes to the main branch of your fork on Github**.


## Notes
- You are allowed to modify or delete all of the provided code. Do not limit yourself to the existing classes and methods.
- You are NOT allowed to change the contents of the `resources` folder located on the server side.
- Note that the client side of the program cannot access the database directly and must connect to the server to ask for data.
- If you decide to use JSON strings in your program, you may find the `gson` package to be useful for serializing a Java object to JSON.
- Your report should include details on the architecture you used for the app, as well as the design process of your database. Provide adequate explanation regarding each table. Specify the primary key chosen for each table and how you ensured it would be unique.


## Bonus Objectives
1. Add a GUI (Graphical User Interface) to your project. It's recommended to use JavaFX. This GUI should include all of the options offered by the command line menu you implemented earlier. Once a download is complete, display the PNG picture received from the database in the GUI.
2. Use multithreading in the server side code to allow multiple clients to connect concurrently. This can be done by creating a main listener thread that waits for connections from clients. Once a client has connected, create a new thread to handle that specific client.
3. Use multithreading to speed up the download process by creating multiple datastreams that run in parallel to each other.
4. Implement an age restriction system for video games. Prevent users who are younger than a specified age to access certain games. You can add a new age restriction column to the `Games` table.


## Evaluation
- Your code should compile and run without any errors
- Your code should be well-organized, readable, properly commented and should follow clean code principles
- Your Database should be well structured with as little data redundancy as possible
- You should use Git for version control and include meaningful commit messages


## Submission
- Push your code to your fork on Github
- Upload a backup of your database to your fork on GitHub
- Upload your report to your fork on GitHub


The deadline for submitting your code is Wednesday, May 31 (10th of Khordad). Good luck, happy coding!
