package Client;

import Shared.*;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.sql.Date;
import java.util.*;

public class ClientMain {
    static Scanner scanner = new Scanner(System.in);
    static boolean loggedIn = false;
    static User loggedInUser = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Socket socket = null;
        try {
            socket = new Socket("localhost", 8000);
            System.out.println("Connected to server!");
        } catch (IOException e) {
            System.out.println("Unable to connect to server!");
            e.printStackTrace();
        }

        assert socket != null;
        ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream objIn = new ObjectInputStream(socket.getInputStream());

        while (true){
            Request request;

            if(loggedIn){
                // printing available games
                request = new Request("showCatalog");
                objOut.writeObject(request);
                objOut.flush();

                Response response = (Response) objIn.readObject();
                Gson gson = new Gson();
                Map<String, Object> games = gson.fromJson(response.getJson(), new TypeToken<Map<String, Object>>(){}.getType());
                for(String key : games.keySet()){
                    Map<String, Object> game = (Map<String, Object>) games.get(key);
                    Double id = (Double) game.get("id");
                    System.out.println(id.intValue() + ". " + game.get("title"));
                }

                System.out.print("Enter game ID that you want to view (-1 to logout): ");
                int id = scanner.nextInt(); scanner.nextLine();

                if(id == -1){
                    loggedIn = false;
                    loggedInUser = null;
                    request = new Request("logout");
                    objOut.writeObject(request);
                    objOut.flush();
                    continue;
                }

                request = new Request("viewGame");
                request.setJson(gson.toJson(id));
                objOut.writeObject(request);
                objOut.flush();

                response = (Response) objIn.readObject();
                Game game = gson.fromJson(response.getJson(), Game.class);
                if(game == null){
                    System.out.println("wrong id!");
                }
                else {
                    System.out.println(game);
                    System.out.println("Want to download? [y/n] ");
                    String answer = scanner.nextLine();
                    if(answer.equals("y")){
                        String path = "src/main/java/Client/Downloads/";
                        request = new Request("downloadGame");
                        request.setJson(gson.toJson(id));
                        objOut.writeObject(request);
                        objOut.flush();

                        response = (Response) objIn.readObject();
                        byte[] bytes = gson.fromJson(response.getJson(), byte[].class);
                        System.out.println(bytes);
                        FileOutputStream fos = new FileOutputStream(path + game.getId() + ".png");
                        fos.write(bytes);
                        System.out.println("File Downloaded");
                        fos.close();
                    }
                }
            }

            else {
                int command = showLoginPage();
                scanner.nextLine();

                switch (command){
                    case 1:
                        request = new Request("login");

                        System.out.print("Username: ");
                        String username = scanner.nextLine();
                        System.out.print("password: ");
                        String password = scanner.nextLine();

                        HashMap<String, Object> dict = new HashMap<>();
                        dict.put("username", username);
                        dict.put("password", password);

                        ObjectMapper objectMapper = new ObjectMapper();
                        String json = objectMapper.writeValueAsString(dict);
                        request.setJson(json);

                        objOut.writeObject(request);
                        objOut.flush();

                        try{
                            Response response = (Response) objIn.readObject();
                            Gson gson = new Gson();
                            if(response.getMessage().equals("Logged In successfully")){
                                System.out.println("changing boolean");
                                loggedIn = true;
                                loggedInUser= new User();
                                loggedInUser = gson.fromJson(response.getJson(), User.class);
                            }
                            System.out.println(response.getMessage());
                        }
                        catch (Exception e){
                            e.printStackTrace();
                        }
                        break;

                    case 2:
                        request = new Request("newAccount");

                        System.out.print("Username: ");
                        username = scanner.nextLine();
                        System.out.print("password: ");
                        password = scanner.nextLine();
                        System.out.print("Date Of Birth (yyyy-MM-dd): ");
                        String date = scanner.nextLine();
                        Date inputDate = Date.valueOf(date);

                        User user = new User();
                        user.setUsername(username);
                        user.setPassword(password);
                        user.setDate(inputDate);

                        objectMapper = new ObjectMapper();
                        json = objectMapper.writeValueAsString(user);
                        request.setJson(json);

                        objOut.writeObject(request);
                        objOut.flush();

                        Response response = (Response) objIn.readObject();
                        System.out.println(response.getMessage());
                        break;
                }
            }
        }


    }

    public static int showLoginPage(){
        System.out.println("1. Login");
        System.out.println("2. Sign Up");
        System.out.print("-> ");
        int command = scanner.nextInt();
        return command;
    }

    public static int showUserMenu(){
        System.out.println("1. View Games");
        System.out.println("2. Sign Up");
        System.out.print("-> ");
        int command = scanner.nextInt();
        return command;
    }
}
