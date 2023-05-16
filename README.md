# Eighth-Assignment-Steam

## Introduction
In this assignment you are tasked with creating an application designed to handle the management and download of video game data. This assignment covers multiple topics in socket programming and database management.
The Download Manager App is a Java-based application designed to facilitate the management and retrieval of video games over a local network. The app consists of separate server and client components, connected to each other using socket programming. The server component maintains a database to store information about video games and user accounts. The app allows clients to request a list of available video games, obtain detailed information about them, and download the corresponding game files.

## Objectives
- Review the concepts of socket programming and database management
- Provide a centralized server to store information about video games and user accounts.
- Allow clients to create user accounts, login, and access video game files..
- Use a hashing algorithm to implement secure password storage.
- Enable clients to request a list of available video games.
- Allow clients to retrieve detailed information about specific video games.
- Provide the ability for clients to download video game files from the server.

## Tasks
1. Fork this repository and clone the fork to your local machine. Ensure to create a new Git branch before starting your work
2. Design the application's architecture
<br>The Download Manager App consists of two main components: the **Server** and the **Client**.

    #### 2.1. Server
    The server component is responsible for handling client requests, managing the database, and serving video game files. It utilizes socket programming to establish a connection with the clients over a local network. The server incorporates a database to store information about video games and user accounts.

    The server's architecture includes the following modules:

    - Socket Listener: Listens for incoming client connections and delegates requests to the appropriate handlers.
    - Request Handlers: Process client requests and interact with the database to fetch requested data or serve files.
    - Database Manager: Interacts with the underlying database system to perform CRUD (Create, Read, Update, Delete) operations.
    - File Manager: Manages the collection of video game files, including their storage, retrieval, and file address management.


    #### 2.2. Client
    The client component provides the user interface for interacting with the Download Manager App. It allows users to create accounts, login, browse available video games, and download files. The client establishes a connection with the server using socket programming and communicates through a series of request-response interactions.

    The client's architecture includes the following modules:

    - User Interface: Provides a graphical or command-line interface for users to interact with the app.
    - Socket Connection: Establishes a connection with the server over the local network using socket programming.
    - Request Generator: Constructs and sends requests to the server based on user actions or commands.
    - Response Handler: Receives and processes responses from the server, displaying the appropriate information or files to the user.
    
3. Create a Database to store the app's data persistently
The server's database plays a central role in storing essential data. You are allowed to use a SQL-based database or a NoSQL database (such as MongoDB). The database must consist of the following tables:

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
    | reviews  | text  |  Text description of the game's average reveiw score |
    | size  | integer  |  Size of the game in kilobytes  |
    | file_path | text  |  Path of the game file stored in the resources folder |


    #### 3.2 Accounts
    This table stores information about user accounts.
    
    | Column name | Data type | Description |
    | ------------- | ------------- | ------------- |
    | id  | text  |  A unique identifier for the account |
    | username  | text  |  Username of the account  |
    | password | text  |  Hashed password of the account  |
    
    
    #### 3.3 Downloads
    This table stores information about user downloads.
    
    | Column name | Data type | Description |
    | ------------- | ------------- | ------------- |
    | account_id  | text  |  A unique identifier for an existing account |
    | game_id  | text  |  A unique identifier for an existing game |
    | last_download_date | date  |  Hashed password of the account  |
    | download_count | Integer  |  The amount of times a user has downloaded a game  |
    
    
Regardless of how you implement the database, it must be able to answer questions such as:
- How many accounts have been created?
- What is the average price of the available video games?
- How many times in total has a game been downloaded?
- How many DISTINCT users have downloaded a game?
     

4. Security
- To ensure the security of user accounts, the app should implement password hashing. When a user creates an account or changes their password, the app applies a hashing algorithm (e.g., bcrypt) to transform the password into a secure, irreversible form. The hashed passwords are stored in the database, providing an additional layer of protection.
- Note that when an existing user tries to login, the password they provide must be hashed again and compared to the hashed password that is stored in the database. Login is succesful only if the two hashed values are equal.
- You can add the `JBCrypt` package to your dependencies and use the hash functions provided in the package.

5. Resources
<br>

6. Commit your changes and push your commits to your fork on Github. Create a pull request (assigned to your mentor) to merge your changes to the main branch of your fork on Github.


## Notes
- You are allowed to modify or delete all of the provided code. Do not limit yourself to the existing classes and methods.
- You are NOT allowed to change the contents of the `resources` folder located on the server side.
- Your report should include details on the architecture you used for the app, as well as the design process of the your database. Provide adequate explanation regarding each table. Specify the primary key chosen for each table and how you ensured it would be unique.

## Bonus Objectives
1.
2.
3. 

## Evaluation
- Your code should compile and run without any errors
- Your code should be well-organized, readable, properly commented and should follow clean code principles
- Your Database should be well structured with as little data redundancy as possible
- You should use Git for version control and include meaningful commit messages


## Submission
- Push your code to your fork on Github
- Upload a backup of your database to your fork on GitHub
- Upload your report to your fork on GitHub


The deadline for submitting your code is Wednesday, May 31 (10th of Khordad). Good luck, happy coding and à bientôt!
