package Server;

import org.json.JSONObject;
import java.io.*;
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

public class clientHandler extends Thread {
    private Socket socket  = null;
    private BufferedReader input = null;
    private PrintWriter out = null;
    private DataBase dataBase = new DataBase();
    private int i = 0;

    public clientHandler(Socket socket,DataBase dataBase,int i) {
        this.i=i;
        this.socket = socket;
        this.dataBase = dataBase;
        try {
            input  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out  = new PrintWriter(socket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void run(){
        while (menu()){}
    }

    public boolean menu() {
        try {
            String order = input.readLine();
            System.out.println("Server get order: "+order+" from client: "+i);
            switch (order) {

                case "usernameExist":
                    String username = input.readLine();
                    if (usernameExist(username) == true) out.println("true");
                    else out.println("false");
                    break;

                case "exit":
                    return false;

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

                case "listOfGames":
                    out.println(listOfGames());
                    break;

                case "checkGameName":
                    String GameName = input.readLine();
                    if (gameExist(GameName) == true) out.println("true");
                    else out.println("false");
                    break;


                case "Download":
                    String gameName= input.readLine();
                    String user = input.readLine();
                    download(gameName, user);
                    break;

                case "gameInfo":
                    gameName = input.readLine();
                    gameInfo(gameName);
                    break;

                default:
                    System.out.println("Command not recognized!");
                    break;
            }

        }catch (IOException i) {
            System.out.println(i);
        }
        return true;
    }
    public  boolean usernameExist(String username) {
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
    public  boolean gameExist(String gameName){
        try {
            String query = "SELECT EXISTS(SELECT 1 FROM games WHERE title = ?)";
            PreparedStatement stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setString(1, gameName);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getBoolean(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void signUp(String jsonString){

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
    public  boolean checkPass(String username ,String password){
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
    public  String listOfGames(){
        try {

            ResultSet resultSet = dataBase.getStatement().executeQuery("select title from games;");
            String listOfGames="";
            while (resultSet.next()){
                listOfGames += "\\ ";
                listOfGames += resultSet.getString("title");
            }
            return listOfGames;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void download(String gameName, String username){
        String filePath;
        try {
            ResultSet resultSet = dataBase.getStatement().executeQuery("select file_patch from games where title = '"+gameName+"';");
            resultSet.next();
             filePath = resultSet.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            int bytes = 0;
            File file = new File(filePath);
            FileInputStream fileInputStream = new FileInputStream(file);
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            // Here we send the File to Server
            dataOutputStream.writeLong(file.length());
            // Here we  break file into chunks
            byte[] buffer = new byte[4 * 1024];
            while ((bytes = fileInputStream.read(buffer)) != -1) {
                // Send the file to Server Socket
                dataOutputStream.write(buffer, 0, bytes);
                dataOutputStream.flush();
            }
            fileInputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        AddToDownloads(gameName, username);
    }
    public  void AddToDownloads(String gameName, String userName ){
        try {
            //get user_id
            String query = "SELECT id from accounts where username = ?";
            PreparedStatement stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setString(1, userName);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            Long user_id = resultSet.getLong(1);

            //get game_id
            query = "SELECT id from games where title = ?";
            stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setString(1, gameName);
            resultSet = stmt.executeQuery();
            resultSet.next();
            String game_id = resultSet.getString(1);


            query = "SELECT COUNT(*) FROM downloads WHERE account_id = ? AND game_id = ?";
            stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setLong(1, user_id);
            stmt.setString(2, game_id);
            resultSet = stmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count > 0){
                query = "SELECT download_count from downloads where account_id = ? AND game_id = ?";
                stmt = dataBase.getConnection().prepareStatement(query);
                stmt.setLong(1, user_id);
                stmt.setString(2, game_id);
                resultSet = stmt.executeQuery();
                resultSet.next();
                int download_count = resultSet.getInt(1);
                download_count++;
                query = "UPDATE downloads SET download_count = ? WHERE account_id = ? AND game_id = ?;";
                stmt = dataBase.getConnection().prepareStatement(query);
                stmt.setInt(1,download_count);
                stmt.setLong(2, user_id);
                stmt.setString(3, game_id);
                stmt.executeUpdate();

                System.out.println("download_count update to: " + download_count);
            }
            else {
                query = "insert into downloads (account_id, game_id, download_count) values ( ? , ? , 1)";
                stmt = dataBase.getConnection().prepareStatement(query);
                stmt.setLong(1, user_id);
                stmt.setString(2, game_id);
                stmt.executeUpdate();

                System.out.println("Row add to table");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  void gameInfo(String gameName){
        PreparedStatement stmt = null;
        try {
            String query = "select * from games where title = ?";
            stmt = dataBase.getConnection().prepareStatement(query);
            stmt.setString(1, gameName);
            ResultSet resultSet = stmt.executeQuery();

            JSONObject jsonObj = new JSONObject();
            resultSet.next();
            jsonObj.put("title", resultSet.getString(2));
            jsonObj.put("developer", resultSet.getString(3));
            jsonObj.put("genre", resultSet.getString(4));
            jsonObj.put("price", resultSet.getDouble(5));
            jsonObj.put("release_year", resultSet.getInt(6));
            jsonObj.put("controller_support", resultSet.getBoolean(7));
            jsonObj.put("reviews", resultSet.getInt(8));
            jsonObj.put("size", resultSet.getInt(9));

            out.println(jsonObj);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  String hashPassword(String password) {
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
