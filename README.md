# In the name of god
# Steam simulation
## Introduction
Steam is a digital distribution platform developed by Valve Corporation that allows users to purchase, download, and play video games on their computers. 
In this code we have simulation of steam. We have database, server and client and request, response between them. Instead playing game, users can download pictures of game
## Desgin and implementation
- Code has 4 classes  (database, serverMain, client handler and clientMain) and a folder with information and pictures of games.
 `Database` is postgresql and makes tables are made in database class. Database contains the following data:
### Games
This table stores information about video games, including their attributes.

| Column name        | Data type        | Description                                                                         |
|--------------------|------------------|-------------------------------------------------------------------------------------|
| id                 | text             | A unique identifier for the game                                                    |
| title              | text             | Title of the video game                                                             |
| developer          | text             | Name of the studio that developed the game                                          |
| genre              | text             | Genre of the video game                                                             |
| price              | double precision | Current price of the video game                                                     |
| release_year       | integer          | The game's release year                                                             |
| controller_support | boolean          | This parameter is True if the game supports controllers                             |
| reviews            | integer          | A value from 0 to 100 indicating the game's average user score on Steam (higher is better) |
| size               | integer          | Size of the game in kilobytes                                                       |
| file_path          | text             | Path of the game file stored in the `Resources` folder                              |


### Accounts
This table stores information about user accounts.

| Column name   | Data type | Description                         |
|---------------|-----------|-------------------------------------|
| id            | text      | A unique identifier for the account |
| username      | text      | Username of the account             |
| hash of password      | text      | Hashed password of the account      |
| date_of_birth | date      | The user's date of birth            |


### Downloads
This table stores information about user downloads.

| Column name    | Data type | Description                                      |
|----------------|-----------|--------------------------------------------------|
| account_id     | text      | A unique identifier for an existing account      |
| game_id        | text      | A unique identifier for an existing game         |
| download_count | Integer   | The number of times a user has downloaded a specific game |
- In code, I get connection of database and used it.

- `serverMain` has a while(true) and it listen for client. When client accept, it makes a thread and run it to client handler.
- `clientHandler` is a thread and and has menu and push clients into that. In menu it get order's client and recognized it in switch case and send it to its function.
- `clientMain` has a login menu contains signIn,signUp and exit. When user sginIn, the menu has list of games, game information, download game and exit.
- For download, serverHandler gets file path from database and breaks file into chunks and sends it to client. Then updates download_count in downloads table. Client saves file in download folder.
## Testing and evaluation
- In order to test my program, I tried diffrent inputs and tracked the program flow. I used breakpoints and other tools to achive my goal.
## Conclusion
- In conclusion, this project was a valuable learning experience that allowed me to gain a deeper understanding of Java programming and multi threading concepts and web sockets.

- Throughout the development process, I encountered several challenges, including design considerations, testing and debugging. However, I was able to overcome these obstacles through careful planning, research, and collaboration with my peers.

- Although this project was successful in achieving its objectives, there is still room for improvement. Future work could include expanding the functionality of the application, improving the user interface and adding GUI, and optimizing performance.