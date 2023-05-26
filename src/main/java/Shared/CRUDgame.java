package Shared;

import Server.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CRUDgame {
    Database database;

    public CRUDgame(Database database) {
        this.database = database;
    }

    public void createGame(Game game) throws SQLException {
        String query = "INSERT INTO games " +
                "(id, title, developer, genre, price, release_year, controller_support, reviews, size, file_path)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?)";

        PreparedStatement stmt = database.getConnection().prepareStatement(query);

        stmt.setInt(1,game.getId());
        stmt.setString(2,game.getTitle());
        stmt.setString(3,game.getDeveloper());
        stmt.setString(4,game.getGenre());
        stmt.setInt(5,game.getPrice());
        stmt.setInt(6,game.getRelease_year());
        stmt.setBoolean(7,game.isController_support());
        stmt.setInt(8,game.getReviews());
        stmt.setInt(9,game.getSize());
        stmt.setString(10,game.getPath_file());


        stmt.executeUpdate();
    }
    public List<Game> getAllGames() throws SQLException {
        PreparedStatement stmt = database.getConnection().prepareStatement("SELECT * FROM games");

        ResultSet rs = stmt.executeQuery();

        List<Game> res = new ArrayList<>();

        while (rs.next()){
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setTitle(rs.getString("title"));
            game.setDeveloper(rs.getString("developer"));
            game.setGenre(rs.getString("genre"));
            game.setPrice(rs.getInt("price"));
            game.setController_support(rs.getBoolean("controller_support"));
            game.setRelease_year(rs.getInt("release_year"));
            game.setSize(rs.getInt("size"));
            game.setReviews(rs.getInt("reviews"));

            res.add(game);
        }
        return res;
    }

    public Game getGameByID(int id) throws SQLException {
        PreparedStatement stmt = database.getConnection().prepareStatement("SELECT * FROM games where id=?");

        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();



        if(rs.next()){
            Game game = new Game();
            game.setId(rs.getInt("id"));
            game.setTitle(rs.getString("title"));
            game.setDeveloper(rs.getString("developer"));
            game.setGenre(rs.getString("genre"));
            game.setPrice(rs.getInt("price"));
            game.setController_support(rs.getBoolean("controller_support"));
            game.setRelease_year(rs.getInt("release_year"));
            game.setSize(rs.getInt("size"));
            game.setReviews(rs.getInt("reviews"));
            game.setPath_file(rs.getString("file_path"));
            return game;
        }
        return null;
    }
}
