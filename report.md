# Steam simulation

## Introduction

In today's fast-paced world, the popularity of online gaming has skyrocketed, and Steam is at the forefront of the digital gaming revolution. Steam is a popular gaming platform that offers users a vast collection of games and gaming-related services. This project aims to simulate the Steam gaming service. The simulation includes features such as creating new account and loggin in service, viewing and downloading a game, which is simply represented by an image of its cover. In this report, we will discuss the design, implementation, and testing of the Steam gaming service simulation, providing details on the project's various components and their functionality. Ultimately, our goal is to create a simulation that accurately represents the Steam gaming service and provides users with an enjoyable and engaging gaming experience.

## Desgin and implementation

- Since this project is a simple simulation of Steam gaming service, it uses images as a representation of games, which are stored in server. Each image comes with its own uniqe text file which stores information related to the game.

- When running the server for the first time, it imports all the games data to the database which is a PostgreSQL database. The servers connects to database when it's started and then uses only one connection to communicate with the database through out the running time.

- The server uses multi-threading to allow multiple clients connect to the server simultaneously. The server listens for requests and create new thread for a connection when it's established.

- To communicate between server and the client, I have written two classes named Request and Response. When the client has a request, it instantiates a Request object and writes it over web socket and sends it to the server. The sever also uses a class called Response to send back data to client over web socket.

## Testing and evaluation

- In order to test my program, I tried diffrent inputs and tracked the program flow. I used breakpoints and other tools to achive my goal.

## Conclusion

In conclusion, this project was a valuable learning experience that allowed me to gain a deeper understanding of Java programming and multi threading concepts and web sockets.

Throughout the development process, I encountered several challenges, including design considerations, testing and debugging. However, I was able to overcome these obstacles through careful planning, research, and collaboration with my peers.

Although this project was successful in achieving its objectives, there is still room for improvement. Future work could include expanding the functionality of the application, improving the user interface and adding GUI, and optimizing performance.

Overall, this project was a rewarding experience that allowed me to apply my skills and knowledge to a practical problem. I am confident that the lessons learned from this project will benefit me in future endeavors, and I look forward to continuing to develop my skills as a software developer.
