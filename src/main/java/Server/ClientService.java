package Server;

import Shared.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.mindrot.jbcrypt.BCrypt;
import java.io.*;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientService implements Runnable{
    Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Database database;
    private User loggedInUser = null;
    private boolean isLoggedIn = false;



    public ClientService(Socket socket, Database database) throws IOException {
        this.socket = socket;
        this.database = database;

        // Initializing in and out streams
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            objIn = new ObjectInputStream(socket.getInputStream());
            objOut = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        // Waiting for request from client
        Request r;
        try {
            while ((r = (Request) objIn.readObject()) != null){
                handleRequest(r);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection Lost!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void handleRequest(Request request) throws IOException, SQLException {
        String command = request.getCommand();
        Response response = new Response();

        // remove line below
        if(isLoggedIn){
            CRUDgame crudGame = new CRUDgame(database);
            if(command.equals("logout")){
                loggedInUser = null;
                isLoggedIn = false;
                response.setMessage("Logged out!");
            }

            else if(command.equals("showCatalog")){
                List<Game> games = crudGame.getAllGames();
                System.out.println("all games:");
                System.out.println(games);
                Map<String, Object> gamesMap = new HashMap<>();

                for(Game game : games){
                    Map<String, Object> gameMap = new HashMap<>();
                    gameMap.put("title", game.getTitle());
                    gameMap.put("developer", game.getDeveloper());
                    gameMap.put("genre", game.getGenre());
                    gameMap.put("id", game.getId());
                    gameMap.put("price", game.getPrice());
                    gameMap.put("reviews", game.getReviews());
                    gameMap.put("release_year", game.getRelease_year());
                    gameMap.put("path_file", game.getPath_file());
                    gameMap.put("size", game.getSize());

                    gamesMap.put(game.getTitle(), gameMap);
                }

                Gson gson = new Gson();
                String a = gson.toJson(gamesMap);
                response.setMessage("games sent!");
                response.setJson(a);
                objOut.writeObject(response);
                objOut.flush();
            }

            else if (command.equals("viewGame")) {
                String rawJSON = request.getJson();
                Gson gson = new Gson();
                int id = gson.fromJson(rawJSON, int.class);
                Game game = crudGame.getGameByID(id);

                response.setJson(gson.toJson(game));
                objOut.writeObject(response);
                objOut.flush();
            }

            else if(command.equals("downloadGame")){
                String rawJSON = request.getJson();
                Gson gson = new Gson();
                int id = gson.fromJson(rawJSON, int.class);
                Game game = crudGame.getGameByID(id);

                String path = game.getPath_file();
                System.out.println(path);
                File file = new File(path);
                FileInputStream fileInputStream = new FileInputStream(file);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);

                byte[] bytes = new byte[(int) file.length()];
                bufferedInputStream.read(bytes, 0, bytes.length);

                response.setJson(gson.toJson(bytes));
                objOut.writeObject(response);
                objOut.flush();

                CRUDdownload cruDdownload = new CRUDdownload(database);
                cruDdownload.newDownload(game.getId(), loggedInUser.getId());
            }
        }

        else{
            CRUDuser crudUser = new CRUDuser(database);
            if(command.equals("newAccount")){
                String json = request.getJson();
                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.readValue(json, User.class);
                user.setPassword(user.hashPassword());
                crudUser.createUser(user);
                response.setMessage("User created!");
                objOut.writeObject(response);
                objOut.flush();
            }

            else if(command.equals("login")){
                String json = request.getJson();
                ObjectMapper objectMapper = new ObjectMapper();
                User user = objectMapper.readValue(json, User.class);
                System.out.println(user);
                User dbUser = crudUser.getUserByUsername(user.getUsername()).get(0);

                HashMap<String, Object> map = new HashMap<>();
                if(BCrypt.checkpw(user.getPassword(), dbUser.getPassword())){
                    loggedInUser = crudUser.getUserByUsername(user.getUsername()).get(0);
                    isLoggedIn = true;
                    response.setMessage("Logged In successfully");
                    Gson gson = new Gson();
                    response.setJson(gson.toJson(loggedInUser));
                }
                else {
                    map.put("logged_in", false);
                    response.setMessage("Wrong password or username!");
                }

                objOut.writeObject(response);
                objOut.flush();
            }
        }
    }
}
