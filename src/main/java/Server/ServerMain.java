package Server;

import java.net.*;
import java.io.*;
public class ServerMain{
    public static void main(String args[]) {
        DataBase dataBase = new DataBase();
        dataBase.connect();
        try {
            ServerSocket server = new ServerSocket(1402);
            System.out.println("Server started");

            int i = 0;
            while (true)
            {
                i++;
                System.out.println("Waiting for a client ...");
                Socket socket = server.accept();
                System.out.println("Client accepted "+ socket.getRemoteSocketAddress());
                clientHandler clientHandler = new clientHandler(socket, dataBase, i);
                clientHandler.start();
            }
        } catch (IOException i) {
            System.out.println(i);
        }

        dataBase.closeConnection();
        System.out.println("database closed");
    }
}