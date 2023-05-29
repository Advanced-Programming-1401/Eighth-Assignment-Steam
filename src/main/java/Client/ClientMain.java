package Client;
import java.net.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import org.json.JSONObject;


public class ClientMain
{
    private static Socket socket = null;
    private static   BufferedReader input = null;
    private static  PrintWriter out = null;

    public ClientMain(String address, int port)
    {
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected to server");

            input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out  = new PrintWriter(socket.getOutputStream(),true);
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
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
                String username = in.nextLine();

                try {
                    out.println("usernameExist");
                    out.println(username);
                    if (input.readLine().equals("false")) System.out.println("Username doesn't exist");
                    else{
                        System.out.print("Enter password\n->");
                        String password = in.nextLine();
                        out.println("checkPass");
                        out.println(username);
                        out.println(password);
                        if (input.readLine().equals("false")) System.out.println("password is not correct");
                        else signin(username);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //sign up
            else if (order.equals("2")) {
                System.out.print("Enter username\n->");
                String username= in.nextLine();
                try {
                    out.println("usernameExist");
                    out.println(username);
                    String foo = input.readLine();
                    if (foo.equals("true")) System.out.println("Username existed");
                    else{
                        System.out.print("Enter password\n->");
                        String password= in.nextLine();
                        String date;
                        while (true){
                            System.out.print("Enter date of birthday(format : MM-dd-yyyy)\n->");
                            date= in.nextLine();
                            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                            dateFormat.setLenient(false);
                            try {
                                dateFormat.parse(date);
                                 break;
                            } catch (ParseException e){
                                System.out.println("Format is wrong!");
                            }
                        }

                        JSONObject jsonObj = new JSONObject();
                        out.println("signUp");
                        jsonObj.put("username", username);
                        jsonObj.put("password", password);
                        jsonObj.put("date",date);

                        out.println(jsonObj);
                        System.out.println("Account create");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //exit
            else if (order.equals("3")) {
                out.println("exit");
                break;
            }else System.out.println("Wrong order!");
        }

    }
    public static void signin(String username){
        
    }
    public static void main(String args[])
    {
        ClientMain client = new ClientMain("127.0.0.1", 1402);RunMenu();
        RunMenu();
        ClientClose();
        System.out.println("Client closed");
    }
}
