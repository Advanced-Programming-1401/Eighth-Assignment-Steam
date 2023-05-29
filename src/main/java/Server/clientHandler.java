package Server;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.Statement;

public class clientHandler implements Runnable {
    private  Socket socket  = null;
    private  BufferedReader input = null;
    private  PrintWriter out = null;
    private static DataBase dataBase = new DataBase();

    public clientHandler(Socket socket, DataBase dataBase) {
        this.socket = socket;
        try {
            input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out  = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.dataBase = dataBase;
    }
    public void run(){
        try {
            String line = "";
            boolean flag = true;
            while (flag) {
                line = input.readLine();
                System.out.println("Server get order: "+line);
                switch (line) {

                    case "usernameExist":
                        String username = input.readLine();
                        if (usernameExist(username) == true) out.println("true");
                        else out.println("false");
                        break;

                    case "exit":
                        flag = false;
                        break;

                    case "signUp":
                        String jsonString = input.readLine();
                        signUp(jsonString);
                        System.out.println("Create new acount");
                        break;

                    case "checkPass":
                        String usernam = input.readLine();
                        String password = input.readLine();
                        if (checkPass(usernam, password) == true) out.println("true");
                        else out.println("false");
                        break;


                    default:
                        break;
                }

            }
        }catch (IOException i) {
            System.out.println(i);
        }
    }

    public static boolean usernameExist(String username) {
        try {
            String query = "SELECT EXISTS(SELECT 1 FROM accounts WHERE username = ?)";
            PreparedStatement stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void signUp(String jsonString){

        JSONObject jsonObj = new JSONObject(jsonString);
        String query = "INSERT INTO accounts ( username, password, date_of_birth) values(?,?,?)";

        try {
            PreparedStatement stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setString(1, jsonObj.getString("username"));
            stmt.setString(2, hashPassword(jsonObj.getString("password")) );

            DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

            Date date = dateFormat.parse(jsonObj.getString("date"));
            stmt.setDate(3,  new java.sql.Date(date.getTime()) );
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


    }
    public static boolean checkPass(String username ,String password){
        try {
            String query="select password from accounts where username = ?";
            PreparedStatement stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setString(1, username);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            if (resultSet.getString(1).equals(hashPassword(password))) return true;
            else return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
}
