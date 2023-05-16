# Eighth-Assignment-Steam

## Introduction
In this assignment you are tasked with creating an application designed to handle the management and download of video game data. This assignment covers multiple topics in socket programming and database management.
The Download Manager App is a Java-based application designed to facilitate the management and retrieval of video games over a local network. The app consists of separate server and client components, connected to each other using socket programming. The server component maintains a database to store information about video games and user accounts. The app allows clients to request a list of available video games, obtain detailed information about them, and download the corresponding game files.

## Objectives
- Review the concepts of socket programminf and database management
- Provide a centralized server to store information about video games and user accounts.
- Allow clients to create user accounts, login, and access video game files based on their account privileges.
- Implement secure password storage by hashing the passwords in the database.
- Enable clients to request a list of available video games.
- Allow clients to retrieve detailed information about specific video games.
- Provide the ability for clients to download video game files from the server.

## Tasks
1. Fork this repository and clone the fork to your local machine. Ensure to create a new Git branch before starting your work
2. Design the application's architecture
<br>The Download Manager App consists of two main components: the server and the client.

    2.1. Server
    <br>The server component is responsible for handling client requests, managing the database, and serving video game files. It utilizes socket programming to establish a connection with the clients over a local network. The server incorporates a database to store information about video games and user accounts.

    The server's architecture includes the following modules:

    - Socket Listener: Listens for incoming client connections and delegates requests to the appropriate handlers.
    - Request Handlers: Process client requests and interact with the database to fetch requested data or serve files.
    - Database Manager: Interacts with the underlying database system to perform CRUD (Create, Read, Update, Delete) operations.
    - File Manager: Manages the collection of video game files, including their storage, retrieval, and file address management.


    2.2. Client
    <br>The client component provides the user interface for interacting with the Download Manager App. It allows users to create accounts, login, browse available video games, and download files. The client establishes a connection with the server using socket programming and communicates through a series of request-response interactions.

    The client's architecture includes the following modules:

    - User Interface: Provides a graphical or command-line interface for users to interact with the app.
    - Socket Connection: Establishes a connection with the server over the local network using socket programming.
    - Request Generator: Constructs and sends requests to the server based on user actions or commands.
    - Response Handler: Receives and processes responses from the server, displaying the appropriate information or files to the user.
    
3. Database Design
The server's database plays a central role in storing essential data for the Download Manager App. It consists of the following tables:

    3.1. Games
    <br>This table stores information about video games, including their attributes.

    Column	Type	Description
    id	INT	Unique identifier for the game
    name	VARCHAR	Name of the video game
    genre	VARCHAR	Genre of the video game
    platform	VARCHAR	Platform(s) supported by the game
    size	INT	Size of the game in megabytes
    file_path	VARCHAR	File path of the game


    3.2 Accounts
    <br>This table stores information about user accounts.

    Column	Type	Description
    id	INT	Unique identifier for the account
    username	VARCHAR	Username of the account
    password	VARCHAR	Hashed password of the account


5. Security
<br>To ensure the security of user accounts ,the Download Manager App implements password hashing. When a user creates an account or changes their password, the app applies a strong hashing algorithm (e.g., bcrypt) to transform the password into a secure, irreversible form. The hashed passwords are stored in the database, providing an additional layer of protection.

3. Commit your changes and push your commits to your fork on Github. Create a pull request (assigned to your mentor) to merge your changes to the main branch of your fork on Github.


## Notes
- You are allowed to modify or delete all of the provided code. Do not limit yourself to the existing classes and methods.
- You are NOT allowed to change the contents of the `resources` folder located on the server side.
- Your report should include details on the solution you chose for each exercise (and why you chose it). Try to focus on the `Calculate Pi` exercise and explain the mathematical algorithm(s) you tried in detail.


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
