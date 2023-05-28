package Client;
import java.net.*;
import java.io.*;
import java.util.Scanner;


public class ClientMain
{
    private static Socket socket            = null;
    private static DataInputStream  input   = null;
    private static DataOutputStream out     = null;

    public ClientMain(String address, int port)
    {
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected to server");

            input  = new DataInputStream(System.in);
            out  = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
        RunMenu();

//        String line = "";
//
//        // keep reading until "Over" is input
//        while (!line.equals("Over"))
//        {
//            try
//            {
//                line = input.readLine();
//                out.writeUTF(line);
//            }
//            catch(IOException i)
//            {
//                System.out.println(i);
//            }
//        }

    }
    public static void ClientClose(){
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void RunMenu(){
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("1.Sign in\n2.Sign up\n3.exit\n->");
            String order = in.nextLine();

            //sign in
            if (order.equals("1")) {
                System.out.print("Enter username\n->");
                String username= in.nextLine();


            }
            //sign up
            else if (order.equals("2")) {

            }
            //exit
            else if (order.equals("3")) {
                //Se rver should close too
                ClientClose();
            }else System.out.println("Wrong order!");
        }

    }
    public static void main(String args[])
    {
        ClientMain client = new ClientMain("127.0.0.1", 5000);
    }
}
