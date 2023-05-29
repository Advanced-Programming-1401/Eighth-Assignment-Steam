package Server;

import java.net.*;
import java.io.*;

public class ServerMain{
    private  Socket  socket   = null;
    private  ServerSocket server   = null;

    private static DataBase dataBase = new DataBase();

    public ServerMain(int port){
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            while (true)
            {
                System.out.println("Waiting for a client ...");
                socket = server.accept();
                System.out.println("Client accepted");
                clientHandler clientHandler = new clientHandler(socket,dataBase);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException i) {
            System.out.println(i);
        }
    }
//    public static void ServerClose(){
//        try {
//            socket.close();
//            input.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public static void main(String args[]) {
        dataBase.connect();
        ServerMain server = new ServerMain(1402);

        dataBase.closeConnection();
        System.out.println("database closed");
//        ServerClose();
//        System.out.println("server closed");
    }
}