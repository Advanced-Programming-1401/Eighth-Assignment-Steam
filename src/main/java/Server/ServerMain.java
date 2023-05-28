package Server;

import java.net.*;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerMain{
    private static Socket  socket   = null;
    private ServerSocket server   = null;
    private static DataInputStream input  =  null;
    private DataBase dataBase = null;
    
    public ServerMain(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a client ...");

            socket = server.accept();
            System.out.println("Client accepted");

            input = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException i) {
            System.out.println(i);
        }
    }

//            String line = "";
//            while (!line.equals("Over"))
//            {
//                line = input.readUTF();
//                System.out.println(hashPassword(line));
//            }

    public static boolean usernameExist(String username){

    }
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // handle exception
            return null;
        }
    }
    public static void ServerClose(){
        System.out.println("Closing connection");

        try {
            socket.close();
            input.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String args[])
    {
        ServerMain server = new ServerMain(5000);
    }
}