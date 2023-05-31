package Server;

import java.sql.*;

public class DataBase {
    private String url = "jdbc:postgresql://localhost:5432/postgres";
    private String user = "postgres";
    private String pass = "12345";
    private Connection connection;
    private Statement statement;
    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement();
            ResultSet resultSet = null;

            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'games')");
            resultSet.next();
            if (!resultSet.getBoolean(1)) {
                statement.executeUpdate("CREATE table games(\n" +
                        "id text not null primary key,\n" +
                        "title text not null,\n" +
                        "developer text,\n" +
                        "genre text,\n" +
                        "price double precision," +
                        " release_year integer, \n" +
                        "controller_support boolean,\n" +
                        "reviews integer,\n" +
                        "size integer not null,\n" +
                        "file_patch text not null);\n");
                System.out.println("games table create");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('292030', 'The Witcher 3: Wild Hunt', 'CD Projekt Red', 'Role-playing', 29.99, 2015, True, 96, 798,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/292030.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('323190', 'Frostpunk', '11 bit studios', 'Strategy', 29.99, 2018, False, 91, 944,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/323190.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('359550', 'Tom Clancys Rainbow Six Siege', 'Ubisoft', 'First-person Shooter', 19.99, 2015, True, 82, 561,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/359550.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('489830', 'The Elder Scrolls V: Skyrim Special Edition', 'Bethesda Game Studios', 'First-person Shooter', 39.99, 2016, True, 96, 677,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/489830.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('1085660', 'Destiny 2', 'Bungie', 'First-person Shooter', 0.0, 2019, True, 74, 657,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/1085660.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('1151640', 'Horizon Zero Dawn Complete Edition', 'Guerrilla', 'Action-adventure', 49.99, 2020, True, 87, 915,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/1151640.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('1174180', 'Red Dead Redemption 2', 'Rockstar Games', 'Action-adventure', 59.99, 2018, True, 90, 771,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/1174180.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('1196590', 'Resident Evil Village', 'Capcom', 'Survival Horror', 39.99, 2021, True, 95, 811,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/1196590.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('1245620', 'Elden Ring', 'FromSoftware', 'Role-playing', 59.99, 2022, True, 94, 703,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/1245620.png' );");

                statement.executeUpdate("INSERT INTO games (id, title, developer, genre, price, release_year, controller_support,reviews, size, file_patch)" +
                        "  values ('2050650', 'Resident Evil 4', 'Capcom', 'Survival Horror', 59.99, 2023, True, 97, 618,'/home/abolfazl/Documents/AP/Eighth-Assignment-Steam/src/main/java/Server/Resources/2050650.png' );");

            }

            //make accounts table
            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'accounts')");
            resultSet.next();
            if (!resultSet.getBoolean(1)) {
                statement.executeUpdate("CREATE table accounts( id bigserial not null primary key, username text not null, password text not null, date_of_birth date not null)");
                System.out.println("accounts table create");
            }

            //make downloads table
            resultSet = statement.executeQuery("SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = 'downloads')");
            resultSet.next();
            if (!resultSet.getBoolean(1)) {
                statement.executeUpdate("CREATE table downloads( account_id bigint references accounts (id), game_id text references games (id), download_count integer not null); ");
                System.out.println("downloads table create");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

     public void closeConnection(){
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}

